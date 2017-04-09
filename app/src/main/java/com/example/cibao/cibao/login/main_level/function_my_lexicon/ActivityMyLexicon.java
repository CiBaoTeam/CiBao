package com.example.cibao.cibao.login.main_level.function_my_lexicon;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.cibao.cibao.DomainModelClass.Lexicon;
import com.example.cibao.cibao.Helpers.DBHelper;
import com.example.cibao.cibao.Helpers.StringHelper;
import com.example.cibao.cibao.R;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 屈彬
 * 2017/3/12
 * 功能-我的词库
 * 允许继承
 */
public class ActivityMyLexicon extends AppCompatActivity {

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
     * @show 控件键-词典名
     */
    final String LAYOUT_KEY_LEXICON_NAME = "LEXICON_NAME";
    /**
     * @show 控件键-词典描述
     */
    final String LAYOUT_KEY_DESCRIPTION = "DESCRIPTION";
    /**
     * @show 菜单第0个项
     */
    final int MENU_ITEM_0 = Menu.FIRST;
    /**
     * @show 菜单项第1项
     */
    final int MENU_ITEM_1 = Menu.FIRST + 1;
    /**
     * @show 词库刀
     */
    Dao<Lexicon, String> DaoLexicon;
    /**
     * @show 数据库操作助手
     */
    protected DBHelper MainDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lexicon);

        initializeDataBase();
        initializeWidget();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(MainDBHelper != null)MainDBHelper.close();
        if(DaoLexicon != null){
            try{
                DaoLexicon.closeLastIterator();
            }catch (SQLException sqlE){
                Log.e("onDestroy()", sqlE.toString());
            }
        }
    }
    // 列表
    /**
     * @show 初始化控件
     */
    void initializeWidget(){
        ListViewMain = (ListView)findViewById(R.id.MyLexicon_ListView_Dictionary);
        addItemToList();
    }

    /**
     * @show 向列表中添加项目
     */
    void addItemToList(){
        if(ListItems != null)ListItems.clear();
        initializeListAdapter();
        ListViewMain.setAdapter(ListAdapter);
    }

    /**
     * @show 初始化列表项目组
     */
    void initializeListItems(){
        ListItems = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> firstItemMap = new HashMap<String, Object>();
        firstItemMap.put(LAYOUT_KEY_LEXICON_NAME, DBHelper.DEFAULT_LEXICON_TABLE_NAME);
        firstItemMap.put(LAYOUT_KEY_DESCRIPTION, DBHelper.DEFAULT_LEXICON_DESCRIPTION);
        ListItems.add(firstItemMap);
        // 从数据库词典表中遍历获取表名
        if(DaoLexicon == null) {
            Toast.makeText(this, "数据库初始化失败!", Toast.LENGTH_LONG).show();
            return;
        }
        List<Lexicon> LexiconList = null;
        try{
            LexiconList = DaoLexicon.queryForAll();
        }catch (SQLException sqlE){
            Log.e(" initializeListItems()", sqlE.toString());
        }
        if(LexiconList == null)return;
        // 遍历词库表，添加词库项
        for(Lexicon lexicon : LexiconList){
            HashMap<String, Object> ItemMap = new HashMap<String, Object>();
            ItemMap.put(LAYOUT_KEY_LEXICON_NAME, lexicon.getName());
            ItemMap.put(LAYOUT_KEY_DESCRIPTION, lexicon.getDescription());
            ListItems.add(ItemMap);
        }
    }

    /**
     * @show 初始化列表适配器
     */
    void initializeListAdapter(){
        initializeListItems();
        if(ListItems == null)return;
        ListAdapter = new SimpleAdapter(this, ListItems, R.layout.layout_my_lexicon_list_item,
                new String[]{LAYOUT_KEY_LEXICON_NAME, LAYOUT_KEY_DESCRIPTION},
                new int[]{R.id.MyLexicon_ListItem_LexiconName, R.id.MyLexicon_ListItem_Description});
        ListAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                return false;
            }
        });
    }
    // 数据库

    /**
     * 数据库初始化
     */
    void initializeDataBase(){
        MainDBHelper = new DBHelper(this, Lexicon.class);
        try{
            DaoLexicon = MainDBHelper.createDao(Lexicon.class);
        }catch (SQLException sqlE){
            Log.e("initializeDataBase()", sqlE.toString());
        }
    }
    // 菜单
    /**
     * @show 创建选项菜单
     * @param menu 菜单
     * @return 是否创建成功
     */
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ITEM_0, 0, "添加").setIcon(android.R.drawable.ic_menu_add);
        // menu.add(0, MENU_ITEM_1, 0, "删除").setIcon(android.R.drawable.ic_menu_delete);
        return true;
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
                createEditTextDialog();
                break;
        }
        return true;
    }
    // 菜单项点击事件

    /**
     * @show 创建文本输入对话框
     */
    void createEditTextDialog(){

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        final View dialogLayout = layoutInflater.inflate(R.layout.layout_edit_lexicon, null);
        new AlertDialog.Builder(this)
                .setTitle("添加词库")
                .setIcon(android.R.drawable.ic_input_add)
                .setView(dialogLayout)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText lexiconName = (EditText) dialogLayout.findViewById(R.id.MyLexicon_EditText_LexiconName);
                        EditText lexiconDescription = (EditText) dialogLayout.findViewById(R.id.MyLexicon_EditText_Description);

                        Lexicon lexicon = new Lexicon();
                        lexicon.setName(lexiconName.getText().toString());
                        lexicon.setDescription(lexiconDescription.getText().toString());
                        //Toast.makeText(getApplicationContext(), "词库名: " + lexiconName.getText().toString(), Toast.LENGTH_LONG).show();
                        createNewLexicon(lexicon);
                        // 刷新界面
                        initializeWidget();
                    }
                })
                .show();
    }
    /**
     * @show 创建新的词库
     * @param lexicon 词库
     */
    void createNewLexicon(Lexicon lexicon){
        if(lexicon == null) return;
        if(StringHelper.isNullOrEmpty(lexicon.getName())){
            Toast.makeText(this, "词库名不能为空!", Toast.LENGTH_LONG).show();
            return;
        }
        // 检查词库名重复
        HashMap<String, Object> ItemMap = new HashMap<String, Object>();
        ItemMap.put(LAYOUT_KEY_LEXICON_NAME, lexicon.getName());
        ItemMap.put(LAYOUT_KEY_DESCRIPTION, lexicon.getDescription());
        if(ListItems.contains(ItemMap)){
            Toast.makeText(this, "该词库已存在!", Toast.LENGTH_LONG).show();
            return;
        }
        if(DaoLexicon == null){
            Toast.makeText(this, "数据库创建失败!", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            DaoLexicon.create(lexicon);
            Toast.makeText(this, "创建成功!", Toast.LENGTH_LONG).show();
        }catch (SQLException sqlE){
            Log.e("createNewLexicon()", sqlE.toString());
        }
    }
}
