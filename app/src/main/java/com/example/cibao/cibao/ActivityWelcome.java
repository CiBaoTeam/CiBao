package com.example.cibao.cibao;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.example.cibao.cibao.login.main_level.ActivityMainMenu;

/**
 * 屈彬
 * 2017/3/19
 * 欢迎活动
 */
public class ActivityWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 去除标题栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        /* 进入下一个活动 */
        new ThreadNavigate().start();
    }
    /**
     * @show 自动进入到登录活动
     */
    protected void navigateToActivityLogin(){

        Intent intent = new Intent(this, ActivityMainMenu.class);
        startActivity(intent);
        finish();
    }
    /**
     * @show 用于处理异步操作
     */
    protected Handler intentHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            navigateToActivityLogin();
        }
    };

    /**
     * @show 延时进程
     */
    protected class ThreadNavigate extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(2000);
            }catch (Exception e){
                Log.d("navigate fail", "navigateToActivityLogin: Thread.sleep() Exception");
            }
            Message msg = new Message();
            msg.what = 0;
            intentHandler.sendMessage(msg);
        }
    }
}
