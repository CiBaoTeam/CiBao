package com.example.cibao.cibao.DomainModelClass;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 屈彬
 * 2017/4/9
 * 选词表，类似于选课表
 */
@DatabaseTable(tableName = "TABLE_WORD_SELECT")
public class WordSelectTable {
    /**
     * @show 行ID
     */
    @DatabaseField(id = true, generatedId = true, columnName = "ID")
    protected long Id;
    public void setId(long Id){this.Id = Id;}
    public long getId(){return Id;}
    /**
     * @show 词典编号
     */
    @DatabaseField(columnName = "LEXICON_ID")
    protected int LexiconID;
    public void setLexiconID(int LexiconID){this.LexiconID = LexiconID;}
    public int getLexiconID(){return LexiconID;}

    /**
     * @show 单词编号
     */
    @DatabaseField(columnName = "WORD_ID")
    protected int WordID;
    public void setWordID(int WordID){this.WordID = WordID;}
    public int getWordID(){return  WordID;}

    /**
     * @show 空构造函数
     */
    public WordSelectTable(){}

    /**
     * @show 带参数的构造函数
     * @param Id 行ID
     */
    public WordSelectTable(long Id){this.Id = Id;}
}
