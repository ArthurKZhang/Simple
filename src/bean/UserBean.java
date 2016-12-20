package bean;

import dao.mysql.UserDao;

/**
 * Created by zhangyu on 11/17/16.
 */
public class UserBean {

    public boolean login(String name, String passwd) {
        UserDao.openDBConnection();
        UserBean user = UserDao.queryUser(name);
        boolean isClosed = UserDao.closeDBConnection();
        if (!isClosed)
            System.err.println("----Connection not closed-----");

        if (user == null)
            return false;

        if (name.equals(user.getName()) && passwd.equals(user.getPasswd())) {
            return true;
        } else {
            return false;
        }
    }

    public String name;
    public String passwd;

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

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
