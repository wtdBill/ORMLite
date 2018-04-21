package com.example.yinpengpeng.tag;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by YinPengpeng on 2018/4/21/021.
 */
@DatabaseTable(tableName = "people")
public class People {
    /**
   * columnName  字段名
   * dataType 数据类型
   * defaultValue 字符串默认值
   * canBeNull 可否为空
   * id 主键
   * generatedId 自增主键
   * foreign 外联
   * unique 唯一
   * */
    //自增主键
    @DatabaseField(generatedId =true)
    public int id;
    @DatabaseField(columnName = "name")
    public String name;
    @DatabaseField(columnName = "sex")
    private String sex;
    @DatabaseField(columnName = "age")
    public int age;


    /**
     * 必须要有无参构造函数，否则报错
     */
    public People(){

    }

    public People(String name,String sex,int age){
        this.name=name;
        this.sex=sex;
        this.age=age;
    }
}
