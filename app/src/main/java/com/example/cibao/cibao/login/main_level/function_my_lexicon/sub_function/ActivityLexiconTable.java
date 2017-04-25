package com.example.cibao.cibao.login.main_level.function_my_lexicon.sub_function;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.example.cibao.cibao.DomainModelClass.Word;
import com.example.cibao.cibao.DomainModelClass.WordSelectTable;
import com.example.cibao.cibao.Helpers.Base64Helper;
import com.example.cibao.cibao.Helpers.BitmapHelper;
import com.example.cibao.cibao.Helpers.DBHelper;
import com.example.cibao.cibao.R;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 屈彬
 * 2017/4/6
 * 单词浏览列表
 * 继承于词典显示活动
 */
public class ActivityLexiconTable extends AppCompatActivity {

    /**
     * @show 主列表
     */
    protected ListView ListViewMain;
    /**
     * @show 列表项目
     */
    protected ArrayList<HashMap<String, Object>> ListItems;
    /**
     * @show 主列表的适配器
     */
    protected SimpleAdapter ListAdapter;
    /**
     * @show 项目点击监听
     */
    protected AdapterView.OnItemClickListener MainItemClickListener;
    /**
     * @show 项目长按事件
     */
    protected AdapterView.OnItemLongClickListener MainItemLongClickListener;
    /**
     * @show 菜单第0个项
     */
    protected final int MENU_ITEM_0 = Menu.FIRST;
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
    /**
     * @show 布局键-单词拼写
     */
    final String LAYOUT_KEY_SPELLING = "SPELLING";
    /**
     * @show 布局键-单词解释
     */
    final String LAYOUT_KEY_MEANING = "MEANING";
    /**
     * @show 布局键-图片
     */
    final String LAYOUT_KEY_IMAGE="IMAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lexicon_table);

        initializeParam();
        initializeDatabases();
        initializeWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(WordHelper != null) {
            WordHelper.close();
            try{
                if(DaoWord != null)DaoWord.closeLastIterator();
            }catch (SQLException sqlE){
                Log.e("onDestroy()", sqlE.toString());
            }
        }
        if(WordSelectHelper != null) {
            WordSelectHelper.close();
            try{
                if(DaoSelectTable != null)DaoSelectTable.closeLastIterator();
            }catch (SQLException sqlE){
                Log.e("onDestroy()", sqlE.toString());
            }
        }
    }

    /**
     * @show 初始化全局变量
     */
    void initializeParam(){
        // 获取父活动的EXTRA
        Intent getLexiconID = getIntent();
        try{
            ParentLexiconID = getLexiconID.getIntExtra(DBHelper.TABLE_LEXICON, 0);
            //Toast.makeText(getApplicationContext(), ParentLexiconID + "", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e("initializeParam", e.toString());
        }
    }
    /**
     * @show 初始化布局控件
     */
    void initializeWidget(){
        ListViewMain = (ListView) findViewById(R.id.LexiconTable_ListView_WordList);
        refreshList(null);
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
    void refreshList(View view){
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
            Toast.makeText(this, "选词表为空!", Toast.LENGTH_LONG).show();
            return;
        }
        // 初始化单词列表
        WordList = new LinkedList<>();
        try{
            for (WordSelectTable wst:
                    WordSelectList) {
                WordList.add(DaoWord.queryForEq("WORD_ID", wst.getWordID()).get(0));
            }
        }catch (SQLException sqlE){
            Log.e(" initializeListItems()", sqlE.toString());
        }
        if(WordList == null)return;
        // 单词表，添加单词项
        for(Word word : WordList){
            HashMap<String, Object> ItemMap = new HashMap<>();
            ItemMap.put(LAYOUT_KEY_SPELLING, word.getSpelling());
            ItemMap.put(LAYOUT_KEY_MEANING, word.getMeaning());
            //ItemMap.put(LAYOUT_KEY_IMAGE, BitmapHelper.BitmapMatrix.resizeImage(Base64Helper.getBitmapFromBase64Code(word.getPictureOfWord()),
                    //BitmapHelper.BitmapMatrix.BitmapSize, BitmapHelper.BitmapMatrix.BitmapSize));
            ListItems.add(ItemMap);
        }
    }
    /**
     * @show 初始化列表适配器
     */
    void initializeListAdapter(){
        initializeListItem();
        if(ListItems == null)return;
        ListAdapter = new SimpleAdapter(this, ListItems, R.layout.layout_word_item,
                new String[]{LAYOUT_KEY_SPELLING, LAYOUT_KEY_MEANING, LAYOUT_KEY_IMAGE},
                new int[]{R.id.LexiconTable_word_item_textView_spelling, R.id.LexiconTable_word_item_textView_meaning,
                R.id.LexiconTable_word_item_imageView_image});
        ListAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                // TODO Auto-generated method stub
                if((view instanceof ImageView) && (data instanceof Bitmap)) {
                    ImageView imageView = (ImageView) view;
                    Bitmap bmp = (Bitmap) data;
                    imageView.setImageBitmap(bmp);
                    return true;
                }
                return false;
            }
        });

    }
    /**
     * @show 创建选项菜单
     * @param menu 菜单
     * @return 是否创建成功
     */
    @Override
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
                navigateToEditWord(0, false);
                break;
        }
        return true;
    }
    /**
     * @show 跳转至编辑单词活动
     */
    void navigateToEditWord(int wordID, boolean isEditMode){
        Intent intent = new Intent(this, ActivityAddWordToLexicon.class);
        // 发送当前词库
        intent.putExtra(DBHelper.TABLE_LEXICON, ParentLexiconID);
        // 是否为编辑状态
        intent.putExtra(ActivityAddWordToLexicon.EDIT_STATUS, isEditMode);
        // 发送选中的单词ID
        intent.putExtra(DBHelper.TABLE_WORD, wordID);
        this.startActivity(intent);
    }
}
