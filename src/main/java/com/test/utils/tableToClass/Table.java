package com.test.utils.tableToClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格对象
 */
class Table {
    private String tableName;  //表名(首字母大写)
    private String javaName;  //表名(首字母大写)
    private List<String> columNames = new ArrayList<String>(); //列名集合
    private List<String> tablecolNames = new ArrayList<String>(); //列名集合

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    private List<String> columTypes = new ArrayList<String>();  //列类型集合，列类型严格对应java类型，如String不能写成string，与列名一一对应
    private List<String> columComments = new ArrayList<String>();

    public String toString() {
        String tableStr = "";
        tableStr = tableStr + tableName + "\r\n";

        //遍历列集合
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
            String columType = columTypes.get(i);
            String comments = columComments.get(i);
            tableStr += "  " + columName + ":  " + columType + "  : " + comments + "\r\n";
        }

        return tableStr;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getColumNames() {
        return columNames;
    }

    public void setColumNames(List<String> columNames) {
        this.columNames = columNames;
    }

    public List<String> getColumTypes() {
        return columTypes;
    }

    public void setColumTypes(List<String> columTypes) {
        this.columTypes = columTypes;
    }

    public List<String> getComments() {
        return columComments;
    }

    public void setComments(List<String> comments) {
        this.columComments = comments;
    }

    public List<String> getTablecolNames() {
        return tablecolNames;
    }

    public void setTablecolNames(List<String> tablecolNames) {
        this.tablecolNames = tablecolNames;
    }
}