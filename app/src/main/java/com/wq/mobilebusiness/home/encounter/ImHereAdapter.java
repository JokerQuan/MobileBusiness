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

import java.util.List;
import java.util.Random;

/**
 * Created by 王铨 on 2016/4/9.
 */
public class ImHereAdapter extends RecyclerView.Adapter<ImHereAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private static final String TAG = "-----Adapter-----";

    private List<String> urlsList;
    private Context context;

    public ImHereAdapter(Context context, List<String> urlsList){
        this.context = context;
        this.urlsList = urlsList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imhere,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int height = (int)(500*Math.random())+100;
        Glide
                .with(context)
                .load(urlsList.get(position))
                .override(520,height<300?height+300:height)
                .centerCrop()
                .into(holder.mImageImHere);

        holder.itemView.setTag(urlsList.get(position));

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
        return urlsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageImHere;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageImHere = (ImageView) itemView.findViewById(R.id.iv_imhere);
        }
    }

}
