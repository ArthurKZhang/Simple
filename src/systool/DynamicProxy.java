package systool;

import intercepter.LogWriter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangyu on 12/9/16.
 */
public class DynamicProxy implements InvocationHandler {
    Object action;
    String actionName;
    String result;

    public DynamicProxy(Object action, String actionName) {
        this.action = action;
        this.actionName = actionName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        before();
        System.out.println(DynamicProxy.class.getName()+ "-----before invoke-----"+actionName);

        result = (String) method.invoke(action, args);

        after();
        System.out.println(DynamicProxy.class.getName()+ "-----after invoke-----"+actionName);
        return result;
    }


    private void before() {
        Map<String, String> map = new HashMap<>();
        map.put("flag","before");
        map.put("name",actionName);
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stime=format.format(date);
        map.put("s-time", stime);
        LogWriter logWriter= new LogWriter();
        logWriter.log(map);
    }

    private void after() {
        Map<String, String> map = new HashMap<>();
        map.put("flag","after");
        Date date = new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String etime=format.format(date);
        map.put("e-time",etime);
        map.put("result",result);
        LogWriter logWriter = new LogWriter();
        logWriter.log(map);
    }
    /*
            <name>login</name>
            <s-time>yyyy-mm-dd hh:mm:ss</s-time>
            <e-time>yyyy-mm-dd hh:mm:ss</e-time>
            <result>success</result>
     */
}
