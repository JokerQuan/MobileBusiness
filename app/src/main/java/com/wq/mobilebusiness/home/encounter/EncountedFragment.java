package com.wq.mobilebusiness.home.encounter;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.Utils.T;
import com.wq.mobilebusiness.home.encounter.chat.ChatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王铨 on 2016/4/9.
 */
public class EncountedFragment extends Fragment {

    private static final String TAG = "-----NewActive-----";
    private static final String DEFAULT_IMGURL = "http://pic.baike.soso.com/p/20130705/20130705160610-1781916533.jpg";


    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private UsersAdapter mAdapter;

    private List<User> usersList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_user);
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
        getUserList();


    }

    private void getUserList() {
        usersList = new ArrayList<User>();
        AVQuery<AVObject> query = new AVQuery<AVObject>("_User");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject obj : list) {
                        String url = "";
                        if (obj.getAVFile("head") == null){
                            url = DEFAULT_IMGURL;
                        } else {
                            url = obj.getAVFile("head").getUrl();
                        }
                        String username = obj.getString("username");
                        String gender = obj.getString("gender");
                        int age = obj.getInt("age");
                        String signature = obj.getString("signature");
                        User user = new User(url, username, gender, age, signature);
                        usersList.add(user);
                    }
                    mAdapter = new UsersAdapter(getContext(), usersList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(),ChatActivity.class);
                            intent.putExtra("username",usersList.get(position).getUsername());
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
