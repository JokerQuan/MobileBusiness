package com.wq.mobilebusiness.home.encounter.chat;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.wq.mobilebusiness.MobileBusiness;
import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.Utils.T;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by 王铨 on 2016/4/14.
 */
public class ChatActivity extends Activity {
    private static final String DEFAULT_IMGURL = "http://pic.baike.soso.com/p/20130705/20130705160610-1781916533.jpg";

    private EditText mEditChat;
    private Button mButtonSend;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ChatAdapter mAdapter;

    private Toolbar mToolBar;

    private List<Chat> mChats = new ArrayList<Chat>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        EventBus.getDefault().register(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        String title = getIntent().getStringExtra("username");
        mToolBar.setTitle("与 " + title + " 偶遇");


        mEditChat = (EditText) findViewById(R.id.et_chat);
        mButtonSend = (Button) findViewById(R.id.btn_send);
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = mEditChat.getText().toString().trim();
                if(!chat.equals("")){
                    mButtonSend.setEnabled(false);
                    sendMessageToSbFromMe();
                }else {
                    T.showShort(getApplicationContext(),"请输入聊天内容！");
                }
            }
        });

//        initView();

        initRecyclerView();

    }

//    private void initView() {
//        final AVUser from = AVUser.getCurrentUser();
//        AVIMClient tom = AVIMClient.getInstance(from.getUsername());
//        tom.open(new AVIMClientCallback() {
//
//            @Override
//            public void done(AVIMClient client, AVIMException e) {
//                if (e == null) {
//                    //登录成功
//                    AVIMConversation conv = client.getConversation("57134b8639b057006bd048da");
//                    conv.queryMessages(new AVIMMessagesQueryCallback() {
//                        @Override
//                        public void done(List<AVIMMessage> messages, AVIMException e) {
//                            if (e == null) {
//                                //成功获取最新10条消息记录
//                                for (AVIMMessage message: messages) {
//                                    AVIMTextMessage msg = (AVIMTextMessage) message;
//                                    String headUrl = from.getAVFile("head").getUrl();
//                                    String name = msg.getFrom();
//                                    String time = String.valueOf(msg.getTimestamp());
//                                    String content = msg.getText();
//                                    Chat chat = new Chat(name,headUrl,time,content);
//                                    mAdapter.addData(chat);
//                                }
//                            }
//                        }
//                    });
//                }else {
//                    Log.d("--------", "done: 获取失败");
//                }
//            }
//        });
//    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_chat);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //创建并设置Adapter
        mAdapter = new ChatAdapter(this,mChats);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 从我发送消息给某人
     */
    public void sendMessageToSbFromMe() {
        final AVUser from = AVUser.getCurrentUser();
        final String to = getIntent().getStringExtra("username");
        AVIMClient me = AVIMClient.getInstance(from.getUsername());
        me.open(new AVIMClientCallback() {
            @Override
            public void done(final AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建与Jerry之间的对话
                    client.createConversation(Arrays.asList(to), "me & " + to, null,
                            new AVIMConversationCreatedCallback() {
                                @Override
                                public void done(AVIMConversation conversation, AVIMException e) {
                                    if (e == null) {
                                        final AVIMTextMessage msg = new AVIMTextMessage();
                                        msg.setText(mEditChat.getText().toString().trim());
                                        // 发送消息
                                        conversation.sendMessage(msg, new AVIMConversationCallback() {
                                            @Override
                                            public void done(AVIMException e) {
                                                if (e == null) {
                                                    //清空输入框信息
                                                    mEditChat.setText("");
                                                    //把对话添加到对话框中
                                                    String headUrl = "";
                                                    if (from.getAVFile("head") == null){
                                                        headUrl = DEFAULT_IMGURL;
                                                    } else {
                                                        headUrl = from.getAVFile("head").getUrl();
                                                    }
                                                    String name = msg.getFrom();
                                                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
                                                    String time = sdf.format(new Date(msg.getTimestamp()));
                                                    String content = msg.getText();
                                                    Chat chat = new Chat(name,headUrl,time,content);
                                                    mAdapter.addData(chat);
                                                    client.close(null);
                                                    mButtonSend.setEnabled(true);
                                                }else {
                                                    mButtonSend.setEnabled(true);
                                                    client.close(null);
                                                    Log.d("last", "done: "+e.getMessage());
                                                    T.showShort(MobileBusiness.getContext(),"发送失败");
                                                }
                                            }
                                        });
                                    }else {
                                        mButtonSend.setEnabled(true);
                                        client.close(null);
                                        Log.d("conversation", "done: "+e.getMessage());
                                    }
                                }
                            });
                }else {
                    mButtonSend.setEnabled(true);
                    client.close(null);
                    Log.d("client", "done: "+e.getMessage());
                }
            }
        });
    }

    /**
     * 接收到消息
     * @param chatEvent 注册的事件类型
     */
    public void onEvent(ChatEvent chatEvent){
        Log.d("---------", "onEventMainThread: ");
        AVIMMessage message = chatEvent.getMessage();
        AVIMTextMessage text = (AVIMTextMessage) message;
        AVIMConversation conversation = chatEvent.getConversation();
        AVIMClient client = chatEvent.getClient();
        String headUrl = "http://ac-5vaa4ngv.clouddn.com/8f8f01d32cd36563.png";
        String name = text.getFrom();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);

        String time = sdf.format(new Date(text.getTimestamp()));
        String content = text.getText();
        Chat chat = new Chat(name,headUrl,time,content);
        mAdapter.addData(chat);
        client.close(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
