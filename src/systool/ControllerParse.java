package systool;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import systool.databean.Action;
import systool.databean.MyClass;
import systool.databean.Result;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyu on 12/1/16.
 */
public class ControllerParse {
    /**
     * Map<String,Action> actions, <action_name, action_object>
     */
    private static Map<String, Action> actions = new HashMap<>();

    private ControllerParse() {
    }

    /**
     * this method parse controller.xml to memory.
     * @throws Exception
     */
    public static void init() throws Exception {

        System.out.println(ControllerParse.class.getResource("/controller.xml").toString());
        SAXReader reader = new SAXReader(); //!!
        Document document = reader.read(ControllerParse.class.getResource("/controller.xml"));
        Element root = document.getRootElement();
        List<Element> actionList = root.elements("action");
        //for each action:
        for (Element e : actionList) {
            String actionName = e.element("name").getText();
            System.out.println("ControllerParse.init()--------action name--"+actionName);

            Element e_class = e.element("class");
            String className = e_class.element("name").getText();
            String classMethod = e_class.element("method").getText();
            System.out.println("ControllerParse.init()--------class name--"+className);
            System.out.println("ControllerParse.init()--------class method--"+classMethod);
            MyClass newClass = new MyClass(className, classMethod);

            Map<String, Result> results = new HashMap<>();
            Iterator<Element> e_ResultIterater = e.elementIterator("result");
            while (e_ResultIterater.hasNext()) {

                Element e_temp = e_ResultIterater.next();

                String resultName = e_temp.element("name").getText();
                System.out.println("ControllerParse.init()--------result name--"+resultName);
                String resultType = e_temp.element("type").getText();
                System.out.println("ControllerParse.init()--------result type--"+resultType);
                String resultValue = e_temp.element("value").getText();
                System.out.println("ControllerParse.init()--------result value"+resultValue);
                results.put(resultName, new Result(resultType, resultValue));
            }
            Action newAction = new Action(newClass, results);
            actions.put(actionName, newAction);//nonpointerexception!!
        }
    }

    /**
     * init() must be called before this method
     * @param actionName
     * @return
     */
    public static Action getaAction(String actionName) {
        if (actionName == null || actionName.equals("")) {
            throw new RuntimeException("requesting by empty action name is denied.");
        }
        Action action = actions.get(actionName);
        if (action == null){
            throw new RuntimeException("no resource found by action name: "+actionName);
        }
        return action;
    }

}
