package com.example.cibao.cibao.DomainModelClass;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 屈彬
 * 2017/4/9
 * 词典
 */
@DatabaseTable(tableName = "TABLE_LEXICON")
public class Lexicon {
    /**
     * @show 词库编号
     */
    @DatabaseField(generatedId = true, columnName = "LEXICON_ID")
    protected int ID;

    public void setID(int ID){this.ID = ID;}
    public int getID(){return ID;}
    /**
     * @show 词典名
     */
    @DatabaseField(columnName = "LEXICON_NAME", index = true)
    protected String Name;

    /**
     * @show 设置词典名称
     * @param Name 名称
     */
    public void setName(String Name){this.Name = Name;}

    /**
     * @show 获取词典名称
     * @return 词典名称
     */
    public String getName(){return Name;}
    /**
     * @show 词典描述
     */
    @DatabaseField(columnName = "LEXICON_DESCRIPTION")
    protected String Description;

    /**
     * @show 设置词典描述
     * @param Description 描述
     */
    public void setDescription(String Description){this.Description = Description;}

    /**
     * @show 获取词典描述
     * @return 描述
     */
    public String getDescription(){return Description;}

    /**
     * @show 空构造函数
     */
    public Lexicon(){}

    /**
     * @show 带参数构造函数
     * @param ID ID
     */
    public Lexicon(int ID){this.ID = ID;}
}
