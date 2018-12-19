<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>实现文件上传</title>
</head>
<body>
	<%request.setCharacterEncoding("utf-8"); %>
	<form name="fileupLoad" action="fileUpload.jsp" method="POST"
							enctype="multipart/form-data">
		<p>id: <input type="text" name="id" size=24 value="888"></p>
		<p>文件名: <input type="file" name="file" size=50></p>
		<p><input type="submit" name="submit" value="OK"></p>
	</form>
</body>
</html>