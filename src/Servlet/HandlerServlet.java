package Servlet;

import data.Person;
import data.User;
import functions.Handler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "/servlet")
public class HandlerServlet extends HttpServlet {
    //Constructor of the object.
    public HandlerServlet(){
        super();
    }

    //Destruction of the servlet
    public void destroy(){
        super.destroy();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String path = "/WEB-INF/classes/configs.properties"; //读取WEB-INF中的配置文件
        String realPath = getServletContext().getRealPath(path);

        Handler handlers = new Handler();
        handlers.ConfigFromConfig(realPath);
        handlers.ConnDB();
        Person person = new Person(handlers.getConn());
        User user = new User(handlers.getConn());

        request.setCharacterEncoding("utf-8");//设置编码格式
        response.setContentType("application/json; charset=utf-8");

        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String tele = request.getParameter("teleno");
        //展示数据库
        handlers.Show_table("users");
        handlers.Show_table("persons");

        String a,b;
        if(Objects.equals(name, "") ||name==null){
            user.Delete_users(username);
            person.Delete_persons(username);
            a = "删除"; b = "user";
        }else{
            a = "更新";
            if(user.Insert_users(username, "888888")==1){
                a = "插入";
            }
            person.Insert_persons(username, name,age,tele);
            b = "person";
        }
        //展示数据库
        handlers.Show_table("users");
        handlers.Show_table("persons");

        response.getWriter().print("{"+"\"operator\""+":"+"\""+a+"\""+","+"\"table\""+":"+"\""+b+"\""+","+"\"username\""+":"+"\""+username+"\""+"}");
        handlers.CloseConn();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{

    }
}
