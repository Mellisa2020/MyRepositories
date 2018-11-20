<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
</head>
<body>
	<div class="layui-container" style="margin-top: 5px">
		<form class="layui-form" action="${pageContext.request.contextPath}/grade/addGrade" method="post">
			<div class="layui-form-item">
				<label class="layui-form-label">班级名称</label>
				<div class="layui-input-block">
					<input type="text" name="name" lay-verify="name" autocomplete="off"
						placeholder="请输入名称" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">班级学科</label>
				<div class="layui-input-block">
					<select name="cid" id="cds">
					<option>--请选择学科--</option>
        				 <c:forEach items="${courses}" var="c"> 
							 	<option value="${c.id}">${c.name}</option>
        				 </c:forEach>
      				</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">班级人数</label>
				<div class="layui-input-block">
					<input type="text" id="f7" lay-verify="name" autocomplete="off"
						 class="layui-input" name="count" value="${grade.count}">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">开班日期</label>
				<div class="layui-input-block">
				 <input type="text" name="createdate" id="date" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">班级周期</label>
				<div class="layui-input-block">
					<input type="text" name="week" lay-verify="name" autocomplete="off"
						placeholder="请输入周期" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">班级地址</label>
				<div class="layui-input-block">
					<input type="text" name="location" lay-verify="name" autocomplete="off"
						placeholder="请输入地址" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<input class="layui-btn"  style="margin-left: 10%" type="submit" value="确认新增">
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
		
		//初始化数据
		function initData() {
			$.get("courseall.do",null,function(arr){
				for(i=0;i<arr.length;i++){
					$("#cds").append("<option value='"+arr[i].id+"'>"+arr[i].name+"</option>");
				}
				form.render("select");
			})
		}
		
	</script>
</body>
</html>