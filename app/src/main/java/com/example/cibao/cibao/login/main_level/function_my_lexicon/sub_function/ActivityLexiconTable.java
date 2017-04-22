package com.example.cibao.cibao.login.main_level.function_my_lexicon.sub_function;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cibao.cibao.DomainModelClass.Word;
import com.example.cibao.cibao.DomainModelClass.WordSelectTable;
import com.example.cibao.cibao.Helpers.DBHelper;
import com.example.cibao.cibao.R;
import com.example.cibao.cibao.login.main_level.function_my_lexicon.ActivityMyLexicon;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 屈彬
 * 2017/4/6
 * 单词浏览列表
 * 继承于词典显示活动
 */
public class ActivityLexiconTable extends ActivityMyLexicon {

    /**
     * @show 单词数据库助手
     */
    DBHelper WordHelper;
    /**
     * @show 选词数据库助手
     */
    DBHelper WordSelectHelper;
    /**
     * @show 单词刀
     */
    Dao<Word, String> DaoWord;
    /**
     * @show 选词表刀
     */
    Dao<WordSelectTable, Integer> DaoSelectTable;
    /**
     * @show 父词库编号
     */
    int ParentLexiconID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon_table);

        initializeParam();
        initializeWidget();
        initializeDatabases();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(WordHelper != null)WordHelper.close();
        if(WordSelectHelper != null)WordSelectHelper.close();
        try{
            DaoWord.closeLastIterator();
            DaoSelectTable.closeLastIterator();
        }catch (SQLException sqlE){
            Log.e("onDestroy()", sqlE.toString());
        }
    }

    /**
     * @show 初始化全局变量
     */
    void initializeParam(){
        // 获取父活动的EXTRA
        Intent getLexiconID = getIntent();
        try{
            ParentLexiconID = Integer.parseInt(getLexiconID.getStringExtra(DBHelper.TABLE_LEXICON));
        }catch (Exception e){
            Log.e("initializeParam", e.toString());
        }
    }
    /**
     * @show 初始化布局控件
     */
    void initializeWidget(){
        ListViewMain = (ListView) findViewById(R.id.LexiconTable_ListView_WordList);
        refreshList();
    }

    /**
     * @show 初始化数据库
     */
    void initializeDatabases(){
        WordHelper = new DBHelper(this, Word.class);
        WordSelectHelper = new DBHelper(this, WordSelectTable.class);
        try{
            DaoWord = WordHelper.createDao(Word.class);
            DaoSelectTable = WordSelectHelper.createDao(WordSelectTable.class);
        }catch (SQLException sqlE){
        Log.e("initializeDatabases()", sqlE.toString());
    }
    }
    /**
     * @show 刷新列表
     */
    void refreshList(){
        if(ListItems != null)ListItems.clear();
        initializeListAdapter();
        ListViewMain.setAdapter(ListAdapter);
    }

    /**
     * @show 初始化列表项
     */
    void initializeListItem(){
        // 从数据库中获取单词
        ListItems = new ArrayList<>();
        // 用词库ID筛选
        if(DaoSelectTable == null || DaoWord == null){
            Toast.makeText(this, "数据库初始化失败!", Toast.LENGTH_LONG).show();
            return;
        }
        List<WordSelectTable> WordSelectList = null;
        List<Word> WordList = null;// 词库包含的单词的ID
        try{
            WordSelectList = DaoSelectTable.queryForEq("LEXICON_ID", ParentLexiconID);
        }catch (SQLException sqlE){
            Log.e(" initializeListItems()", sqlE.toString());
        }
        if(WordSelectList == null){
            Toast.makeText(this, "选词表获取失败!", Toast.LENGTH_LONG).show();
            return;
        }
        // 初始化单词列表
        WordList = new LinkedList<>();
        try{
            for (WordSelectTable wst:
                    WordSelectList) {
                WordList.add(DaoWord.queryForId(wst.getWordID() + ""));
            }
        }catch (SQLException sqlE){
            Log.e(" initializeListItems()", sqlE.toString());
        }

    }
    /**
     * @show 初始化列表适配器
     */
    void initializeListAdapter(){
        initializeListItem();
    }
    /**
     * @show 创建选项菜单
     * @param menu 菜单
     * @return 是否创建成功
     */
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        setIconEnable(menu, true);
        menu.add(0, MENU_ITEM_0, 0, "添加单词").setIcon(android.R.drawable.ic_menu_add);
        // menu.add(0, MENU_ITEM_1, 0, "删除").setIcon(android.R.drawable.ic_menu_delete);
        return true;
    }

    /**
     * @show 设置菜单图标可显示性
     * @param menu 菜单
     * @param enable 是否可显示
     */
    private void setIconEnable(Menu menu, boolean enable){
        try{
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);

            //MenuBuilder实现Menu接口，创建菜单时，传进来的menu其实就是MenuBuilder对象(java的多态特征)
            m.invoke(menu, enable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * @show 菜单项点击事件
     * @param item 菜单项
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case MENU_ITEM_0:
                break;
        }
        return true;
    }

    /**
     * @show 跳转至编辑单词活动
     */
    void navigateToEditWord(){
        Intent intent = new Intent(this, ActivityAddWordToLexicon.class);
        // 发送当前词库
        this.startActivity(intent);
    }
}
