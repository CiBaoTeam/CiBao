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

import com.example.cibao.cibao.Helpers.Base64Helper;
import com.example.cibao.cibao.Helpers.BitmapHelper;
import com.example.cibao.cibao.Helpers.DBHelper;
import com.example.cibao.cibao.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_to_lexicon);

        initializeGlobalVar();
        initializeWidget();
    }

    /**
     * @show 初始化全局变量
     */
    protected void initializeGlobalVar(){
        Intent getLexiconID = this.getIntent();
        try{
            ParentLexiconID = getLexiconID.getIntExtra(DBHelper.TABLE_LEXICON, 0);
            isEditMode = getLexiconID.getBooleanExtra(EDIT_STATUS, false);
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

            }
        });
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
                    WordImageSerial = Base64Helper.getBase64CodeFromBitmap(WordImage);
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
