package com.wq.mobilebusiness.home.smartlife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.home.encounter.User;

import java.util.List;

/**
 * Created by 王铨 on 2016/4/9.
 */
public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    private static final String TAG = "-----Adapter-----";

    private List<Business> businessList;
    private Context context;

    public BusinessAdapter(Context context, List<Business> businessList){
        this.context = context;
        this.businessList = businessList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextBusinessname.setText(businessList.get(position).getName());
        holder.mTextIntroduce.setText(businessList.get(position).getIntroduce());
        Glide
                .with(context)
                .load(businessList.get(position).getImgUrl())
                .into(holder.mImageBusiness);

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
        return businessList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageBusiness;
        public TextView mTextBusinessname;
        public TextView mTextIntroduce;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageBusiness = (ImageView) itemView.findViewById(R.id.iv_business);
            mTextBusinessname = (TextView) itemView.findViewById(R.id.tv_businessname);
            mTextIntroduce = (TextView) itemView.findViewById(R.id.tv_businessintroduce);
        }
    }

}
