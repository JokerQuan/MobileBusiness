package com.wq.mobilebusiness.home;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bumptech.glide.Glide;
import com.wq.mobilebusiness.MobileBusiness;
import com.wq.mobilebusiness.R;
import com.wq.mobilebusiness.Utils.T;
import com.wq.mobilebusiness.login.LoginActivity;
import com.wq.mobilebusiness.setting.SettingActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "-----Main-----";
    private static Boolean isQuit = false;
    private Timer timer = new Timer();
    private FragmentManager fm = getSupportFragmentManager();
    private HomeFragment mHomeFragment;
    private EncounterFragment mEncounterFragment;
    private SmartLifeFragment mSmartLifeFragment;
    private Toolbar toolbar;
    private AVUser mCurrentUser;
    private ImageView mImageAvatar;
    private TextView mTextNickName;
    private TextView mTextMySign;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_36dp);
        setSupportActionBar(toolbar);

        initView();
        initEvent();
        initData();
        initChat();
    }

    private void initChat() {
        AVIMClient client = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());
        client.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                T.showShort(MobileBusiness.getContext(),"开始接收消息");
            }
        });

    }

    private void initView() {
        mHomeFragment = new HomeFragment();
        mEncounterFragment = new EncounterFragment();
        mSmartLifeFragment = new SmartLifeFragment();
        replaceFragment(R.id.fl_content,mHomeFragment);
        mCurrentUser = AVUser.getCurrentUser();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        View headView = getLayoutInflater().from(MainActivity.this).inflate(R.layout.drawer_header,null);
        mImageAvatar = (ImageView) headView.findViewById(R.id.avatar);
        mTextNickName = (TextView) headView.findViewById(R.id.nick_name);
        mTextMySign = (TextView) headView.findViewById(R.id.tv_mysign);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.addHeaderView(headView);
    }

    private void initEvent(){
        //监听抽屉导航的菜单项的选择，并作出相应的操作
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.home:
                        replaceFragment(R.id.fl_content,mHomeFragment);
                        toolbar.setTitle(R.string.app_name);
                        break;
                    case R.id.encounter:
                        if (mHomeFragment.isVisible()){
                            hideFragment(mHomeFragment);
                        }
                        replaceFragment(R.id.fl_content,mEncounterFragment);
                        toolbar.setTitle(R.string.encounter);
                        break;
                    case R.id.smart_life:
                        if (mHomeFragment.isVisible()){
                            hideFragment(mHomeFragment);
                        }
                        replaceFragment(R.id.fl_content,mSmartLifeFragment);
                        toolbar.setTitle(R.string.smart_life);
                        break;
                    case R.id.about:
                        showAboutDialog();
                        break;
                    case R.id.settings:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.logout:
                        AVUser.logOut();             //清除缓存用户对象
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        toolbar.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDrawerLayout.isDrawerOpen(mNavigationView)){
                    mDrawerLayout.openDrawer(mNavigationView);
                }else {
                    mDrawerLayout.closeDrawers();
                }
            }
        });
        mImageAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
            }
        });
    }

    /**
     * 显示“关于”信息对话框
     */
    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(R.string.about)
                .setMessage(R.string.about_content)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    private void replaceFragment(int resID, Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(resID,fragment);
        transaction.commit();
    }

    private void hideFragment(Fragment fragment){
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(fragment);
    }

    /**
     * 初始化Navigation Drawer数据
     */
    private void initData() {
        if (mCurrentUser != null) {
            Log.d(TAG, "initData: "+mCurrentUser.toString());
            mTextNickName.setText(mCurrentUser.getUsername());
            mTextMySign.setText(mCurrentUser.getString("signature"));
            String url = "";
            //如果用户还没设置头像，则设置为默认头像
            if (mCurrentUser.getAVFile("head") == null){
                url = "http://ac-5vaa4ngv.clouddn.com/8a8cc8f7889a9c20.jpg";
            } else {
                url = mCurrentUser.getAVFile("head").getUrl();
            }
            Glide
                    .with(MainActivity.this)
                    .load(url)
                    .into(mImageAvatar);
        }
    }
    /**
     * 再按一下返回键退出程序
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)){
            mDrawerLayout.closeDrawers();
        }else if (!mHomeFragment.isVisible()) {
            replaceFragment(R.id.fl_content, mHomeFragment);
            mNavigationView.setCheckedItem(R.id.home);
            toolbar.setTitle(R.string.app_name);
        }else if (!isQuit) {
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
    /**
     * 监听菜单按钮，控制NavigationView
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_MENU:
                if (!mDrawerLayout.isDrawerOpen(mNavigationView)){
                    mDrawerLayout.openDrawer(mNavigationView);
                }else {
                    mDrawerLayout.closeDrawers();
                }
        }
        return super.onKeyDown(keyCode, event);
    }
}
