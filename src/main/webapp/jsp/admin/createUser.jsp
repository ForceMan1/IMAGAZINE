<%--
  Created by IntelliJ IDEA.
  User: Igor
  Date: 13.01.2017
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание нового пользователя</title>
    <form name="createUserForm" method="POST" action="/jsp/admin/processCreateUsers">
        <div>
            <label for="txtFio">ФИО:</label><input type="text" id="txtFio" name="txtFio"/>
        </div>
        <div>
            <label for="txtBirthday">Логин:</label><input type="text" id="txtBirthday" name="txtBirthday"/>
        </div>
        <div>
            <label for="txtLogin">Логин:</label><input type="text" id="txtLogin" name="txtLogin"/>
        </div>
        <div>
            <label for="txtPassword">Пароль:</label><input type="text" id="txtPassword" name="txtPassword"/>
        </div>
        <div>
            <label for="cbActive">Активтировать пользователя:</label><input type="checkbox" id="cbActive" name="cbActive" checked/>
        </div>
        <div>
            <input type="submit" value="SUBMIT" name="sbCreateUser"/>
        </div>
    </form>

</head>
<body>

</body>
</html>
