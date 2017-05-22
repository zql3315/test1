package com.infosky.framework.annotation;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * 通过注解方式给数据库表和列加上字段
 * @author n004967
 *
 */
public class AnnotationWork {

    private List<String> classFilePaths = new ArrayList<String>();

    /**
     * 表名
     */
    private List<String> tablenames = new ArrayList<String>();

    /**
     * 表对应字段
     */
    private Map<String, Map<String, String>> tablecolumns = new HashMap<String, Map<String, String>>();

    /**
     * 表对应注释
     */
    private Map<String, String> tablecomments = new HashMap<String, String>();

    /**
     * 初始化注释
     */
    public void initComment() {
        classFilePaths = new ArrayList<String>();
        tablenames = new ArrayList<String>();
        tablecolumns = new HashMap<String, Map<String, String>>();
        tablecomments = new HashMap<String, String>();
        try {

            String classFolderPath = AnnotationWork.class.getClassLoader().getResource("/").getFile();

            fullClassFilePath(classFolderPath);

            if (classFilePaths != null && classFilePaths.size() > 0) {

                for (int i = 0; i < classFilePaths.size(); i++) {
                    String classFilePath = classFilePaths.get(i);

                    @SuppressWarnings("rawtypes") Class className = null;
                    try {
                        className = Class.forName(classFilePath);
                        workComment(className);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            fullTableComment();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充class文件路径
     * @param path
     */
    public void fullClassFilePath(String path) {

        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    String filename = f.getName();
                    if (filename.indexOf(".class") >= 0 && f.getAbsolutePath().indexOf("entity") >= 0 && filename.indexOf("\\$") < 0) {
                        String classFilePath = f.getAbsolutePath().split("WEB-INF\\\\classes\\\\")[1];
                        classFilePath = classFilePath.replaceAll(".class", "");
                        classFilePath = classFilePath.replaceAll("\\\\", ".");
                        classFilePaths.add(classFilePath);
                    }

                } else {
                    fullClassFilePath(f.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 填充表注释
     */
    public void fullTableComment() {

        List<String> sqls = createSqls();

        for (String sql : sqls) {
            executeSql(sql);
        }

    }

    /**
     * 创建sql语句
     * @return
     */
    public List<String> createSqls() {
        String DB_TYPE = "MYSQL";
        String username = "";
        try {
            InputStream in = AnnotationWork.class.getClassLoader().getResourceAsStream("hibernate.properties");
            Properties config = new Properties();
            config.load(in);
            in.close();
            username = config.getProperty("dataSource.username");
            String driverClassName = config.getProperty("dataSource.driverClassName");
            if (driverClassName != null) {
                if (driverClassName.toUpperCase().indexOf("MYSQL") >= 0) {
                    DB_TYPE = "MYSQL";
                } else if (driverClassName.toUpperCase().indexOf("ORACLE") >= 0) {
                    DB_TYPE = "ORACLE";
                } else if (driverClassName.toUpperCase().indexOf("SQLSERVER") >= 0) {
                    DB_TYPE = "SQLSERVER";
                } else {

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> sqls = new ArrayList<String>();
        for (String tablename : tablenames) {

            if (tablecomments.get(tablename) != null) {
                String comment = tablecomments.get(tablename);
                if (DB_TYPE.equals("MYSQL")) {
                    String sql = "ALTER TABLE " + tablename + " COMMENT='" + comment + "'";
                    sqls.add(sql);
                } else if (DB_TYPE.equals("ORACLE")) {
                    String sql = "comment on table " + username + "." + tablename + " is '" + comment + "'";
                    sqls.add(sql);

                } else if (DB_TYPE.equals("SQLSERVER")) {

                }
            }

            Map<String, String> columns = tablecolumns.get(tablename);
            if (columns != null && columns.size() > 0) {

                if (DB_TYPE.equals("MYSQL")) {
                    String sql = "select column_name,column_type from information_schema.columns where table_name='" + tablename + "'";
                    List<Object[]> list = querySql(sql);

                    Map<String, String> columntypes = new HashMap<String, String>();
                    for (Object[] objects : list) {
                        columntypes.put(objects[0].toString(), objects[1].toString());
                    }
                    for (String columnname : columns.keySet()) {
                        String comment = columns.get(columnname);
                        if (columntypes.get(columnname) != null) {

                            sql = "ALTER table " + tablename + " MODIFY COLUMN " + columnname + " " + columntypes.get(columnname) + " COMMENT '" + comment + "'";

                            sqls.add(sql);
                        } else {
                            System.out.println(tablename + ":" + columnname);
                        }
                    }
                } else if (DB_TYPE.equals("ORACLE")) {
                    for (String columnname : columns.keySet()) {
                        String comment = columns.get(columnname);

                        String sql = "COMMENT ON COLUMN " + username + "." + tablename + "." + columnname + " is '" + comment + "'";
                        sqls.add(sql);
                    }

                } else if (DB_TYPE.equals("SQLSERVER")) {

                }

            }
        }
        return sqls;

    }

    /**
     * 执行sql语句
     * @param sql
     */
    public void executeSql(String sql) {

        /*Session session = this.getSession();
        Transaction transaction = session.getTransaction();
        try {
        	transaction.begin();
        	Query query = session.createSQLQuery(sql);
        	query.executeUpdate();
        } catch (Exception e) {
        	e.printStackTrace();
        	transaction.rollback();
        } finally {
        	transaction.commit();
        	session.close();
        }*/

    }

    /**
     * 执行sql语句
     * @param sql
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> querySql(String sql) {
        /*Session session = this.getSession();
        try {
        	Query query = session.createSQLQuery(sql);
        	return query.list();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	session.close();
        }*/

        return null;
    }

    @SuppressWarnings({
            "rawtypes", "unchecked"
    })
    public void workComment(Class className) {

        String tablename = "";
        Field[] fields = className.getDeclaredFields();
        //获取表名
        if (className.isAnnotationPresent(Table.class)) {
            Table t = (Table) className.getAnnotation(Table.class);
            tablename = t.name();
            if (className.isAnnotationPresent(Comment.class)) {
                Comment c = (Comment) className.getAnnotation(Comment.class);
                String comment = c.value();
                tablecomments.put(tablename, comment);
            }
            Map<String, String> columns = new HashMap<String, String>();
            for (Field f : fields) {

                if (f.isAnnotationPresent(Comment.class)) {

                    String columnname = f.getName();
                    if (f.isAnnotationPresent(JoinColumn.class)) {

                        columnname = f.getAnnotation(JoinColumn.class).name();
                    }
                    String comment = f.getAnnotation(Comment.class).value();
                    // 业务逻辑
                    columns.put(columnname, comment);

                }
            }
            tablenames.add(tablename);
            tablecolumns.put(tablename, columns);
        }

    }

}
