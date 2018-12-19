function showMsgBox(boxId,title, msg){
    var msgBox = document.getElementById(boxId);
    document.querySelector("div.msgtitle").innerHTML = title;
    document.querySelector("div.msgcontent").innerHTML = msg;
    msgBox.style.display = "block";
    pos();
    showShadow();
}
function hideMsgBox(boxId){
    var msgBox = document.getElementById(boxId);
    msgBox.style.display = "none";
    hideShadow();
}
function showShadow(){
    var shadow=document.getElementById("shadow");
    shadow.style.width= ""+document.documentElement.scrollWidth+"px";
    if(document.documentElement.clientHeight>document.documentElement.scrollHeight)
       shadow.style.height=""+document.documentElement.clientHeight+"px";
    else
       shadow.style.height=""+document.documentElement.scrollHeight+"px";
    shadow.style.display="block";
}
function hideShadow(){
    var shadow = document.getElementById("shadow");
    shadow.style.display = "none";
}

function pos(){
    var msgbox = document.getElementById("msgbox1");
    msgbox.style.left = (document.documentElement.clientWidth / 2 - msgbox.clientWidth / 2) + "px";
    msgbox.style.top = (document.documentElement.clientHeight / 2 - msgbox.clientHeight / 2) + "px";
}
    
function check(){
    var title = "姓名";
    var msg = document.getElementById("name").value;
    if (msg == "") {
        title = "错误信息";
        msg = "姓名不能为空";
    }
    showMsgBox("msgbox1", title, msg);
}