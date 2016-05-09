package com.wq.mobilebusiness;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.wq.mobilebusiness.Utils.NotificationUtils;
import com.wq.mobilebusiness.home.encounter.chat.ChatEvent;

import de.greenrobot.event.EventBus;

/**
 *
 * Created by 王铨 on 2016/3/30.
 */
public class MobileBusiness extends Application {
    public static String USER = "";
    public static boolean isOnChat = false;
    private static Context context;
    public static Context getContext(){
        return context;
    }
    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            Log.d("Mobilebusiness", "onMessage: "+client.getClientId());
            if(message instanceof AVIMTextMessage){
                String myself = AVUser.getCurrentUser().getUsername();
                //过滤自己发送的消息
                if (!message.getFrom().equals(myself)){
                    //通知ChatActivity
                    Log.d("Mobilebusiness", message.getFrom());
                    Log.d("Mobilebusiness", myself);
                    EventBus.getDefault().post(new ChatEvent(message,conversation,client));
                    //Notifycation
                    if (!isOnChat){
                        USER = message.getFrom();
                        sendNotifycation(message,conversation);
                    }
                }
            }
        }

        public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            Log.d("Application", "onMessageReceipt: ");
        }

        private void sendNotifycation(AVIMMessage message, AVIMConversation conversation){
            String notificationContent = ((AVIMTextMessage)message).getText();
            Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
            intent.putExtra(Constants.CONVERSATION_ID, conversation.getConversationId());
            intent.putExtra(Constants.MEMBER_ID, message.getFrom());
            NotificationUtils.showNotification(context, message.getFrom(), notificationContent, null, intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AVOSCloud.initialize(this,
                "5VAA4ngv7eBsuyxU3C6gkwcD-gzGzoHsz",
                "u41y0IaqOAsLwOiIml7V588e");
        //注册默认的消息处理逻辑
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
    }
}
