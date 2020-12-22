package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class User {
    private Connection conn = null;

    public User(Connection conn) {
        this.conn = conn;
    }

    // person 信息插入时，利用 username 查询 users 表，如果不存在，则要同时插入 user 信息，默认密码888888
    public boolean Search_users(String username)throws Exception{
        boolean flag = true;
        String sql_search_users = "select * from users where username=?";
        PreparedStatement pstm = this.conn.prepareStatement(sql_search_users);
        pstm.setString(1, username);
        //结果集
        ResultSet rs = pstm.executeQuery();
        //当 ResultSet 为非空时，其游标指向第一条记录前面，若为空时由于不存在第一条记录，所以这时候游标也无法向指第一条记录前面
        if(!rs.isBeforeFirst()){
            flag = false;
        }
        rs.close();
        pstm.close();
        return flag;
    }
    //查询 users 并返回 resultset
    public ResultSet Search_return_users(String username)throws Exception{
        //查询name字段中以“李”字开头。
        //select * from table1 where name like '李*'
        String sql_search_return_users = "select * from users where username like ?";
        PreparedStatement pstm = this.conn.prepareStatement(sql_search_return_users);
        pstm.setString(1, username);
        return pstm.executeQuery();
    }
    //删除 users 表里对应的数据
    public void Delete_users(String username){
        try{
            String sql_delete_users = "delete from users where username=?";
            PreparedStatement pstm = this.conn.prepareStatement(sql_delete_users);
            pstm.setString(1, username);
            pstm.executeUpdate();
            pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //插入数据到数据库的 users 表
    public int Insert_users(String username, String pass){
        try{
            //如果表里面已经有了，就不要插入了
            if(Search_users(username)==true){
                return 0;
            }
            if(username.length()>10 || username.length()<=0 || pass.length()>8 || pass.length()<=0){
                throw new Exception("请输入长度小于10的用户名以及长度合适的密码");
            }
            PreparedStatement pstm = null;
            String sql_insert_users = "INSERT INTO users(username,pass) VALUES (?,?)";
            pstm = this.conn.prepareStatement(sql_insert_users);
            //填充sql语句中的？
            pstm.setString(1, username);
            pstm.setString(2, pass);
            //使用executeUpdate函数执行sql语句
            pstm.executeUpdate();
            pstm.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
