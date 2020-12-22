package data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Person {
    private Connection conn = null;

    public Person(Connection conn) {
        this.conn = conn;
    }


    // person 信息插入时，利用 username 查询 persons 表，如果存在，就修改信息
    public boolean Search_persons(String username)throws Exception{
        boolean flag = true;
        String sql_search_persons = "select * from persons where username=?";
        PreparedStatement pstm = this.conn.prepareStatement(sql_search_persons);
        pstm.setString(1, username);
        //结果集
        ResultSet rs = pstm.executeQuery();
        if(!rs.isBeforeFirst()){
            flag = false;
        }
        rs.close();
        pstm.close();
        return flag;
    }
    //插入数据到数据库的 persons 表
    public void Insert_persons(String ... strs){
        try{
            String username=null,name=null,age=null,teleno=null;
            int i=0;
            for(String string: strs){
                if(i==0) username = string;
                if(i==1) name = string;
                if(i==2) age = string;
                if(i==3) teleno = string;
                i++;
            }
            if(username.length()>10 || username.length()<=0 || name.length()>20 || name.length()<=0 ||(teleno!=null && teleno.length()>11)){
                throw new Exception("请输入长度小于10的用户名以及长度小于20的名字");
            }
            //如果已经存在了，就先删除了，再重新插入
            if(Search_persons(username)==true){
                Delete_persons(username);
            }
            if(age==null|| Objects.equals(age, "")) {
                age = null;
            }
            if(teleno==null|| Objects.equals(teleno, "")) {
                teleno = null;
            }
            PreparedStatement pstm = null;
            String sql_insert_persons = "INSERT INTO persons VALUES (?,?,?,?)";
            pstm = this.conn.prepareStatement(sql_insert_persons);
            //填充sql语句中的？
            pstm.setString(1, username);
            pstm.setString(2, name);
            pstm.setString(3, age);
            pstm.setString(4, teleno);
            //使用executeUpdate函数执行sql语句
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除 persons 表里对应的数据
    public void Delete_persons(String username){
        try{
            String sql_delete_persons = "delete from persons where username=?";
            PreparedStatement pstm = this.conn.prepareStatement(sql_delete_persons);
            pstm.setString(1, username);
            pstm.executeUpdate();
            pstm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
