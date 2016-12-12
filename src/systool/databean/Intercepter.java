package systool.databean;

/**
 * Created by zhangyu on 12/9/16.
 */
public class Intercepter {
    private String name;
    private MyClass myClass;

    public Intercepter(String name, MyClass myClass) {
        this.name = name;
        this.myClass = myClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyClass getMyClass() {
        return myClass;
    }

    public void setMyClass(MyClass myClass) {
        this.myClass = myClass;
    }
}
