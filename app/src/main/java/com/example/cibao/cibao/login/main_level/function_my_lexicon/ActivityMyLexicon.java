package com.example.cibao.cibao.login.main_level.function_my_lexicon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.cibao.cibao.Helpers.DBHelper;
import com.example.cibao.cibao.R;

import java.util.ArrayList;
import java.util.HashMap;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lexicon);

        initializeWidget();
    }

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
        // 从数据库中获取表名
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
    // 数据库操作应专门开线程来执行，用Handler来处理主线程事件
}
