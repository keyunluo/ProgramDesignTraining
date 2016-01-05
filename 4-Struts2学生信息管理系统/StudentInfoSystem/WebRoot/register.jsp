<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册页面</title>
    
	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/theme.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="bootstrap/css/front-page.css">
	<script src="bootstrap/js/JQuery.js" type="text/javascript"></script>
    <script src="bootstrap/js/bootstrap.js" type="text/javascript"></script>
   
  </head>
 <!--  -->
  <body class="">
    <div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">                  
                </ul>
                <a class="brand" href="login.jsp"><span class="first">学生信息管理系统</span> <span class="second">注册</span></a>
        </div>
    </div>
    <div class="container" style="margin-top:100px" ">
      <form class="form-signin" action="user!register" method="post" name="form1">
         <div class="page-header" >
          <h2>用户注册 </h2>
        </div>

    	用户名:<input type="text" value="${user.userName }" name="user.userName" id="userName" size="20"/><br/>
    	密&nbsp;&nbsp;&nbsp;码:<input type="password" value="${user.password }" name="user.password" id="password" size="21"/><br/>
    	
    	<button class="btn  btn-primary" type="submit">注册</button>
    	<button class="btn  " type="reset">重置</button>
    	<a href="login.jsp">已有账号?</a>
        </form><br>
        
       
       <footer>
             <hr>
               <p class="pull-right">&copy; 2015.12 <a href="#" target="_blank">streamer</a></p>
       </footer>

    </div> 
     
  
  </body>
  
</html>
