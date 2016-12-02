package systool.databean;

/**
 * Created by zhangyu on 12/1/16.
 */
public class Result {
    private String type;
    private String value;

    public Result(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Result{" +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
