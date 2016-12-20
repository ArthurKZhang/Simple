package dao.postgresql;

import bean.UserBean;

import java.sql.*;

/**
 * Created by zhangyu on 12/19/16.
 */
public class UserDao {
    public static Connection connection = null;

    /**
     * openDBConnection(): Connection, 负责打开MySQL数据库连接
     */
    public static Connection openDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("*******CANNOT FIND MYSQL DRIVER*********");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(PSqlConfig.URL, PSqlConfig.USER, PSqlConfig.PASSWD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * closeDBConnection(): boolean, 负责关闭MySQL数据库连接
     * success or not connected -true; fail- false
     */
    public static boolean closeDBConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return true;
    }

    /**
     * queryUser(String userName): UserBean, 负责根据参数查询对象表记录
     *
     * @param userName
     * @return
     */
    public static UserBean queryUser(String userName) {
        UserBean userBean = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + PSqlConfig.TABLE_USER + " WHERE name =\"" + userName + "\"");
            while (resultSet.next()) {
                userBean = new UserBean(resultSet.getString("name"), resultSet.getString("password"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return userBean;
    }

    /**
     * insertUser(UserBean u): boolean, 负责根据参数增加对象表记录
     *
     * @param u
     */
    public static boolean insertUser(UserBean u) {
        if (u == null || u.getName() == null || u.getPasswd() == null)
            return false;

        String name = u.getName();
        String password = u.getPasswd();

        try {
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO " + PSqlConfig.TABLE_USER + " VALUES(\"" + name + "\",\"" + password + "\")");
            int rowChanged = statement.executeUpdate();
            if (rowChanged >= 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * updateUser(UserBean u): boolean, 负责根据参数修改对象表记录
     *
     * @param u
     * @return
     */
    public static boolean updateUser(UserBean u) {
        if (u == null || u.getName() == null || u.getPasswd() == null)
            return false;

        try {
            UserBean old = null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM" + PSqlConfig.TABLE_USER + "WHERE name =\"" + u.getName() + "\"");
            while (resultSet.next()) {
                old = new UserBean(resultSet.getString("name"), resultSet.getString("password"));
            }
            if (old == null) {
                System.err.println(u.getName() + "not exits in table----");
                return false;
            }

            String sql = "UPDATE " + PSqlConfig.TABLE_USER + " SET name=?,password=? WHERE name=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getPasswd());
            preparedStatement.setString(3, old.getName());
            preparedStatement.setString(4, old.getPasswd());

            boolean result = preparedStatement.execute();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 负责根据参数删除对象表记录
     *
     * @param u
     * @return
     */
    public static boolean deleteUser(UserBean u) {
        String sql = "DELETE FROM " + PSqlConfig.TABLE_USER + " WHERE name=? and password=?";
        try {
            PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getPasswd());
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
