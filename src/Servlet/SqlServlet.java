package Servlet;

import functions.Handler;
import org.json.JSONException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "showSql")
public class SqlServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");//设置编码格式
        response.setContentType("application/json; charset=utf-8");

        String path = "/WEB-INF/classes/configs.properties"; //读取WEB-INF中的配置文件
        String realPath = getServletContext().getRealPath(path);

        Handler handlers = new Handler();
        handlers.ConfigFromConfig(realPath);
        handlers.ConnDB();

        String str = "";
        try {
            str = handlers.resultSetToJson("users") +"^connection*connection^"+ handlers.resultSetToJson("persons");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().print(str);
        handlers.CloseConn();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
