package test.公共utils;
import java.io.*;


import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/12/30
 * @描述
 */
public class AllUtils {

    //得到当前方法的名字
    public String getMethodName(){
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        return methodName;
    }

    //向文件末尾添加内容
    public void addStrToFile(String filePath){
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(filePath, true));
            out.write("hello world");
        } catch (IOException e) {
            // error processing code
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            }catch (IOException e) {

            }

        }
    }

    /**
     * 使用JDBC链接Oracle
     */
    String driverClass = "oracle.jdbc.driver.OracleDriver";
    Connection con;
    public void init(FileInputStream fs) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException
    {
        Properties props = new Properties();
        props.load(fs);
        String url = props.getProperty("db.url");
        String userName = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        Class.forName(driverClass);
        con= DriverManager.getConnection(url, userName, password);
    }
    public void fetch() throws SQLException, IOException
    {
        PreparedStatement ps = con.prepareStatement("select SYSDATE from dual");
        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            // do the thing you do
        }
        rs.close();
        ps.close();
    }

    /**
     *
     * @param dirPath
     * @return
     *  列出文件和目录
     */
    public static File[] getFiles(String dirPath) {
        File dir = new File(dirPath);
        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (int i = 0; i < children.length; i++) {
                // Get filename of file or directory
                String filename = children[i];
            }
        }
        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };
        children = dir.list(filter);
        // The list of files can also be retrieved as File objects
        File[] files = dir.listFiles();
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }

        };
        files = dir.listFiles(fileFilter);
        return files;
    }




    public static void main(String[] args) {
        //得到当前方法的名字
        File[] files = getFiles("C:\\Users\\super\\Desktop\\vpn");
        System.out.println(files);

    }
}
