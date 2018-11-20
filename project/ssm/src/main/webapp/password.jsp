<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>滴答办公系统-班级新增</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/layui/css/layui.css" media="all">
<script type="text/javascript" src="${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
</head>
<body>
	<div class="layui-container" style="margin-top: 5px">
		<form class="layui-form" action="${pageContext.request.contextPath}/user/change/${user.id}" method="post">
			<div class="layui-form-item">
				<label class="layui-form-label">原始密码</label>
				<div class="layui-input-block">
					<input type="text" name="oldpwd" lay-verify="name" autocomplete="off"
						placeholder="请输入密码" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">新密码</label>
				<div class="layui-input-block">
				 <input type="password" name="newpwd" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">确认新密码</label>
				<div class="layui-input-block">
			      <input type="password" name="repwd" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<input class="layui-btn"  style="margin-left: 10%" type="submit" value="确认修改">
			</div>
		</form>
	</div>


	<script src="${pageContext.request.contextPath}/media/layui/layui.js"></script>
	<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
	<script>
	var form;
		layui.use([ 'form', 'laydate' ],
				function() {
				form = layui.form, layer = layui.layer, laydate = layui.laydate;
						//日期
						laydate.render({
							elem : '#date'
						});
						initData();
		});
		
	</script>
</body>
</html>