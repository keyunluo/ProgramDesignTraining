<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息管理系统</title>

	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/theme.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="bootstrap/css/front-page.css">
	<script src="bootstrap/js/JQuery.js" type="text/javascript"></script>
    <script src="bootstrap/js/bootstrap.js" type="text/javascript"></script>
    <SCRIPT language=javascript type=text/javascript>
	
	//换一张
	function loadimage(){
		document.getElementById("randImage").src = "image.jsp?" + Math.random();//禁止缓存
	}
</SCRIPT>
   
</head>

<body class="">
    <div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">
                    
                </ul>
                <a class="brand" href="login.jsp"><span class="first">学生信息管理</span> <span class="second">系统</span></a>
        </div>
    </div>
    <div class="container" style="margin-top:100px" ">
      <form class="form-signin" id=adminlogin  method=post name=adminlogin action="user!login">
         <div class="page-header" >
          <h2>学生信息管理系统 </h2>
        </div>
        <input type="text" class="input-block-level" placeholder="用户名"  value="${user.userName }" name="user.userName" id="userName"/>
        <input type="password" class="input-block-level" placeholder="密码"  value="${user.password }" name="user.password" id="password"/>
        <input  class=yzm size=8 type=text value="${imageCode }" class="input-block" name="imageCode" id="imageCode" placeholder="请输入验证码">
		<img onclick="javascript:loadimage();" title="换一张" name="randImage" id="randImage" src="image.jsp" width="60" height="20" border="1" align="absmiddle">
        
        <label class="checkbox">
       <input type="checkbox" value="remember-me" class="right"> 记住我 
        </label>
        
        <button class="btn  btn-primary" type="submit">登录</button>
        <button class="btn  " type="reset">重置</button>
        <a href="register.jsp">注册</a>
      </form>
       <footer>
                        <hr>
                        <p class="pull-right">&copy; 2015.12 <a href="#" target="_blank">streamer</a></p>
                    </footer>

    </div> 
     
  
  </body>
</html>