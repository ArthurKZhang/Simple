<%--
  Created by IntelliJ IDEA.
  User: zhangyu
  Date: 11/17/16
  Time: 10:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<form action="login.scaction">
    <table>
        <tr>
            <td align="right">用户名：</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td align="right">密码：</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td align="right" colspan="2"><input type="submit" value="登录"/></td>
        </tr>
    </table>
</form>

</body>
</html>
