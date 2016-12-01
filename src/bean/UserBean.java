package bean;

/**
 * Created by zhangyu on 11/17/16.
 */
public class UserBean {

    public boolean check(String name, String passwd) {
        if (name.equals("name") && passwd.equals("passwd")) {
            return true;
        } else {
            return false;
        }
    }


    public  String name;
    public  String passwd;

    public UserBean(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public UserBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
