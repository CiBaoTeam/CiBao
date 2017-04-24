package com.example.cibao.cibao.login.main_level.function_my_lexicon.sub_function;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cibao.cibao.DomainModelClass.Word;
import com.example.cibao.cibao.DomainModelClass.WordSelectTable;
import com.example.cibao.cibao.Helpers.Base64Helper;
import com.example.cibao.cibao.Helpers.BitmapHelper;
import com.example.cibao.cibao.Helpers.DBHelper;
import com.example.cibao.cibao.Helpers.StringHelper;
import com.example.cibao.cibao.R;
import com.j256.ormlite.dao.Dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * 屈彬
 * 2017/3/12
 * 添加单词到词库
 */
public class ActivityAddWordToLexicon extends AppCompatActivity {

    /**
     * @show 父词库ID
     */
    protected int ParentLexiconID;
    /**
     * @show 判断当前活动是创建活动还是编辑活动
     */
    protected boolean isEditMode = false;
    /**
     * @show 编辑模式标志
     */
    public static final String EDIT_STATUS = "EDIT_STATUS";
    // 单词变量
    /**
     * @show 单词图片
     */
    Bitmap WordImage;
    /**
     * @show 单词图片BASE64序列
     */
    String WordImageSerial;
    // 控件
    /**
     * 单词编辑框
     */
    EditText EditText_Spelling;
    /**
     * @show 中文解释编辑框
     */
    EditText EditText_Meaning;
    /**
     * @show 音标编辑框
     */
    EditText EditText_PhoneticSymbol;
    /**
     * @show 图片编辑按钮
     */
    ImageButton ImageButton_AddImage;
    /**
     * @show 确认按钮
     */
    Button Button_Confirm;
    // 数据库类
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
     * @show 传入的单词ID
     */
    int WordID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_to_lexicon);

        initializeGlobalVar();
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
    protected void initializeGlobalVar(){
        Intent getLexiconID = this.getIntent();
        try{
            ParentLexiconID = getLexiconID.getIntExtra(DBHelper.TABLE_LEXICON, 0);
            isEditMode = getLexiconID.getBooleanExtra(EDIT_STATUS, false);
            WordID = getLexiconID.getIntExtra(DBHelper.TABLE_WORD, 0);
        }catch (Exception e){
            Log.e("initializeGlobalVar", e.toString());
        }
    }

    /**
     * @show 初始化控件
     */
    protected void initializeWidget(){
        EditText_Spelling = (EditText) findViewById(R.id.AddWord_EditText_Spelling);
        EditText_Meaning = (EditText) findViewById(R.id.AddWord_EditText_Meaning);
        EditText_PhoneticSymbol = (EditText) findViewById(R.id.AddWord_EditText_PhoneticSymbol);
        ImageButton_AddImage = (ImageButton) findViewById(R.id.AddWord_ImageButton_AddImage);
        Button_Confirm = (Button) findViewById(R.id.AddWord_Button_Confirm);

        // 添加点击事件
        ImageButton_AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_AddImage_Click();
            }
        });
        Button_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Confirm_Click();
            }
        });
        // 判断是否为编辑模式
        if(isEditMode){
            loadWordInfo();
        }
    }

    /**
     * @show 加载单词信息
     */
    void loadWordInfo(){
        if(DaoWord == null){
            Toast.makeText(getApplicationContext(), "数据库初始化失败!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            Word word = DaoWord.queryForEq("WORD_ID", WordID).get(0);
            EditText_Spelling.setText(word.getSpelling());
            EditText_Meaning.setText(word.getMeaning());
            EditText_PhoneticSymbol.setText(word.getPhoneticSymbol());
            ImageButton_AddImage.setImageBitmap(Base64Helper.getBitmapFromBase64Code(word.getPictureOfWord()));
        }catch (SQLException sqlE){
            Log.e("loadWordInfo()", sqlE.toString());
        }
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
     * @show 确认按钮点击事件
     */
    void btn_Confirm_Click(){
        // 保存数据
        String spelling = EditText_Spelling.getText().toString();
        if(StringHelper.isNullOrEmpty(spelling)){
            Toast.makeText(getApplicationContext(), "单词不能为空!", Toast.LENGTH_LONG).show();
            return;
        }
        Word word = new Word(spelling);
        word.setMeaning(EditText_Meaning.getText().toString());
        word.setPhoneticSymbol(EditText_PhoneticSymbol.getText().toString());
        Bitmap bitmap;
        if(WordImage != null){
            bitmap = WordImage;
        }else{
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.image_add);
            bitmap = BitmapHelper.BitmapMatrix.resizeImage(bitmap, BitmapHelper.BitmapMatrix.BitmapSize, BitmapHelper.BitmapMatrix.BitmapSize);
        }
        WordImageSerial = Base64Helper.getBase64CodeFromBitmap(bitmap);
        word.setPictureOfWord(WordImageSerial);
        // 保存到数据库
        if(DaoWord == null || DaoSelectTable == null){
            Toast.makeText(getApplicationContext(), "数据库访问失败!", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            if(isEditMode){
                word.setID(WordID);
                DaoWord.update(word);
            }else {
                if(DaoWord.queryForEq("SPELLING", word.getSpelling()).get(0) != null){
                    Toast.makeText(getApplicationContext(), "该单词在数据库中已存在，不可重复添加!", Toast.LENGTH_LONG).show();
                    return;
                }
                DaoWord.create(word);
                // 更新选词表
                WordSelectTable wst = new WordSelectTable();
                wst.setLexiconID(ParentLexiconID);
                word = DaoWord.queryForEq("SPELLING", word.getSpelling()).get(0);
                wst.setWordID(word.getID());
                DaoSelectTable.create(wst);
            }
            Toast.makeText(getApplicationContext(), "添加成功!", Toast.LENGTH_LONG).show();
            finish();
        }catch (SQLException sqlE){
            Toast.makeText(getApplicationContext(), "单词创建失败!", Toast.LENGTH_LONG).show();
            Log.e("confirm()", sqlE.toString());
        }
    }
    /**
     * @show 添加图片时的事件
     */
    public void btn_AddImage_Click(){
        Intent openAlbumIntent = new Intent(
                Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, 0);
    }

    /**
     * 选择图片文件后的返回事件
     * @param requestCode 请求码
     * @param resultCode 结果
     * @param data 数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            if(data != null){
                try {
                    InputStream stream = getContentResolver().openInputStream(data.getData());
                    WordImage = BitmapFactory.decodeStream(stream);
                    stream.close();
                    // 缩放图片
                    WordImage = BitmapHelper.BitmapMatrix.resizeImage(WordImage, BitmapHelper.BitmapMatrix.BitmapSize, BitmapHelper.BitmapMatrix.BitmapSize);
                    // 转存为BASE64序列
                    //WordImageSerial = Base64Helper.getBase64CodeFromBitmap(WordImage);
                    ImageButton_AddImage.setImageBitmap(WordImage);
                } catch (FileNotFoundException e) {
                    Log.e("read Pic", e.toString());
                } catch (IOException e) {
                    Log.e("read Pic", e.toString());
                }
            }
        }
    }
}
