package main;
import functions.Handler;
import data.Person;
import data.User;
import java.sql.*;
public class DataBase {
    public static void main(String[] args) throws Exception {
        Handler handlers = new Handler();
        handlers.ConfigFromConfig("D:\\IdeaProject\\test\\src\\configs.properties");
        handlers.ConnDB();
        Person person = new Person(handlers.getConn());
        User user = new User(handlers.getConn());
        //清空tables里的内容
        handlers.Clear_db();
        //展示数据库

        System.out.println(Integer.valueOf("1"));

        handlers.Show_table("users");
        handlers.Show_table("persons");

        System.out.println("任务2************：");
        //任务2：插入数据到 users表 和 persons 表中
        user.Insert_users("ly", "123456");
        user.Insert_users("liming", "345678");
        user.Insert_users("test", "11111");
        user.Insert_users("test1", "12345");
        // person 表
        //插入person表，又可能user表里没有，所以先插入user表，插入的时候会判断是否存在，存在就不插入了。
        user.Insert_users("ly", "888888");
        person.Insert_persons("ly", "雷力");
        user.Insert_users("liming", "888888");
        person.Insert_persons("liming", "李明", "25");
        user.Insert_users("test", "888888");
        person.Insert_persons("test", "测试用户", "20", "13388449933");
        //展示数据库
        handlers.Show_table("users");
        handlers.Show_table("persons");


        System.out.println("任务3************：");
        //任务3：插入数据 或 修改数据 到 persons 表，同时同步 users 表
        user.Insert_users("ly", "888888");
        person.Insert_persons("ly", "王五");
        user.Insert_users("test2", "888888");
        person.Insert_persons("test2", "测试用户2");
        user.Insert_users("test1", "888888");
        person.Insert_persons("test1", "测试用户1", "33");
        user.Insert_users("test", "888888");
        person.Insert_persons("test", "张三", "23", "18877009966");
        user.Insert_users("admin", "888888");
        person.Insert_persons("admin", "admin", "23", "13088881111");
        //展示数据库
        handlers.Show_table("users");
        handlers.Show_table("persons");

        System.out.println("任务4************：");
        //任务4：删除users表中test打头的username，同时按照规则一并删除person表相应的数据。
        ResultSet rs = user.Search_return_users("test%");
        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                user.Delete_users(rs.getString("username"));
                person.Delete_persons(rs.getString("username"));
            }
        }
        //展示数据库
        handlers.Show_table("users");
        handlers.Show_table("persons");
        //System.out.println(handlers.resultSetToJson("persons"));
        rs.close();
        handlers.CloseConn();
    }
}
