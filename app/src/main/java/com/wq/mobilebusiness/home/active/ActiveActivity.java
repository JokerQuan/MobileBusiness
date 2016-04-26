package com.wq.mobilebusiness.home.active;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wq.mobilebusiness.R;

/**
 * Created by 王铨 on 2016/4/19.
 */
public class ActiveActivity extends Activity {
    private TextView mTextTitle;
    private ImageView mImageActive;
    private TextView mTextContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);

        initView();

        initData();
    }

    private void initView() {
        mTextTitle = (TextView) findViewById(R.id.et_title);
        mImageActive = (ImageView) findViewById(R.id.iv_content);
        mTextContent = (TextView) findViewById(R.id.et_content);
        mTextTitle.setText("活动标题");
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        Active active = (Active) bundle.getSerializable("active");
        mTextContent.setText(active.getIntroduce());
        Glide
                .with(this)
                .load(active.getImgUrl())
                .into(mImageActive);
    }
}
