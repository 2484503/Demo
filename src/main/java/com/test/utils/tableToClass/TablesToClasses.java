package com.test.utils.tableToClass;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesToClasses {

    //生成文件path
    private final static String generatedEntityFilesPath = "D:/GeneratedEntityFiles/modelFiles";

    //MySQL连接
    private final static String driverName = "com.mysql.jdbc.Driver";
    private final static String url = "jdbc:mysql://10.1.90.25:3307/lh_smart_mgt";
    private final static String username = "root";
    private final static String password = "lhtest12#@";

    /*
     * 连接数据库获取所有表信息
     */
    private static List<Table> getTables(String driverName, String url, String username, String password) {
        List<Table> tables = new ArrayList<Table>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection(url, username, password);
            //获取所有表名
            //String showTablesSql = "show tables";  //MySQL查询所有表格名称命令
            String showTablesSql = "";
            if (driverName.toLowerCase().indexOf("mysql") != -1) {
                showTablesSql = "show tables";  //MySQL查询所有表格名称命令
            } else if (driverName.toLowerCase().indexOf("sqlserver") != -1) {
                showTablesSql = "Select TABLE_NAME FROM edp.INFORMATION_SCHEMA.TABLES Where TABLE_TYPE='BASE TABLE'";  //SQLServer查询所有表格名称命令
            } else if (driverName.toLowerCase().indexOf("oracle") != -1) {
                showTablesSql = "select table_name from user_tables"; //ORACLE查询所有表格名称命令
            }
            ps = con.prepareStatement(showTablesSql);
            rs = ps.executeQuery();
            //循环生成所有表的表信息
            while (rs.next()) {
                tables.add(getTable(rs.getString(1).trim(), con));
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tables;
    }

    /*
     * 获取指定表信息并封装成Table对象
     * @param tableName
     * @param con
     */
    private static Table getTable(String tableName, Connection con) throws SQLException {
        Table table = new Table();
        table.setTableName(convertFirstLetterToUpperCase(tableName));
        table.setJavaName(generateDaoName(tableName));
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        ps = con.prepareStatement("show full columns from " + tableName);
        rs = ps.executeQuery();
        while (rs.next()) {
            table.getColumNames().add(generateJavaName(rs.getString("field").toLowerCase().trim()));
            table.getTablecolNames().add(rs.getString("field").toLowerCase().trim());
            table.getColumTypes().add(convertType(rs.getString("type")));
            table.getComments().add(rs.getString("comment"));

        }

        System.out.println(table.toString());
        rs.close();
        ps.close();
        return table;
    }

    /*
     * 将数据库的数据类型转换为java的数据类型
     */
    private static String convertType(String mysqlType) {
        String javaType = "";

        String mysqlTypeStr = mysqlType.trim().toLowerCase();
        if (mysqlTypeStr.indexOf("int") != -1) {
            javaType = "Integer";
        } else if (mysqlTypeStr.indexOf("tinyint") != -1) {
            javaType = "Integer";
        } else if (mysqlTypeStr.equals("char")) {
            javaType = "String";
        } else if (mysqlTypeStr.equals("number")) {
            javaType = "Integer";
        } else if (mysqlTypeStr.indexOf("varchar") != -1) {
            javaType = "String";
        } else if (mysqlTypeStr.equals("blob")) {
            javaType = "Byte[]";
        } else if (mysqlTypeStr.equals("float")) {
            javaType = "Float";
        } else if (mysqlTypeStr.equals("double")) {
            javaType = "Double";
        } else if (mysqlTypeStr.indexOf("decimal") != -1) {
            javaType = "BigDecimal";
        } else if (mysqlTypeStr.indexOf("bigint") != -1) {
            javaType = "Long";
        } else if (mysqlTypeStr.equals("date")) {
            javaType = "Date";
        } else if (mysqlTypeStr.equals("timestamp")) {
            javaType = "Date";
        } else if (mysqlTypeStr.equals("time")) {
            javaType = "Time";
        } else if (mysqlTypeStr.equals("datetime")) {
            javaType = "Date";
        } else if (mysqlTypeStr.equals("year")) {
            javaType = "Date";
        } else if (mysqlTypeStr.equals("longtext")) {
            javaType = "String";
        } else {
            javaType = "[unconverted]" + mysqlType;
        }

        return javaType;
    }

    /*
     * 生成指定表对象对应的类文件
     * @param table
     */
    private static void generateClassFile(Table table) {
        String tableName = table.getJavaName();
        List<String> columNames = table.getColumNames();
        List<String> columTypes = table.getColumTypes();
        List<String> columComments = table.getComments();
        //生成私有属性和get、set方法
        String propertiesStr = "";  //私有属性字符串
        String getterSetterStr = "";  //get、set方法字符串
        propertiesStr += "\r\n\tpublic static final String TABLE_NAME = \"" + convertFirstLetterToLowerCase(table.getTableName()) + "\";\r\n\r\n";
        propertiesStr += "\tprivate static final long serialVersionUID = 1L;\r\n";
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
            String columType = columTypes.get(i);
            String commet = columComments.get(i);
            propertiesStr += "\t /** \r\n";
            propertiesStr += "\t * \r\n";
            propertiesStr += "\t * " + commet + "\r\n";
            propertiesStr += "\t */ \r\n";
            propertiesStr += "\t" + "private " + columType + " " + columName + ";" + "\r\n";
            getterSetterStr +=
                    "\t" + "public " + columType + " "
                            + "get" + convertFirstLetterToUpperCase(columName) + "() {\r\n"
                            + "\t\t" + "return this." + columName + ";\r\n\t}"
                            + "\r\n\r\n"
                            + "\t" + "public void "
                            + "set" + convertFirstLetterToUpperCase(columName)
                            + "(" + columType + " " + columName + ") {\r\n"
                            + "\t\t" + "this." + columName + " = " + columName + ";\r\n\t}"
                            + "\r\n\r\n";
        }

        //生成无参构造方法和重写toString方法
        String str1 = "\t" + "public " + tableName + "() {}\r\n";  //无参构造方法
        String toStringStr = "\tpublic String toString() { \r\n\t\treturn ";  //toString方法
        for (int i = 0; i < columNames.size(); i++) {
            if (i == 0) {
                toStringStr += "\"" + columNames.get(i) + ":\" + " + columNames.get(i);
            } else {
                toStringStr += "\", " + columNames.get(i) + ":\" + " + columNames.get(i);
            }
            if (i + 1 != columNames.size()) {
                toStringStr += " + ";
            }
        }
        toStringStr += ";\r\n\t}\r\n";

        File folder = new File(generatedEntityFilesPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File classFile = new File(generatedEntityFilesPath + "/" + tableName + ".java");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
            bw.write("public class " + tableName + " implements Serializable{\r\n");
            bw.write(propertiesStr);
            /*bw.write("\r\n");
            bw.write(str1);
            bw.write("\r\n");
            bw.write(toStringStr);*/
            bw.write("\r\n");
            bw.write(getterSetterStr);
            bw.write("}");
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("生成类文件(" + tableName + ")出错！");
            e.printStackTrace();
        }

    }

    /*
     * 生成指定表对象对应的类文件
     * @param table
     */
    private static void generateDaoFile(Table table) {
        String tableName = table.getJavaName();
        List<String> columNames = table.getColumNames();
        List<String> tColNames = table.getTablecolNames();
        List<String> columTypes = table.getColumTypes();
        List<String> columComments = table.getComments();
        //生成私有属性和get、set方法
        String propertiesStr = "";  //私有属性字符串
        String mapperStr = "";  //get、set方法字符串
        propertiesStr += "\tprivate Logger LOG = Logger.getLogger(" + tableName + "Dao.class);\r\n\r\n";
        propertiesStr += "\t@Resource(name = \"jdbcTemplate\")\r\n";
        propertiesStr += "\tpublic void setJdbcTemplate(JdbcTemplate jdbcTemplate) {\r\n" +
                "\t\tthis.jdbcTemplate = jdbcTemplate;\r\n" +
                "\t};" + "\r\n";
        mapperStr += "\tpublic List<" + tableName + "> get" + tableName + "PaginationList(PaginationParamVo paginationParamVo) {\r\n" +
                "\t\tList<Object> params = new ArrayList<>();\r\n" +
                "\t\tStringBuilder sqlBuilder = new StringBuilder();\r\n" +
                "\t\tsqlBuilder.append(\"select * from \")\r\n" +
                "\t\t\t\t.append(" + tableName + ".TABLE_NAME);\r\n" +
                "\t\tsqlBuilder.append(\" limit ?, ?\");\r\n" +
                "\t\tparams.add(paginationParamVo.getOffset());\r\n" +
                "\t\tparams.add(paginationParamVo.getPagesize());\r\n" +
                "\t\tList<" + tableName + "> " + convertFirstLetterToLowerCase(tableName) + "List = super.queryList(sqlBuilder.toString(), new " + tableName + "Mapper(),\r\n" +
                "\t\t\t\tparams.toArray(new Object[params.size()]));\r\n" +
                "\r\n" +
                "\t\treturn " + convertFirstLetterToLowerCase(tableName) + "List;\r\n" +
                "\t}\r\n\r\n";
        mapperStr += "\tpublic long get" + tableName + "Count() {\r\n" +
                "\t\tStringBuilder sqlBuilder = new StringBuilder();\r\n" +
                "\t\tsqlBuilder.append(\"select count(*) from \")\r\n" +
                "\t\t\t\t.append(" + tableName + ".TABLE_NAME);\r\n" +
                "\t\tlong count = super.queryCount(sqlBuilder.toString());\r\n" +
                "\t\treturn count;\r\n" +
                "\t}\r\n\r\n";
        mapperStr += "\tprotected class " + tableName + "Mapper implements RowMapper<" + tableName + "> { \r\n";
        mapperStr += "\t\tpublic " + tableName + " mapRow(ResultSet rs, int rowNum) throws SQLException {\r\n";
        mapperStr += "\t\t\t" + tableName + " " + convertFirstLetterToLowerCase(tableName) + " = new " + tableName + "();\r\n";
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
            String tColName = tColNames.get(i);
            String columType = columTypes.get(i);
            mapperStr += "\t\t\t\t" + convertFirstLetterToLowerCase(tableName) + ".set" + convertFirstLetterToUpperCase(columName) + "(rs.get" + ("Integer".equals(columType) ? "Int" : columType) + "(\"" + tColName + "\"));\r\n";

        }
        mapperStr += "\t\t\t\treturn " + convertFirstLetterToLowerCase(tableName) + ";\r\n";
        mapperStr += "\t\t}\r\n";
        mapperStr += "\t}\r\n";
        //生成无参构造方法和重写toString方法
        String str1 = "\t" + "public " + tableName + "() {}\r\n";  //无参构造方法
        String toStringStr = "\tpublic String toString() { \r\n\t\treturn ";  //toString方法

        toStringStr += ";\r\n\t}\r\n";

        File folder = new File(generatedEntityFilesPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File classFile = new File(generatedEntityFilesPath + "/" + tableName + "Dao.java");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
            bw.write("");
            bw.write("public class " + tableName + "Dao  extends BaseDao<" + tableName + "> {\r\n");
            bw.write(propertiesStr);
            /*bw.write("\r\n");
            bw.write(str1);
            bw.write("\r\n");
            bw.write(toStringStr);*/
            bw.write("\r\n");
            bw.write(mapperStr);
            bw.write("}");
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("生成类文件(" + tableName + ")出错！");
            e.printStackTrace();
        }

    }

    /*
     * 生成指定表对象对应的类文件
     * @param table
     */
    private static void generateUlFile(Table table) {
        String tableName = table.getJavaName();
        List<String> columNames = table.getColumNames();
        List<String> tColNames = table.getTablecolNames();
        List<String> columTypes = table.getColumTypes();
        List<String> columComments = table.getComments();
        //生成私有属性和get、set方法
        String propertiesStr = "";  //私有属性字符串
        String mapperStr = "";  //get、set方法字符串
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
            String columType = columTypes.get(i);
            String commet = columComments.get(i);
            mapperStr += "\t *@apiParam {" + columType + "}  [" + columName + "]  " + commet + "\r\n";

        }

        mapperStr += "\t *<ul>\r\n";
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
            String tColName = tColNames.get(i);
            String commet = columComments.get(i);
            mapperStr += "\t *<li>" + columName + ":" + commet + "</li>\r\n";

        }
        mapperStr += "\t *</ul>";

        File folder = new File(generatedEntityFilesPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File classFile = new File(generatedEntityFilesPath + "/" + tableName + "UL.txt");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
            bw.write("");
            bw.write(mapperStr);
            bw.write("\r\n");
            bw.write("\r\n");
            bw.write("\r\n");
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("生成类文件(" + tableName + ")出错！");
            e.printStackTrace();
        }

    }

    /*
     * 首字母大写
     */
    private static String convertFirstLetterToUpperCase(String letter) {
        return letter.substring(0, 1).toUpperCase() + letter.substring(1, letter.length());
    }

    /*
     * 首字母小写
     */
    private static String convertFirstLetterToLowerCase(String letter) {
        if (StringUtils.isEmpty(letter)) {
            return letter;
        }

        return letter.substring(0, 1).toLowerCase() + letter.substring(1, letter.length());
    }

    public static String generateJavaName(String colname) {
        String[] nameList = colname.split("_");
        String name = "";
        for (int i = 0; i < nameList.length; i++) {
            name += convertFirstLetterToUpperCase(nameList[i]);
        }
        return convertFirstLetterToLowerCase(name);
    }

    public static String generateDaoName(String tabname) {
        String[] nameList = tabname.split("_");
        String name = "";
        for (int i = 0; i < nameList.length; i++) {
            name += convertFirstLetterToUpperCase(nameList[i]);
        }
        return name;
    }

    public static void main(String[] args) {
        List<Table> tables = new ArrayList<Table>();

        tables = getTables(driverName, url, username, password);
        System.out.println("Generating...");
        for (int i = 0; i < tables.size(); i++) {
            generateClassFile(tables.get(i));
            generateDaoFile(tables.get(i));
            generateUlFile(tables.get(i));
        }
        System.out.println("Generate Success!");
    }

}