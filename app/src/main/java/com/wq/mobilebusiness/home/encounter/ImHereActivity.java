package com.wq.mobilebusiness.home.encounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wq.mobilebusiness.R;

/**
 * Created by 王铨 on 2016/4/19.
 */
public class ImHereActivity extends Activity {
    private ImageView mImageBigimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imhere);

        mImageBigimg = (ImageView) findViewById(R.id.iv_bigimg);

        Glide
                .with(this)
                .load(getIntent().getStringExtra("url"))
                .into(mImageBigimg);

        mImageBigimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
