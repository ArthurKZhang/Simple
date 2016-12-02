package systool.databean;

import java.util.Map;

/**
 * Created by zhangyu on 12/1/16.
 */
public class Action {

    private MyClass aClass;
    private Map<String, Result> results;

    public Action() {
    }

    public Action(MyClass aClass, Map<String, Result> results) {
        this.aClass = aClass;
        this.results = results;
    }


    public MyClass getaClass() {
        return aClass;
    }

    public void setaClass(MyClass aClass) {
        this.aClass = aClass;
    }

    public Map<String, Result> getResults() {
        return results;
    }

    public void setResults(Map<String, Result> results) {
        this.results = results;
    }
}
