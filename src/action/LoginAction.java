package action;

import bean.UserBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangyu on 12/1/16.
 */
public class LoginAction implements ILoginAction{

    /**
     * @param request
     * @param response
     * @return return a string success or fail
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("********" + username + "||" + password);

        UserBean userBean = new UserBean(username, password);

        boolean feedback = userBean.check(username, password);

        if (feedback == true) {
            return "success";
        } else if (feedback==false){
            return "fail";
        }else {
            return null;
        }
    }
}
