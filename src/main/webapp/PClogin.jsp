<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/callBackBD" method="post">
		<input type="text" name="userName">
		<input type="text" name="passward">
		<input type="hidden" name="openid" value="${openid }">
		<input type="submit" value="登陆并绑定">
	</form>
</body>
</html>