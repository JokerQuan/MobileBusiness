package com.wq.mobilebusiness.home.active;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.wq.mobilebusiness.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王铨 on 2016/4/9.
 */
public class OldActiveFragment extends Fragment {

    private static final String TAG = "-----NewActive-----";

    private static final String DEFAULT_IMGURL = "http://pic.baike.soso.com/p/20130705/20130705160610-1781916533.jpg";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ActivesAdapter mAdapter;

    private List<Active> activeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active,container,false);
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_active);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
        //创建并设置Adapter
        getActiveList();


    }

    private void getActiveList() {
        activeList = new ArrayList<Active>();
        AVQuery<AVObject> query = new AVQuery<AVObject>("Active");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject obj : list) {
                    String url = obj.getAVFile("img").getUrl();
                    String introduce = obj.get("introduce").toString();
                    Active active = new Active(url,introduce);
                    activeList.add(active);
                }
                mAdapter = new ActivesAdapter(getContext(),activeList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new ActivesAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("active",activeList.get(position));
                        Intent intent = new Intent(getContext(),ActiveActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
