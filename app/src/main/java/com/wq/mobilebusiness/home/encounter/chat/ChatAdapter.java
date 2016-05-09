package com.wq.mobilebusiness.home.encounter.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.wq.mobilebusiness.R;

import java.util.List;

/**
 * 聊天数据Adapter
 * Created by 王铨 on 2016/4/17.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static final int CHAT_SEND = 0;
    private static final int CHAT_RECIVE = 1;
    private Context context;
    private List<Chat> mChats;

    private AVUser mUser;

    public ChatAdapter(Context context, List<Chat> mChats) {
        this.context = context;
        this.mChats = mChats;
        mUser = AVUser.getCurrentUser();
    }

    @Override
    public int getItemViewType(int position) {
        if (mChats.get(position).getFromWho().equals(mUser.getUsername())){
            return CHAT_SEND;
        }else {
            return CHAT_RECIVE;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder vh;
        if (viewType == CHAT_SEND){
            Log.d("ChatAdapter", "onCreateViewHolder: send");
            view = LayoutInflater.from(context).inflate(R.layout.item_send,parent,false);
            vh = new ViewHolder(view);
        }else {
            Log.d("ChatAdapter", "onCreateViewHolder: recive");
            view = LayoutInflater.from(context).inflate(R.layout.item_recive,parent,false);
            vh = new ViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat chat = mChats.get(position);
        Log.d("bind", "onBindViewHolder: "+chat.toString());
        holder.mTextName.setText(chat.getFromWho());
        holder.mTextTime.setText(chat.getDate());
        holder.mTextConten.setText(chat.getContent());
        Glide
                .with(context)
                .load(chat.getHeadUrl())
                .into(holder.mImageHead);
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public void addData(Chat chat) {
        Log.d("add", "addData: ");
        mChats.add(chat);
        notifyItemInserted(mChats.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageHead;
        private TextView mTextName;
        private TextView mTextTime;
        private TextView mTextConten;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageHead = (ImageView) itemView.findViewById(R.id.chat_head);
            mTextName = (TextView) itemView.findViewById(R.id.chat_name);
            mTextTime = (TextView) itemView.findViewById(R.id.chat_time);
            mTextConten = (TextView) itemView.findViewById(R.id.chat_content);
        }
    }
}
