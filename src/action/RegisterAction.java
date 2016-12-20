package action;

import bean.UserBean;
import dao.mysql.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangyu on 12/1/16.
 */
public class RegisterAction implements IRegisterAction {

    @Override
    public String register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username==null || password==null)
            return "fail";

        UserDao.openDBConnection();
        UserBean user = UserDao.queryUser(username);

        if (user != null) {
            System.err.println(username + " Already Exists--------");
            UserDao.closeDBConnection();
            return "fail";
        }

        boolean isOK = UserDao.insertUser(new UserBean(username, password));
        boolean isClosed = UserDao.closeDBConnection();
        System.err.println("----Connection not closed------");
        String result = "fail";
        if (isOK) {
            result = "success";
        }
        return result;
    }
}
