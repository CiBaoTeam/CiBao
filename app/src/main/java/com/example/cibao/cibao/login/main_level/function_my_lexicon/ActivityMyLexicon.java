package com.example.cibao.cibao.login.main_level.function_my_lexicon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.cibao.cibao.R;

/**
 * 屈彬
 * 2017/3/12
 * 功能-我的词库
 */
public class ActivityMyLexicon extends AppCompatActivity {

    /**
     * @show 词典清单
     */
    protected ListView ListView_Dictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lexicon);

        initializeWidget();
    }

    /**
     * @show 初始化控件
     */
    protected void initializeWidget(){
        ListView_Dictionary = (ListView)findViewById(R.id.MyLexicon_ListView_Dictionary);
    }
}
