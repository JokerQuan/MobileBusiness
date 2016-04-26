package com.wq.mobilebusiness.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.Utils.T;
import com.wq.mobilebusiness.home.MainActivity;
import com.wq.mobilebusiness.login.LoginActivity;

/**
 * Created by 王铨 on 2016/3/31.
 */
public class RegisterActivity extends Activity
{
    private static final String TAG = "-------Register-------";
    private EditText mEditUsername;
    private EditText mEditPassword;
    private EditText mEditRePassword;
    private RadioGroup mRadioGender;
    private EditText mEditAge;
    private Button mBtnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView() {
        mEditUsername = (EditText) findViewById(R.id.et_username);
        mEditPassword = (EditText) findViewById(R.id.et_password);
        mEditRePassword = (EditText) findViewById(R.id.et_repassword);
        mRadioGender = (RadioGroup) findViewById(R.id.reg_gender);
        mEditAge = (EditText) findViewById(R.id.et_age);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        //按钮点击事件监听
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername();
            }
        });
    }
    /**
     * 检查用户名
     */
    private void checkUsername() {
        String username = mEditUsername.getText().toString().trim();
        if (username.equals("")){
            T.showShort(RegisterActivity.this, R.string.null_username);
        }else if (username.length()<6) {
            T.showShort(RegisterActivity.this, R.string.name_tooshort);
        }else if (username.length()>20){
            T.showShort(RegisterActivity.this, R.string.name_toolong);
        }else if (username.contains(" ")){
            T.showShort(RegisterActivity.this,R.string.not_space);
        }else if (Character.isDigit(username.charAt(0))){
            T.showShort(RegisterActivity.this,R.string.start_num);
        }else {
            checkPassword();
        }
    }

    /**
     * 检查密码的合法性，以及是否一致
     */
    private void checkPassword() {
        String username = mEditUsername.getText().toString().trim();
        String password = mEditPassword.getText().toString().trim();
        String repassword = mEditRePassword.getText().toString().trim();
        String gender = getGender();
        String age = mEditAge.getText().toString().trim();
        if (password.length()<6){
            //密码太短
            T.showShort(RegisterActivity.this,R.string.pw_tooshort);
        }else if (!password.equals(repassword)){
            //密码不一致
            T.showShort(RegisterActivity.this,R.string.pw_notsame);
        }else if (age.equals("")) {
            //未填入年龄
            T.showShort(RegisterActivity.this,R.string.age_null);
        }else {
            //都正确，设置注册按钮状态
            mBtnRegister.setEnabled(false);
            mBtnRegister.setText(R.string.submiting);
            //创建一个 AVUser 并设置相应属性
            AVUser regUser = new AVUser();
            regUser.setUsername(username);
            regUser.setPassword(password);
            regUser.put("gender",gender);
            regUser.put("age",Integer.valueOf(age));
            //开始执行异步注册方法
            regUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    //返回的 AVException 为空，注册成功
                    if (e == null){
                        T.showShort(RegisterActivity.this,R.string.register_success);
                        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        //跳转至主页面
                        startActivity(intent);
                        finish();
                    }else {
                        //否则获取错误信息，并提示用户
                        mBtnRegister.setEnabled(true);
                        mBtnRegister.setText(R.string.register);
                        Log.d(TAG, "done: "+e.getMessage());
                        switch (e.getCode()){
                            case 202:
                                T.showShort(RegisterActivity.this,R.string.user_exists);
                                break;
                        }
                    }
                }
            });
        }
    }
    /**
     * 获取性别单选按钮组的值
     */
    public String getGender() {
        String gender = "secrecy";
        int id = mRadioGender.getCheckedRadioButtonId();
        switch (id){
            case R.id.rb_male:
                return "male";
            case R.id.rb_female:
                return "female";
            case R.id.rb_secrecy:
                return "secrecy";
            default:
                return gender;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        finish();
    }
}
