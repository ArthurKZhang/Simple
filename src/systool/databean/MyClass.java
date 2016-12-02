package systool.databean;


/**
 * Created by zhangyu on 12/1/16.
 */
public class MyClass {
    private String name;
    private String method;

    public MyClass(String name, String method) {
        this.name = name;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.equals(""))
            this.name = name;
        else
            System.err.println(this.getClass().toString() + "Empty class name--setName()");
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        if (method != null && !method.equals(""))
            this.method = method;
        else
            System.err.println(this.getClass().toString()+"Empty class method--setMethod()");
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "name='" + name + '\'' +
                ", method='" + method + '\'' +
                '}';
    }
}
