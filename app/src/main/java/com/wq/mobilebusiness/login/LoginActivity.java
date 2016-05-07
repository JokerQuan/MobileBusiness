package com.wq.mobilebusiness.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.Utils.T;
import com.wq.mobilebusiness.home.MainActivity;
import com.wq.mobilebusiness.register.RegisterActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 登录界面
 * Created by 王铨 on 2016/3/30.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "-------Login-------";
    private EditText mEditUsername;
    private EditText mEditPassword;
    private Button mButtonRegister;
    private Button mButtonLogin;

    private static Boolean isQuit = false;
    private Timer timer = new Timer();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mEditUsername = (EditText) findViewById(R.id.et_username);
        mEditPassword = (EditText) findViewById(R.id.et_password);
        mButtonRegister = (Button) findViewById(R.id.btn_jump_reg);
        mButtonLogin = (Button) findViewById(R.id.btn_login);
        //给按钮设置监听器
        mButtonRegister.setOnClickListener(this);
        mButtonLogin.setOnClickListener(this);
    }

    /**
     * 实现点击事件方法
     * @param v
     */
    @Override
    public void onClick(View v) {
        Log.d("Login", "onClick: start");
        switch (v.getId()){
            case R.id.btn_jump_reg:
                //如果点击了注册按钮，则跳转到注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                //如果点击了登录按钮，设置按钮状态
                mButtonLogin.setEnabled(false);
                mButtonLogin.setText(R.string.logining);
                //登录
                doLogin();
                break;
            case R.id.btn_forget:
                // TODO: 2016/4/6
                break;
        }
    }

    /**
     * 登录操作
     */
    private void doLogin() {
        //获取输入的用户名、密码
        String username = mEditUsername.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();
        if (username.equals("")){
            //如果没有输入用户名，提示用户输入
            T.showShort(LoginActivity.this,R.string.null_username);
            mButtonLogin.setEnabled(true);
            mButtonLogin.setText(R.string.login);
        }else {
            //使用Leancloud提供的AVUser类实现异步登录
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                //操作完成回调
                public void done(AVUser user, AVException e) {
                    if (e == null) {
                        //异常为空，则表示登录成功，跳转至主页面
                        T.showShort(LoginActivity.this, R.string.login_success);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        //登录失败，设置按钮状态
                        mButtonLogin.setEnabled(true);
                        mButtonLogin.setText(R.string.login);
                        //控制台打印错误信息，方便调试
                        Log.d(TAG, "done: " + e.getMessage());
                        switch (e.getCode()) {
                            case 211:
                                //用户名不存在
                                T.showShort(LoginActivity.this, R.string.null_user);
                                break;
                            case 210:
                                //用户名或密码错误
                                T.showShort(LoginActivity.this, R.string.error_userorpsw);
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (!isQuit) {
            isQuit = true;
            T.showShort(getBaseContext(), R.string.press_onemore);
            //两秒内按下两次返回键，则退出程序
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            timer.schedule(task, 2000);
        } else {
            finish();
        }
    }
}
