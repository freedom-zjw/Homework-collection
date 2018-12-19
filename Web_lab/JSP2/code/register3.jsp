<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%request.setCharacterEncoding("utf-8");%>
<%
	String submit1 = request.getParameter("submit1");
    String submit2 = request.getParameter("submit2");
	if (submit2 != null)
		response.sendRedirect("http://172.18.187.11/main.htm");
	String method = request.getMethod();
	boolean post = method.equalsIgnoreCase("post");

	String name = request.getParameter("name");
	if(name==null) name="";
	else name = name + "*";

	String password = request.getParameter("password");
	if(password==null) password="";

	String campus = request.getParameter("campus");
	if(campus==null) campus="";
	String campus1[]= {"","","","",""};
	if(campus.equals("south")) campus1[0] = "checked";
	else if(campus.equals("east")) campus1[1] = "checked";
	else if(campus.equals("north")) campus1[2] = "checked";
	else if(campus.equals("Zhuhai")) campus1[3] = "checked";
	else if(campus.equals("Shenzhen"))campus1[4] = "checked";	

	String grade = request.getParameter("grade");
	if(grade==null) grade="";
	String selects[]= {"","","",""};
	if(grade.equals("freshman"))selects[0] = "ture";
	else if(grade.equals("sophomore"))selects[1] = "true";
	else if(grade.equals("junior"))selects[2] = "true";
	else if(grade.equals("senior"))selects[3] = "true";

	String club1 = request.getParameter("club1");
	if(club1==null) club1="";
	if(club1.equals("dance"))club1 = "checked";
	String club2 = request.getParameter("club2");
	if(club2==null) club2="";
	if(club2.equals("photo"))club2 = "checked";
	String club3 = request.getParameter("club3");
	if(club3==null) club3="";
	if(club3.equals("basketball"))club3 = "checked";

	String content = request.getParameter("content");
	if(content==null) content="";
	else content = content + "*";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>用户注册</title>
  	<style>
  		[for="content"]{vertical-align:top;}
  		#content {width:300px;height:200px}
  	</style>
  </head>  
  <body>
    <form action="register3.jsp" method="post">
    	<input type="hidden" name="id" value="12345"/>
    	同学名: <input type="text" name="name" value=<%=name %>><br/><br/>
    	密码: <input type="password" name="password" value=<%=password%>><br/><br/>
    	校区: 南校区<input type="radio" name="campus" value="south" <%=campus1[0]%>/>
    		  东校区<input type="radio" name="campus" value="east" <%=campus1[1]%>/>
    		  北校区<input type="radio" name="campus" value="north" <%=campus1[2]%>/>
    		  珠海校区<input type="radio" name="campus" value="Zhuhai"<%=campus1[3]%>/>
    		  深圳校区<input type="radio" name="campus" value="Shenzhen"<%=campus1[4]%>/><br/><br/>
    	年级: <select name="grade">
    			<option value="freshman" <%=selects[0]%>>大学一年级</option>
            	<option value="sophomore" <%=selects[1]%>>大学二年级</option>
            	<option value="junior" <%=selects[2]%>>大学三年级</option>
                <option value="senior" <%=selects[3]%>>大学四年级</option>
    		</select><br/><br/>
		社团: 舞蹈队<input type="checkbox"name="club1"value="dance" <%=club1%>/>
		               摄影<input type="checkbox"name="club2"value="photo" <%=club2%>/>
			 篮球<input type="checkbox"name="club3"value="basketball" <%=club3%>/><br/>
    		<label for="content">说明:</label>
    		<textarea id="content" name="content"><%=content%></textarea><br/><br/>
    		<input type="submit" name="submit1" value="保存"/>
    		<input type="submit" name="submit2" value="退出"/>
    </form>
  </body>
</html>