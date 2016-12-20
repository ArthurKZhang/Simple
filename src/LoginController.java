import bean.UserBean;
import systool.ControllerParse;
import systool.DynamicProxy;
import systool.databean.Action;
import systool.databean.MyClass;
import systool.databean.Result;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by zhangyu on 11/17/16.
 */
public class LoginController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        try {
            System.out.println("-------LoginController.init()-----------");
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

        Action action = ControllerParse.getAction(actionName);
        MyClass actionClass = action.getaClass();
        String method = actionClass.getMethod();
        String actionclassName = actionClass.getName();

        Map<String, Result> resultMap = action.getResults();

        try {
            Class<?> aclass = Class.forName(actionclassName);
            Object aobj = aclass.newInstance();
            Class<?>[] ainterfaces = aclass.getInterfaces();

            String resultString;

            //if contians intercepter configuration, use dynamic proxcy
            if (action.getIntercepterNames() != null || !action.getIntercepterNames().isEmpty()) {
                System.out.println("intercepters of action " + actionName + " : " + action.getIntercepterNames().toString());
                //Proxcy
                InvocationHandler handler = new DynamicProxy(aobj, actionName);
                Object proxyInstance = Proxy.newProxyInstance(handler.getClass().getClassLoader(), ainterfaces, handler);
                Method proxyMethod = ainterfaces[0].getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
                resultString = (String) proxyMethod.invoke(ainterfaces[0].cast(proxyInstance), request, response);
                System.out.println("resultString---ProxcyPart-- " + resultString);
            } else {
                //reflect directly
                Method meth = aclass.getDeclaredMethod(method, HttpServletRequest.class, HttpServletResponse.class);
                resultString = (String) meth.invoke(aobj, request, response);
            }

            if (resultString == null || resultString.equals("")) {
                System.err.println("return null from " + actionclassName);
                return;
            }

            Result result = resultMap.get(resultString);
            String re_type = result.getType();
            String re_value = result.getValue();

            if (re_type.equals("forward")) {
                if (re_value.endsWith(".xml")) {
                    response.sendRedirect(request.getContextPath() + "/" + re_value);
                } else {
                    request.getRequestDispatcher(re_value).forward(request, response);
                }
            } else if (re_type.equals("redirect")) {
                response.sendRedirect(request.getContextPath() + "/" + re_value);
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
