package com.example.cibao.cibao.login.main_level.function_my_lexicon;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.cibao.cibao.DomainModelClass.Lexicon;
import com.example.cibao.cibao.Helpers.DBHelper;
import com.example.cibao.cibao.Helpers.StringHelper;
import com.example.cibao.cibao.R;
import com.example.cibao.cibao.login.main_level.function_my_lexicon.sub_function.ActivityLexiconTable;
import com.j256.ormlite.dao.Dao;

import java.lang.reflect.Method;
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
     * @show 项目点击监听
     */
    protected AdapterView.OnItemClickListener MainItemClickListener;
    /**
     * @show 项目长按事件
     */
    protected AdapterView.OnItemLongClickListener MainItemLongClickListener;
    /**
     * @show 控件键-词典名
     */
    final String LAYOUT_KEY_LEXICON_NAME = "LEXICON_NAME";
    /**
     * @show 控件键-词典描述
     */
    final String LAYOUT_KEY_DESCRIPTION = "DESCRIPTION";
    /**
     * @show 键-词库编号
     */
    final String KEY_LEXICON_ID = "LEXICON_ID";
    /**
     * @show 菜单第0个项
     */
    protected final int MENU_ITEM_0 = Menu.FIRST;
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
    DBHelper MainDBHelper;
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
        refreshList();
        ListViewMain.setAdapter(ListAdapter);
        setMainItemClickListener();
        ListViewMain.setOnItemClickListener(MainItemClickListener);
        setMainItemLongClickListener();
        ListViewMain.setOnItemLongClickListener(MainItemLongClickListener);
    }

    /**
     * @show 刷新列表项
     */
    void refreshList(){
        if(ListItems != null)ListItems.clear();
        initializeListAdapter();
        ListViewMain.setAdapter(ListAdapter);
    }

    /**
     * @show 初始化列表项目组
     */
    void initializeListItems(){
        ListItems = new ArrayList<>();
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
            HashMap<String, Object> ItemMap = new HashMap<>();
            ItemMap.put(LAYOUT_KEY_LEXICON_NAME, lexicon.getName());
            ItemMap.put(LAYOUT_KEY_DESCRIPTION, lexicon.getDescription());
            ItemMap.put(KEY_LEXICON_ID, lexicon.getID());
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

    /**
     * @show 设置项目点击事件监听器
     */
    void setMainItemClickListener(){
        MainItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                forwardToLexiconTableActivity(position);
            }
        };
    }

    /**
     * @show 设置项目长按事件
     */
    void setMainItemLongClickListener(){
        MainItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(ListItems.get(position).get(LAYOUT_KEY_LEXICON_NAME).toString())
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setItems(new String[]{"打开词库", "编辑词库", "删除词库"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Toast.makeText(getApplicationContext(), "" + which, Toast.LENGTH_SHORT).show();
                                switch (which){
                                    case 0:
                                        forwardToLexiconTableActivity(position);
                                        break;
                                    case 1:
                                        createEditTextDialog(true, position, "添加或修改词库");
                                        break;
                                    case 2:
                                        new AlertDialog.Builder(view.getContext())
                                                .setTitle("确认删除吗?")
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setNegativeButton("取消", null)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Lexicon lexicon = new Lexicon();
                                                        lexicon.setID(Integer.parseInt(ListItems.get(position).get(KEY_LEXICON_ID).toString()));
                                                        lexicon.setName(ListItems.get(position).get(LAYOUT_KEY_LEXICON_NAME).toString());
                                                        lexicon.setDescription(ListItems.get(position).get(LAYOUT_KEY_DESCRIPTION).toString());
                                                        deleteLexicon(lexicon);
                                                        // 刷新界面
                                                        refreshList();
                                                    }
                                                })
                                                .show();
                                        break;
                                }
                            }
                        })
                        .show();
                return true;
            }
        };
    }
    // 项目点击事件

    /**
     * @show 进入单词浏览界面
     * @param lexiconPosition 词库在列表中的位置
     */
    void forwardToLexiconTableActivity(int lexiconPosition){
        Intent LexiconTableIntent = new Intent();
        LexiconTableIntent.setClass(getApplicationContext(), ActivityLexiconTable.class);
        // 传送选中的词表名
        LexiconTableIntent.putExtra(DBHelper.TABLE_LEXICON, (int)ListItems.get(lexiconPosition).get(KEY_LEXICON_ID));
        startActivity(LexiconTableIntent);
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
        setIconEnable(menu, true);
        menu.add(0, MENU_ITEM_0, 0, "添加词库").setIcon(android.R.drawable.ic_menu_add);
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
                createEditTextDialog(false, 0, "添加或修改词库");
                break;
        }
        return true;
    }

    // 菜单项点击事件

    /**
     * @show 创建文本输入对话框
     * @param editMode 是否是编辑模式
     */
    void createEditTextDialog(final boolean editMode, final int position, final String title){

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        final View dialogLayout = layoutInflater.inflate(R.layout.layout_edit_lexicon, null);
        final EditText lexiconName = (EditText) dialogLayout.findViewById(R.id.MyLexicon_EditText_LexiconName);
        final EditText lexiconDescription = (EditText) dialogLayout.findViewById(R.id.MyLexicon_EditText_Description);
        if(editMode){
            lexiconName.setText(ListItems.get(position).get(LAYOUT_KEY_LEXICON_NAME).toString());
            lexiconDescription.setText(ListItems.get(position).get(LAYOUT_KEY_DESCRIPTION).toString());
        }
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setIcon(android.R.drawable.ic_input_add)
                .setView(dialogLayout)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Lexicon lexicon = new Lexicon();

                        lexicon.setName(lexiconName.getText().toString());
                        lexicon.setDescription(lexiconDescription.getText().toString());
                        //Toast.makeText(getApplicationContext(), "词库名: " + lexiconName.getText().toString(), Toast.LENGTH_LONG).show();
                        if(editMode){
                            lexicon.setID(Integer.parseInt(ListItems.get(position).get(KEY_LEXICON_ID).toString()));
                            editLexicon(lexicon, position);
                        }
                        else createNewLexicon(lexicon);
                    }
                })
                .show();
    }

    /**
     * @show 编辑词库
     * @param lexicon 词库
     * @param position 词库在列表中的位置
     */
    void editLexicon(Lexicon lexicon, int position){
        if(lexicon == null) return;
        if(StringHelper.isNullOrEmpty(lexicon.getName())){
            Toast.makeText(this, "词库名不能为空!", Toast.LENGTH_LONG).show();
            return;
        }
        // 检查词库名重复
        HashMap<String, Object> ItemMap = new HashMap<String, Object>();
        ItemMap.put(LAYOUT_KEY_LEXICON_NAME, lexicon.getName());
        ItemMap.put(LAYOUT_KEY_DESCRIPTION, lexicon.getDescription());
        if(ListItems.contains(ItemMap) && ListItems.get(position).get(LAYOUT_KEY_LEXICON_NAME).toString().equals(lexicon.getName())){
            Toast.makeText(this, "词库名不能重复!", Toast.LENGTH_LONG).show();
            return;
        }
        if(DaoLexicon == null){
            Toast.makeText(this, "数据库访问失败!", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            DaoLexicon.update(lexicon);
            Toast.makeText(this, "修改成功!", Toast.LENGTH_LONG).show();
        }catch (SQLException sqlE){
            Log.e("editLexicon()", sqlE.toString());
        }
        // 刷新界面
        refreshList();
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
            Toast.makeText(this, "词库名不能重复!", Toast.LENGTH_LONG).show();
            return;
        }
        if(DaoLexicon == null){
            Toast.makeText(this, "数据库访问失败!", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            DaoLexicon.create(lexicon);
            Toast.makeText(this, "创建成功!", Toast.LENGTH_LONG).show();
        }catch (SQLException sqlE){
            Log.e("createNewLexicon()", sqlE.toString());
        }
        // 刷新界面
        refreshList();
    }

    /**
     * @show 删除词库
     * @param lexicon 词库
     */
    void deleteLexicon(Lexicon lexicon){
        if(lexicon == null)return;
        if(DaoLexicon == null){
            Toast.makeText(this, "数据库访问失败!", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            DaoLexicon.delete(lexicon);
            Toast.makeText(this, "删除成功!", Toast.LENGTH_LONG).show();
        }catch (SQLException sqlE){
            Log.e("createNewLexicon()", sqlE.toString());
        }
        // 刷新界面
        refreshList();
    }
}
