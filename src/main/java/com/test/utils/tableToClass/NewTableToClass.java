package com.test.utils.tableToClass;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewTableToClass {

    //生成文件path
    private final static String generatedEntityFilesPath = "D:/GeneratedEntityFiles/modelFiles";

    //MySQL连接
    private final static String driverName = "com.mysql.jdbc.Driver";
    private final static String url = "jdbc:mysql://10.1.90.25:3307/lh_smart_mgt";
    private final static String database = "lh_smart_mgt";
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
        if (mysqlTypeStr.indexOf("tinyint") != -1) {
            javaType = "Integer";
        } else if (mysqlTypeStr.indexOf("int") != -1) {
            javaType = "Long";
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
     * 生成model文件
     * @param table
     */
    private static void generateModelFile(Table table) {
        String tableName = table.getJavaName();
        List<String> columNames = table.getColumNames();
        List<String> columTypes = table.getColumTypes();
        List<String> columComments = table.getComments();
        //生成私有属性和get、set方法
        String propertiesStr = "";  //私有属性字符串

        propertiesStr += "private static final long serialVersionUID = 1L;\r\n";
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
            String columType = columTypes.get(i);
            String commet = columComments.get(i);

            propertiesStr += "\t /** \r\n";
            propertiesStr += "\t * \r\n";
            propertiesStr += "\t * " + commet + "\r\n";
            propertiesStr += "\t */ \r\n";
            propertiesStr += "private " + columType + " " + columName + ";\r\n";
        }
        propertiesStr += "private transient boolean persisted;";
        propertiesStr += "public " + tableName + "(){}";
        propertiesStr += "public boolean isNew() { return this.id == null; }";
        propertiesStr += "public void setPersisted(Boolean persisted) { this.persisted = persisted; }";
        propertiesStr += "public Boolean getPersisted() { return this.persisted; }";
        propertiesStr += "@Override public String toString() { return ToStringBuilder.reflectionToString(this); }}";

        File folder = new File(generatedEntityFilesPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File classFile = new File(generatedEntityFilesPath + "/" + tableName + ".java");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
            bw.write("public class " + tableName + " implements Persistable<Long> {");
            bw.write(propertiesStr);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("生成model文件(" + tableName + ")出错！");
            e.printStackTrace();
        }

    }

    /*
     * 生成指定表DB文件
     * @param table
     */
    private static void generateDBFile(Table table) {
        String tableName = table.getJavaName();
        List<String> columNames = table.getColumNames();
        List<String> columTypes = table.getColumTypes();
        List<String> tablecolNames = table.getTablecolNames();
        //生成私有属性和get、set方法
        String propertiesStr = "";  //私有属性字符串
        propertiesStr += "private static String TABLE_NAME = \"" + table.getTableName().toUpperCase() + "\";";
        propertiesStr += "private static String TABLE_ALIAS = \"" + database + "\";";
        propertiesStr += "public static String getTableName(){return TABLE_NAME.toLowerCase();}";
        propertiesStr += "public static String getTableAlias() { return TABLE_NAME + \" as \" + TABLE_ALIAS; }";
        propertiesStr += "public static String getAlias() { return TABLE_ALIAS; }";
        propertiesStr += "public static String selectAllColumns(boolean ... useAlias) { return (useAlias[0] ? TABLE_ALIAS : TABLE_NAME) + \".*\"; }";
        propertiesStr += "public enum COLUMNS{\r\n";
        for (String colum : tablecolNames) {
            propertiesStr += colum.toUpperCase() + "(\"" + colum + "\"),\r\n";
        }
        propertiesStr += ";\r\n";
        propertiesStr += "private String columnName; ";
        propertiesStr += "private COLUMNS (String columnName) { this.columnName = columnName; }";
        propertiesStr += "public void setColumnName (String columnName) { this.columnName = columnName; }";
        propertiesStr += "public String getColumnName () { return this.columnName; }";
        propertiesStr += "public String getColumnAlias () { return TABLE_ALIAS + \".\" + this.columnName; }";
        propertiesStr += "public String getColumnAliasAsName () { return TABLE_ALIAS + \".\" + this.columnName + \" as \" + TABLE_ALIAS + \"_\" + this.columnName; } ";
        propertiesStr += "public String getColumnAliasName () { return TABLE_ALIAS + \"_\" + this.columnName; } }";
        propertiesStr += "public " + tableName + "DB (){}";
        propertiesStr += "public static final RowMapper<" + tableName + "> ROW_MAPPER = new " + tableName + "RowMapper ();";
        propertiesStr += "public static final class  " + tableName + "RowMapper implements RowMapper<" + tableName + ">{";
        propertiesStr += "public " + tableName + " mapRow(ResultSet rs, int rowNum) throws SQLException{ ";
        propertiesStr += tableName + " obj = new " + tableName + "();";
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
            String columType = columTypes.get(i);
            String tablecolName = tablecolNames.get(i);
            propertiesStr += "obj.set" + convertFirstLetterToUpperCase(columName) + "(rs.get" + ("Integer".equals(columType) ? "Int" : columType) + "(COLUMNS." + tablecolName.toUpperCase() + ".getColumnName()));";
        }
        propertiesStr += "return obj;}}";

        propertiesStr += "public static final RowUnmapper<" + tableName + "> ROW_UNMAPPER = new " + tableName + "RowUnmapper ();";
        propertiesStr += "public static final class " + tableName + "RowUnmapper implements RowUnmapper<" + tableName + ">{";
        propertiesStr += "public Map<String, Object> mapColumns(" + tableName + " " + convertFirstLetterToLowerCase(tableName) + "){";
        propertiesStr += "Map<String, Object> mapping = new LinkedHashMap<String, Object>();";
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
            String tablecolName = tablecolNames.get(i);
            propertiesStr += "mapping.put(COLUMNS." + tablecolName.toUpperCase() + ".getColumnName(), " + convertFirstLetterToLowerCase(tableName) + ".get" + convertFirstLetterToUpperCase(columName) + "());";
        }
        propertiesStr += "return mapping;}}";
        propertiesStr += "public static StringBuffer getAllColumnAliases (){ StringBuffer strBuf = new StringBuffer (); int i = COLUMNS.values ().length; for (COLUMNS c : COLUMNS.values ()) { strBuf.append (c.getColumnAliasAsName ()); if (--i > 0) strBuf.append (\", \"); } return strBuf; }}";

        File folder = new File(generatedEntityFilesPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File classFile = new File(generatedEntityFilesPath + "/" + tableName + "DB.java");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
            bw.write("import java.sql.ResultSet; import java.sql.SQLException; import java.sql.Timestamp; import java.util.LinkedHashMap; import java.util.Map; import org.springframework.jdbc.core.RowMapper; import com.nurkiewicz.jdbcrepository.RowUnmapper;");
            bw.write("public class " + tableName + "DB {");
            bw.write(propertiesStr);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("生成DB文件(" + tableName + ")出错！");
            e.printStackTrace();
        }

    }

    /*
     * 生成指定表Repository文件
     * @param table
     */
    private static void generateRepositoryFile(Table table) {
        String tableName = table.getJavaName();
        //生成私有属性和get、set方法
        String propertiesStr = "";  //私有属性字符串
        propertiesStr += "private Logger LOG = Logger.getLogger(" + tableName + "Repository.class);";
        propertiesStr += "@Resource(name = \"jdbcTemplate\")private JdbcOperations jdbcOperations;";
        propertiesStr += "private JdbcRepository<" + tableName + ", Long> jdbcRepository;";
        propertiesStr += "public void setTargetTable(" + tableName + " " + tableName + ") { jdbcRepository = new JdbcRepository<" + tableName + ", Long>( " + tableName + "DB.ROW_MAPPER, " + tableName + "DB.ROW_UNMAPPER, " + tableName + "DB.getTableName()); jdbcRepository.setJdbcOperations(jdbcOperations); }";
        propertiesStr += "protected JdbcOperations getJdbcOperations() { return this.jdbcOperations; }";
        propertiesStr += "public " + tableName + "Repository() { super(" + tableName + "DB.ROW_MAPPER, " + tableName + "DB.ROW_UNMAPPER, " + tableName + "DB.getTableName()); } ";
        propertiesStr += "public " + tableName + "Repository(RowMapper<" + tableName + "> rowMapper, RowUnmapper<" + tableName + "> rowUnmapper, String idColumn) { super(" + tableName + "DB.ROW_MAPPER, " + tableName + "DB.ROW_UNMAPPER, " + tableName + "DB.getTableName(), idColumn); }";
        propertiesStr += "@Override protected " + tableName + " postCreate(" + tableName + " entity, Number generatedId) { entity.setId(generatedId.longValue()); entity.setPersisted(true); return entity; }}";

        File folder = new File(generatedEntityFilesPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File classFile = new File(generatedEntityFilesPath + "/" + tableName + "Repository.java");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
            bw.write("import javax.annotation.Resource; import org.apache.log4j.Logger; import org.springframework.jdbc.core.JdbcOperations; import org.springframework.jdbc.core.RowMapper; import com.nurkiewicz.jdbcrepository.JdbcRepository; import com.nurkiewicz.jdbcrepository.RowUnmapper;");
            bw.write("@Repository(\"" + convertFirstLetterToLowerCase(tableName) + "Repository\") public class " + tableName + "Repository extends JdbcRepository<" + tableName + ", Long> {");
            bw.write(propertiesStr);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("生成Repository文件(" + tableName + ")出错！");
            e.printStackTrace();
        }

    }

    /*
     * 生成api文件
     * @param table
     */
    private static void generateUlFile(Table table) {
        String tableName = table.getJavaName();
        List<String> columNames = table.getColumNames();
        List<String> columComments = table.getComments();
        //生成私有属性和get、set方法
        String mapperStr = "";  //get、set方法字符串

        mapperStr += "\t *<ul>\r\n";
        for (int i = 0; i < columNames.size(); i++) {
            String columName = columNames.get(i);
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
            System.out.println("生成api文件(" + tableName + ")出错！");
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
            generateModelFile(tables.get(i));
            generateDBFile(tables.get(i));
            generateRepositoryFile(tables.get(i));
            generateUlFile(tables.get(i));
        }
        System.out.println("Generate Success!");
    }

}
