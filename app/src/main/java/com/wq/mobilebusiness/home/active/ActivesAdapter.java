package com.wq.mobilebusiness.home.active;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wq.mobilebusiness.R;

import java.util.List;

/**
 * Created by 王铨 on 2016/4/9.
 */
public class ActivesAdapter extends RecyclerView.Adapter<ActivesAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private static final String TAG = "-----Adapter-----";

    private List<Active> activeList;
    private Context context;

    public ActivesAdapter(Context context, List<Active> activeList){
        this.context = context;
        this.activeList = activeList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextActive.setText(activeList.get(position).getIntroduce());
        Glide
                .with(context)
                .load(activeList.get(position).getImgUrl())
                .into(holder.mImageActive);

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
        return activeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageActive;
        public TextView mTextActive;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageActive = (ImageView) itemView.findViewById(R.id.iv_avtive);
            mTextActive = (TextView) itemView.findViewById(R.id.tv_active);
        }
    }

}
