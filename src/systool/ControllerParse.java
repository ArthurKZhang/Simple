package systool;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import systool.databean.Action;
import systool.databean.Intercepter;
import systool.databean.MyClass;
import systool.databean.Result;

import java.util.*;

/**
 * Created by zhangyu on 12/1/16.
 */
public class ControllerParse {
    /**
     * Map<String,Action> actions, <action_name, action_object>
     */
    private static Map<String, Action> actions = new HashMap<>();

    /**
     * Map<String,Intercepter> intercepters, <intercepterName, Intercepter_object>
     */
    private static Map<String, Intercepter> intercepters = new HashMap<>();

    private ControllerParse() {
    }

    /**
     * this method parse controller.xml to memory.
     *
     * @throws Exception
     */
    public static void init() throws Exception {

        System.out.println(ControllerParse.class.getResource("/controller.xml").toString());
        SAXReader reader = new SAXReader(); //!!
        Document document = reader.read(ControllerParse.class.getResource("/controller.xml"));
        Element root = document.getRootElement();

        /*
         * parse "intercepter"s edited by E3 Arthur ZHANG
         <interceptor>
         <name>logWriter</name>
         <class>
         <name>intercepter.LogWriter</name>
         <method>log</method>
         </class>
         </interceptor>
         */
        List<Element> intercepterList = root.elements("interceptor");
        for (Element e : intercepterList) {
            String intercepterName = e.element("name").getText();
            System.out.println("ControllerParse.init()--------intercepter name--" + intercepterName);
            Element intercepterClass = e.element("class");
            String intercptClassName = intercepterClass.elementText("name");
            System.out.println("ControllerParse.init()--------intercepter className--" + intercptClassName);
            String intercptClassMethod = intercepterClass.elementText("method");
            System.out.println("ControllerParse.init()--------intercepter classMethod--" + intercptClassMethod);
            intercepters.put(intercepterName, new Intercepter(intercepterName, new MyClass(intercptClassName, intercptClassMethod)));
        }


        /*
         * parse "action"s
         * + editions for <intercepter-ref> by E3 Arthur ZHANG
         */
        List<Element> actionList = root.elements("action");
        //for each action:
        for (Element e : actionList) {
            String actionName = e.element("name").getText();
            System.out.println("ControllerParse.init()--------action name--" + actionName);

            Element e_class = e.element("class");
            String className = e_class.element("name").getText();
            String classMethod = e_class.element("method").getText();
            System.out.println("ControllerParse.init()--------class name--" + className);
            System.out.println("ControllerParse.init()--------class method--" + classMethod);
            MyClass newClass = new MyClass(className, classMethod);

            /*
             * editions for <intercepter-ref> by E3 Arthur ZHANG
             <interceptor-ref>
                <name>logWriter</name>
             </interceptor-ref>
             */
            List<String> intercptRefNames = new ArrayList<>();
            List<Element> intercptRefs = e.elements("interceptor-ref");
            for (Element interRef : intercptRefs) {
                String intercptRefName = interRef.elementText("name");
                intercptRefNames.add(intercptRefName);
            }
            //parse "result"s
            Map<String, Result> results = new HashMap<>();
            Iterator<Element> e_ResultIterater = e.elementIterator("result");
            while (e_ResultIterater.hasNext()) {

                Element e_temp = e_ResultIterater.next();

                String resultName = e_temp.element("name").getText();
                System.out.println("ControllerParse.init()--------result name--" + resultName);
                String resultType = e_temp.element("type").getText();
                System.out.println("ControllerParse.init()--------result type--" + resultType);
                String resultValue = e_temp.element("value").getText();
                System.out.println("ControllerParse.init()--------result value" + resultValue);
                results.put(resultName, new Result(resultType, resultValue));
            }
            Action newAction = new Action(newClass, results);
            newAction.setIntercepterNames(intercptRefNames);// added by E3 Arthur ZHANG
            actions.put(actionName, newAction);
        }
    }

    /**
     * init() must be called before this method
     *
     * @param actionName
     * @return
     */
    public static Action getAction(String actionName) {
        if (actionName == null || actionName.equals("")) {
            throw new RuntimeException("requesting by empty action name is denied.");
        }
        Action action = actions.get(actionName);
        if (action == null) {
            throw new RuntimeException("no resource found by action name: " + actionName);
        }
        return action;
    }

    private static void initIntercepters(){

    }

}
