<%@ page language="java" import="java.util.*,java.io.*"
contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html><head><title>Rich Text Editing</title></head>
<body>
    <div id="richedit" name="edit" style="height:300px;width:600px;border:solid 2px black" contenteditable="true">  
    </div><br>  
    <input type="button" value="斜体" onclick="italic()">  
    <input type="button" value="颜色" onclick="color()">  
    <input type="button" value="字号" onclick="big()">  
    <input type="button" value="图像" onclick="pic()">  
    <input type="button" value="链接" onclick="link()">  
    <input type="button" value="撤销" onclick="cancle()">  
    <input type="button" value="代码" onclick="show()">  
    <script>  
        function italic() {  
            document.execCommand("italic",false,null);  
        }  
        function big() {  
            document.execCommand("fontsize",false,7);  
        }  
        function color() {  
            document.execCommand("forecolor",false,"red");  
        }  
        function pic() {  
            document.execCommand("insertimage",false,"images/home.gif");  
        }  
        function link() {  
            document.execCommand("createlink",false,"http://www.sysu.edu.cn");  
        }  
        function cancle() {  
            var o = document.execCommand('undo');  
        }  
        function show() {  
            var o=document.getElementById("richedit");  
            alert(o.innerHTML);  
        }  
    </script>  
</body>
</html>