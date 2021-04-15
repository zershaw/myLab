<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<body>
<h2>Hello World!</h2>
<form action="${pageContext.request.contextPath}/login" method="post">
    username <input name="username" type="text"> <br>
    password: <input name="password" type="password"> <br>
    hobbies:
    <input name="hobbies" type="checkbox" value="女孩">女孩
    <input name="hobbies" type="checkbox" value="代码">代码
    <input name="hobbies" type="checkbox" value="电影">电影
    <input name="hobbies" type="checkbox" value="爬山">爬山
    <input type="submit">
</form>
</body>
</html>
