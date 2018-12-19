<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.util.*,org.apache.commons.io.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%String fileName = ""; %>
<%String fileURL = ""; %>
<%String preName = ""; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>a{color:blue;}</style>
<title>文件传输例子</title>
</head>
<body>
	<%  request.setCharacterEncoding("utf-8");%>
	<%  boolean isMultipart = ServletFileUpload.isMultipartContent(request);//检查表单中是否包含文件
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			//factory.setSizeThreshold(yourMaxMemorySize); //设置使用的内存最大值
			//factory.setRepository(yourTempDirectory); //设置文件临时目录
			ServletFileUpload upload = new ServletFileUpload(factory);
			//upload.setSizeMax(yourMaxRequestSize); //允许的最大文件尺寸
			List items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fi = (FileItem) items.get(i);
				if (fi.isFormField()) {//如果是表单字段
					preName = fi.getString("utf-8") + "_";
				}
				else{
					DiskFileItem dfi = (DiskFileItem) fi;
					if (!dfi.getName().trim().equals("")) {//getName()返回文件名称或空串
						out.print("文件被上传到服务上的实际位置： ");
						fileName = preName + FilenameUtils.getName(dfi.getName());
						String filepath=application.getRealPath("/temp/files")
								 + System.getProperty("file.separator") //获取系统文件分隔符
								 + fileName;
						fileURL = "<a href=\"" + "temp/files/" + fileName + "\">" +fileName + "</a>";
						out.println(new File(filepath).getAbsolutePath());
						dfi.write(new File(filepath));
					}
				}
			}
		}
	%>
	</br>
	<%=fileURL%>
</body>
</html>