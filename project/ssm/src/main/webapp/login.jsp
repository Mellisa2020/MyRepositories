<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>滴答办公系统-登录</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<!-- load css -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/media/layui/css/layui.css"
	media="all">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/media/css/login.css"
	media="all">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/media/css/verify.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/media/js/login.js"></script>
<script type="application/javascript"
		src="${pageContext.request.contextPath}/media/js/verify.min.js"></script>
</head>
<body class="layui-bg-black">
	<div class="layui-canvs"></div>
	<div class="layui-layout layui-layout-login">
		<form action="${pageContext.request.contextPath}/user/login"
			method="post">
			<h1>
				<strong>滴答办公系统登录</strong> <em>Tick-tock Office System</em>
			</h1>
			<div class="layui-user-icon larry-login">
				<input type="text" placeholder="账号" class="login_txtbx"
					name="username" value="admin" />
			</div>
			<div class="layui-pwd-icon larry-login">
				<input type="password" placeholder="密码" name="password"
					value="123" class="login_txtbx" />
			</div>
			<div class="feri-code">
				<div id="mpanel4"></div>
			</div>
			<div class="layui-submit larry-login">
				<input type="submit" id="btn1" disabled="disabled" value="立即登陆"
					class="submit_btn" />
			</div>
		</form>
		<div class="layui-login-text">
			<p>© 2016-2018 北京滴答科技有限公司 Feri 版权所有</p>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
			//滑动验证码
			$('#mpanel4').pointsVerify({
				defaultNum : 6, //默认的文字数量
				checkNum : 1, //校对的文字数量
				vSpace : 5, //间隔
				imgName : [ '1.jpg', '2.jpg', '3.jpg' ],
				imgSize : {
					width : '400px',
					height : '200px',
				},
				barSize : {
					width : '400px',
					height : '40px',
				},
				ready : function() {
				},
				success : function() {
					//......后续操作
					$("#btn1").attr("disabled", false);
				},
				error : function() {
				}
			});
		});
	</script>
</body>
</html>