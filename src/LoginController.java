import bean.UserBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangyu on 11/17/16.
 */
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("********" + username + "||" + password);

        UserBean userBean = new UserBean(username, password);

        boolean feedback = userBean.check(username, password);

        if (feedback == true) {
            //........
            request.setAttribute("message", "Login SUCCESS");
            request.getRequestDispatcher("/WEB-INF/pages/login_success.jsp").forward(request, response);
        } else if (feedback == false) {
            //........
            request.setAttribute("message", "Login FAILED");
            request.getRequestDispatcher("/WEB-INF/pages/login_fail.jsp").forward(request, response);
        } else {
            //.........
        }

    }

}
