package com.example.cibao.cibao.DomainModelClass;

import android.media.Image;

/**
 * 单词类
 * 2017/4/4
 * 屈彬
 */
public class Word {
    /**
     * 单词本身
     */
    String Spelling;
    public void setSpelling(String Spelling){this.Spelling = Spelling;}
    public String getSpelling(){return Spelling;}
    /**
     * @show 中文意思
     */
    String Meaning;
    public void setMeaning(String Meaning){this.Meaning = Meaning;}
    public String getMeaning(){return Meaning;}
    /**
     * @show 发信
     */
    String Pronunciation;
    public void setPronunciation(String Pronunciation){this.Pronunciation = Pronunciation;}
    public String getPronunciation(){return Pronunciation;}

    /**
     * @show 音标
     */
    String PhoneticSymbol;
    public void setPhoneticSymbol(String PhoneticSymbol){this.PhoneticSymbol = PhoneticSymbol;}
    public String getPhoneticSymbol(){return PhoneticSymbol;}

    /**
     * @show 图片
     */
    Image PictureOfWord;
    public void setPictureOfWord(Image PictureOfWord){this.PictureOfWord = PictureOfWord;}
    public Image getPictureOfWord(){return PictureOfWord;}

    /**
     * @show 所属用户
     */
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
