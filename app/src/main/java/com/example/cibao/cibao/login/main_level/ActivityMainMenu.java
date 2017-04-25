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

import com.example.cibao.cibao.Helpers.DBHelper;
import com.example.cibao.cibao.R;
import com.example.cibao.cibao.login.main_level.function_my_lexicon.ActivityMyLexicon;
import com.example.cibao.cibao.login.main_level.function_my_lexicon.sub_function.ActivityAddWordToLexicon;
import com.example.cibao.cibao.login.main_level.function_my_lexicon.sub_function.ActivityLexiconTable;

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
    protected Button Button_Music;
    protected Button Button_Movie;
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

        Button_Music = (Button) findViewById(R.id.MainMenu_Button_Block_Music);
        Button_Music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ActivityAddWordToLexicon.class);
                // 发送当前词库
                intent.putExtra(DBHelper.TABLE_LEXICON, 0);
                // 是否为编辑状态
                intent.putExtra(ActivityAddWordToLexicon.EDIT_STATUS, false);
                // 发送选中的单词ID
                intent.putExtra(DBHelper.TABLE_WORD, 0);
                startActivity(intent);
            }
        });
        Button_Movie = (Button) findViewById(R.id.MainMenu_Button_Block_Movie);
        Button_Movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ActivityLexiconTable.class);

                startActivity(intent);
            }
        });
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
