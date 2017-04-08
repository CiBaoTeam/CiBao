package com.example.cibao.cibao.DomainModelClass;

import android.media.Image;

import com.j256.ormlite.field.DatabaseField;

/**
 * 单词类
 * 2017/4/4
 * 屈彬
 */
public class Word {
    /**
     * 单词本身
     */
    @DatabaseField(generatedId = true)
    String Spelling;
    public void setSpelling(String Spelling){this.Spelling = Spelling;}
    public String getSpelling(){return Spelling;}
    /**
     * @show 中文意思
     */
    @DatabaseField
    String Meaning;
    public void setMeaning(String Meaning){this.Meaning = Meaning;}
    public String getMeaning(){return Meaning;}
    /**
     * @show 发音
     */
    @DatabaseField
    String Pronunciation;
    public void setPronunciation(String Pronunciation){this.Pronunciation = Pronunciation;}
    public String getPronunciation(){return Pronunciation;}

    /**
     * @show 音标
     */
    @DatabaseField
    String PhoneticSymbol;
    public void setPhoneticSymbol(String PhoneticSymbol){this.PhoneticSymbol = PhoneticSymbol;}
    public String getPhoneticSymbol(){return PhoneticSymbol;}

    /**
     * @show 以BASE64编码保存的图片
     */
    @DatabaseField
    String PictureOfWord;
    public void setPictureOfWord(String PictureOfWord){this.PictureOfWord = PictureOfWord;}
    public String getPictureOfWord(){return PictureOfWord;}

    /**
     * @show 所属用户
     */
    @DatabaseField
    String UserID;
    public void setUserID(String UserID){this.UserID = UserID;}
    public String getUserID(){return UserID;}

    /**
     * @show 空构造函数
     */
    public Word(){}

    /**
     * @show 带参数构造函数
     * @param Spelling 单词本身
     */
    public Word(String Spelling){this.Spelling = Spelling;}
}
