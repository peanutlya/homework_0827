package control;

import dao.UserDao;
import entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "LoginServlet",urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        User currentUser=new User(username,password);
        User user=null;
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSessionFactory sqlSessionFactory;
        inputStream = getClass().getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try(SqlSession session=sqlSessionFactory.openSession()){
            UserDao userDao = session.getMapper(UserDao.class);
            user = userDao.queryCountByKeywords(currentUser);
        }
       /* User currentUser=new User(username,password);
        UserService userService=new UserService();
        User user= null;
        try {
            user = userService.queryCountByKeywords(currentUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        if(user!=null){
            //用户登录成功
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            if(remember!=null) {
                //创建cookies
                Cookie cookie1 = new Cookie("username", username);
                Cookie cookie2 = new Cookie("password", password);

                //设置时长
                cookie1.setMaxAge(60);
                cookie2.setMaxAge(60);

                //add至响应中
                response.addCookie(cookie1);
                response.addCookie(cookie2);
            }

            //request.getRequestDispatcher("/User/login.jsp").forward(request, response);
            response.sendRedirect("/admin/user/index.jsp");
        }
        else{
            response.sendRedirect("/login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
