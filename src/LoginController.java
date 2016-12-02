import bean.UserBean;
import systool.ControllerParse;
import systool.databean.Action;
import systool.databean.MyClass;
import systool.databean.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by zhangyu on 11/17/16.
 */
public class LoginController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
//            String path = this.getServletContext().getRealPath("src/controller.xml");
//            System.out.println("------"+path);
            ControllerParse.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String actionName = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf(".scaction"));
        System.out.println("test---actionName:" + actionName);

        Action action = ControllerParse.getaAction(actionName);
        MyClass actionClass = action.getaClass();
        String method = actionClass.getMethod();
        String className = actionClass.getName();

        Map<String, Result> resultMap = action.getResults();

        try {
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.newInstance();
            Method meth = clazz.getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            String returnFlag = (String) meth.invoke(obj, request, response);

            if (returnFlag == null || returnFlag.equals("")) {
                System.err.println("return null from " + className);
                return;
            }

            Result result = resultMap.get(returnFlag);
            String re_type = result.getType();
            String re_value = result.getValue();

            if (re_type.equals("forward")) {
                request.getRequestDispatcher(re_value).forward(request, response);
            } else if (re_type.equals("redirect")) {
                response.sendRedirect(request.getContextPath()+"/"+re_value);
            } else {

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
