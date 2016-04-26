package com.wq.mobilebusiness.home.encounter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.home.active.Active;

import java.util.List;

/**
 * Created by 王铨 on 2016/4/9.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private static final String TAG = "-----Adapter-----";

    private List<User> userList;
    private Context context;

    public UsersAdapter(Context context, List<User> userList){
        this.context = context;
        this.userList = userList;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener)
    {
        this.mOnItemClickListener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextUsername.setText(userList.get(position).getUsername());
        holder.mTextUserGender.setText(userList.get(position).getGender());
        holder.mTextUserAge.setText(""+userList.get(position).getAge());
        holder.mTextSignature.setText(userList.get(position).getSignature());
        Glide
                .with(context)
                .load(userList.get(position).getImgUrl())
                .into(holder.mImageUser);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageUser;
        public TextView mTextUsername;
        public TextView mTextUserGender;
        public TextView mTextUserAge;
        public TextView mTextSignature;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageUser = (ImageView) itemView.findViewById(R.id.iv_user);
            mTextUsername = (TextView) itemView.findViewById(R.id.tv_username);
            mTextUserGender = (TextView) itemView.findViewById(R.id.tv_usergender);
            mTextUserAge = (TextView) itemView.findViewById(R.id.tv_userage);
            mTextSignature = (TextView) itemView.findViewById(R.id.tv_signature);
        }
    }

}
