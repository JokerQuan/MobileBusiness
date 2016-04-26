package com.wq.mobilebusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.wq.mobilebusiness.home.MainActivity;
import com.wq.mobilebusiness.login.LoginActivity;

/**
 * Created by 王铨 on 2016/4/3.
 */
public class LogoActivity extends Activity {
    private ImageView mImageLogo;
    private TextView mTextLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置布局文件
        setContentView(R.layout.activity_logo);
        initView();
    }
    /**
     * 初始化页面
     */
    private void initView(){
        mImageLogo = (ImageView) findViewById(R.id.iv_logo);
        mTextLogo = (TextView) findViewById(R.id.tv_logo);
        //新建一个从完全透明到不透明的渐变动画
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        //时间为2000毫秒
        animation.setDuration(2000);
        //给图标和文字设置上面的动画
        mImageLogo.setAnimation(animation);
        mTextLogo.setAnimation(animation);
        //设置动画监听器
        animation.setAnimationListener(new Animation.AnimationListener() {
            AVUser currentUser;
            Intent intent;
            //动画开始时回调改函数
            @Override
            public void onAnimationStart(Animation animation) {
                //获取当前已登录的用户
                //AVUser类由Leancloud平台提供
                currentUser = AVUser.getCurrentUser();
                if (currentUser!=null){
                    //如果当前用户存在，则设置Intent为跳转到程序主界面
                    intent = new Intent(LogoActivity.this,MainActivity.class);
                }else {
                    //用户不存在，则设置Intent为跳转到登录界面
                    intent = new Intent(LogoActivity.this,LoginActivity.class);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            //当动画执行结束时回调此函数
            @Override
            public void onAnimationEnd(Animation animation) {
                //启动相应的界面
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
