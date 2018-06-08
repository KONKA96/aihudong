<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":"
			+ request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>校园版后台管理系统</title>
<%-- <jsp:include page="common/include_css.jsp" />
<jsp:include page="common/include_js.jsp" /> --%>

<link href="<%=basePath %>resources/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="<%=basePath %>resources/css/style.css?v=4.1.0" rel="stylesheet">
<link href="<%=basePath %>resources/css/login.css" rel="stylesheet">
<script src="<%=basePath%>resources/js/jquery.min.js?v=2.1.4"></script>
</head>
<body>
	<script type="text/javascript">
		if(window.top.location.href!=location.href)      
		{         
		    window.top.location.href=location.href;      
		}  
	</script>
	<div style="width: 400px;margin-left: auto;margin-right: auto;margin-top: 200px;">
		<form class="form-horizontal" id="editForm">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">用户名</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" 
						placeholder="请输入用户名" name="username" ">
					<p class="bg-danger" id="umAlt"></p>
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword3" class="col-sm-4 control-label">密码</label>
				<div class="col-sm-8">
					<input type="password" class="form-control" id="inputPassword3"
						placeholder="请输入密码" name="password"">
					<p class="bg-danger" id="pwAlt"></p>
				</div>
			</div>

			<!-- <label for="inputPassword3" class="col-sm-4 control-label">角色：</label>
			<div class="col-sm-8">
				
				<select class="form-control m-b" name="power">
					<option selected>--请选择--</option>
					<option value="0">--根级管理员--</option>
					<option value="1">--一级管理员--</option>
					<option value="2">--操作管理员--</option>
				</select>
			</div> -->

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" class="btn btn-default" onclick="exeLogin()">登陆</button>
				</div>
			</div>
		</form>
	
	</div>
	<script type="text/javascript">
	$(document).keydown(function(event){
		if(event.keyCode==13){
			exeLogin();
		}
　　});
	
		function exeLogin(){
			$.ajax({
				url:"/aihudong-web/login/adminLogin",
				data:$("#editForm").serialize(),
				type:"post",
				success:function(data){
					if(data=='success'){
						alert("登录成功！");
						window.location="/aihudong-web/admin/test";
					}else{
						alert("登录失败");
					}
				}
			})
		}
	</script>
</body>
</html>