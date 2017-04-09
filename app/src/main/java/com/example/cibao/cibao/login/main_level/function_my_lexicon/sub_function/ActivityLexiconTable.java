package com.example.cibao.cibao.login.main_level.function_my_lexicon.sub_function;

import android.os.Bundle;

import com.example.cibao.cibao.R;
import com.example.cibao.cibao.login.main_level.function_my_lexicon.ActivityMyLexicon;

/**
 * 屈彬
 * 2017/4/6
 * 单词浏览列表
 * 继承于词典显示活动
 */
public class ActivityLexiconTable extends ActivityMyLexicon {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon_table);
    }
}
