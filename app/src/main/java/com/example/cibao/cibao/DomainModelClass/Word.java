package com.example.cibao.cibao.DomainModelClass;



import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 单词类
 * 2017/4/4
 * 屈彬
 */
@DatabaseTable(tableName = "TABLE_WORD")
public class Word {
    /**
     * 单词本身
     */
    @DatabaseField(id = true, columnName = "SPELLING")
    String Spelling;
    public void setSpelling(String Spelling){this.Spelling = Spelling;}
    public String getSpelling(){return Spelling;}
    /**
     * @show 中文意思
     */
    @DatabaseField(columnName = "MEANING")
    String Meaning;
    public void setMeaning(String Meaning){this.Meaning = Meaning;}
    public String getMeaning(){return Meaning;}
    /**
     * @show 发音
     */
    @DatabaseField(columnName = "PRONUNCIATION")
    String Pronunciation;
    public void setPronunciation(String Pronunciation){this.Pronunciation = Pronunciation;}
    public String getPronunciation(){return Pronunciation;}

    /**
     * @show 音标
     */
    @DatabaseField(columnName = "PHONETIC_SYMBOL")
    String PhoneticSymbol;
    public void setPhoneticSymbol(String PhoneticSymbol){this.PhoneticSymbol = PhoneticSymbol;}
    public String getPhoneticSymbol(){return PhoneticSymbol;}

    /**
     * @show 以BASE64编码保存的图片
     */
    @DatabaseField(columnName = "PICTURE_OF_WORD")
    String PictureOfWord;
    public void setPictureOfWord(String PictureOfWord){this.PictureOfWord = PictureOfWord;}
    public String getPictureOfWord(){return PictureOfWord;}

    /**
     * @show 所属用户
     */
    @DatabaseField(columnName = "USER_ID")
    String UserID;
    public void setUserID(String UserID){this.UserID = UserID;}
    public String getUserID(){return UserID;}

    /**
     * @show 属于哪一个词库
     */
    @DatabaseField(columnName = "LEXICON_NAME")
    String LexiconBelonged;
    public void setLexiconBelonged(String LexiconBelonged){this.LexiconBelonged = LexiconBelonged;}
    public String getLexiconBelonged(){return LexiconBelonged;}
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
