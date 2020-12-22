package functions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;
import java.io.*;
import java.util.Properties;

public class Handler {

    //mysql驱动包名
    private String DRIVER_NAME = null;
    //数据库连接地址
    private String URL = null;
    //用户名,此处为root用户
    private String USER_NAME = null;
    //密码，此处为:huhao
    private String PASSWORD = null;
    //连接数据库
    private Connection conn = null;

    public Connection getConn() {
        return conn;
    }

    //读取配置文件并赋值
    public void ConfigFromConfig(String path){
        try {
            Properties prop = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            prop.load(bufferedReader);
            this.DRIVER_NAME = prop.getProperty("DRIVERNAME");
            System.out.println();
            this.URL = prop.getProperty("URL");
            this.USER_NAME = prop.getProperty("USER");
            this.PASSWORD = prop.getProperty("PASS");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //连接数据库
    public void ConnDB(){
        try{
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            this.conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    //关闭连接
    public void CloseConn(){
        try {
            this.conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //清空数据库
    public void Clear_db(){
        try {
            PreparedStatement pstm1 = this.conn.prepareStatement("delete from users");
            PreparedStatement pstm2 = this.conn.prepareStatement("delete from persons");
            pstm1.executeUpdate();
            pstm2.executeUpdate();
            pstm1.close();
            pstm2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //制表所用的字符串
    public String GetStr(int l, int length){
        String str = "";
        int i = l - length;
        for(int j=0; j<i; j++) str += "-";
        return str;
    }
    //制表展示数据库
    public void Show_table(String table){
        try{
            String sql = "SELECT * FROM "+ table;
            PreparedStatement pstm = this.conn.prepareStatement(sql);
            //结果集
            ResultSet rs = pstm.executeQuery();
            System.out.println("表 "+table+"：");
            if(table == "users"){
                System.out.println("|username-----|-----pass|");
                while (rs.next()) {
                    System.out.println("|" + rs.getString("username") + GetStr(13, rs.getString("username").length())
                            + "|" + GetStr(9, rs.getString("pass").length()) + rs.getString("pass") + "|");
                }
            }
            else{
                System.out.println("|username-----|--------name--------|-----age-----|-----teleno|");
                while (rs.next()) {
                    String str1,str2;
                    if(rs.getString("age")==null){
                        str1 = "-------------";
                    }else{
                        str1 = "-----" + rs.getString("age") + GetStr(8, rs.getString("age").length());
                    }
                    if(rs.getString("teleno")==null){
                        str2 = "-----------";
                    }else{
                        str2 = GetStr(11, rs.getString("teleno").length()) + rs.getString("teleno");
                    }
                    System.out.println("|" + rs.getString("username") + GetStr(13, rs.getString("username").length())
                            + "|" + "--------" + rs.getString("name") + GetStr(12, rs.getString("name").length())
                            + "|" + str1 + "|" + str2 + "|");
                }
            }
            rs.close();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //resultset转json
    public String resultSetToJson(String table) throws SQLException, JSONException {
        String sql = "SELECT * FROM "+ table;
        PreparedStatement pstm = this.conn.prepareStatement(sql);
        //结果集
        ResultSet rs = pstm.executeQuery();
        // json数组
        JSONArray array = new JSONArray();

        // 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();
            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
            array.put(jsonObj);
        }
        rs.close();
        pstm.close();
        return array.toString();
    }
}

