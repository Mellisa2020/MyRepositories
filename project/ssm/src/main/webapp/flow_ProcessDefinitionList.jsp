<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>滴答办公系统-审批流程管理</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/layui/css/layui.css" ${pageContext.request.contextPath}/media="all">
<script src="${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
</head>
<body>
	<div class="layui-container">
		<table class="layui-table" id="tbdata" lay-filter="tbop">
			<thead>
				<tr>
					<td>序号</td>
					<td>流程名称</td>
					<td>流程Key</td>
					<td>最新版本</td>
					<td>说明</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${list}" var="pd">
				<tr>
					<td>${pd.id}</td>
					<td>${pd.name}</td>
					<td>${pd.key}</td>
					<td>${pd.version}</td>
					<td>${pd.description}</td>
					<td><a class="layui-btn layui-btn-mini" href="${pageContext.request.contextPath}/process/processpng/${pd.id}">查看流程图</a>
						<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del" onclick="deletePd(${pd.deploymentId});">删除</a></td>
				</tr>
			 </c:forEach>
			</tbody>
		</table>
		
	<script src="${pageContext.request.contextPath}/media/layui/layui.js"></script>
	
	<script type="text/javascript">
	   function deletePd(deploymentId){
		   layui.use('table', function() {
			   layer.confirm('是否确认删除该流程图?',function(index) {
				   location.href="${pageContext.request.contextPath}/process/deletedeploy/"+deploymentId;
				   //layer.msg("删除成功", {icon : 6});
				   //layer.msg("删除失败", {icon : 5});
			   });
		   });
	   }
	</script>
	
	
</body>
</html>