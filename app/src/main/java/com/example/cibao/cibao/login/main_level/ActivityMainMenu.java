package com.example.cibao.cibao.login.main_level;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.cibao.cibao.R;
import com.example.cibao.cibao.login.main_level.function_my_lexicon.ActivityMyLexicon;

/**
 * 屈彬
 * 2017/3/12
 * 主菜单活动
 */
public class ActivityMainMenu extends AppCompatActivity {

    /**
     * @show “我的词库”按钮
     */
    protected Button Button_BlockMyLexicon;
    /**
     * @show 按钮点击事件监听器
     */
    protected BlockButtonClickListener BlockButtonListener_ClickListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initializeWidget();
    }

    /**
     * @show 初始化控件
     */
    protected void initializeWidget(){
        BlockButtonListener_ClickListen = new BlockButtonClickListener();

        Button_BlockMyLexicon = (Button)findViewById(R.id.MainMenu_Button_Block_MyLexicon);
        Button_BlockMyLexicon.setOnClickListener(BlockButtonListener_ClickListen);
    }

    /**
     * @show 设置按钮监听器
     */
    protected class BlockButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId() == Button_BlockMyLexicon.getId())Button_BlockMyLexicon_ClickEvent();
        }


    }

    /**
     * @show “我的词库”按钮点击后发生的事件
     */
    protected void Button_BlockMyLexicon_ClickEvent(){
        Intent intent = new Intent(this, ActivityMyLexicon.class);
        startActivity(intent);
    }
}
