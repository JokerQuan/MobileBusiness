package com.wq.mobilebusiness.home.encounter;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.Utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王铨 on 2016/4/9.
 */
public class ImHereFragment extends Fragment {

    private static final String TAG = "-----NewActive-----";

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private ImHereAdapter mAdapter;

    private List<String> urlsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imhere,container,false);
        initRecyclerView(view);

        return view;
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_imhere);
        mLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        getUserList();
    }

    private void getUserList() {
        urlsList = new ArrayList<String>();
        AVQuery<AVObject> query = new AVQuery<AVObject>("_File");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject obj : list) {
                        String url = obj.getString("url");
                        urlsList.add(url);
                    }
                    mAdapter = new ImHereAdapter(getContext(), urlsList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new ImHereAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(),ImHereActivity.class);
                            intent.putExtra("url",urlsList.get(position));
                            startActivity(intent);
                        }
                    });
                }else {
                    Log.d(TAG, "done: "+e.getMessage());
                }
            }
        });
    }
}
