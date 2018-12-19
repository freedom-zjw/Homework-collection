<%@ page language="java" import="java.util.Random, java.util.*, java.io.*"
contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<title>40个随机数</title>
		<style type="text/css">
			div{
				float: left;
				width: 50px;
			}
		</style>
	</head>
	<body>
		<h1>40个随机数</h1>
		<%
			Random rnd = new Random(50);
			for(int i=0;i<40;i++){
				int n = rnd.nextInt(1000);
				out.print("<div>"+String.format("%d", n)+"</div>");
			}
		%>
	</body>
</html>