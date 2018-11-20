<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>滴答办公系统-学科列表</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/layui/css/layui.css" media="all">
<script src="${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
<script type="text/javascript">

	//跳转页码
	function goPage(pageIndex){
		var pageSize=$("#psize").val();//页码
		$("#courseform").attr("action","${pageContext.request.contextPath}/course/list/"+pageIndex+"?pageSize="+pageSize);
		$("#courseform").submit(); //提交表单参数
	}
	
	//更新页面大小
	function changeSize(obj){
		var size=obj.value;
		$("#courseform").attr("action","${pageContext.request.contextPath}/course/list/1?pageSize="+size);
		$("#courseform").submit(); //提交表单参数
	}
	
</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/course/list/1" id="courseform">
       
    </form>
	<div class="layui-container">
		<table class="layui-table" id="tbdata" lay-filter="tbop">
			<thead>
				<tr>
					<td>序号</td>
					<td>学科名称</td>
					<td>周数</td>
					<td>成立日期</td>
					<td>总数</td>
					<td>类型</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pageBean.list}" var="c">
				<tr>
					<td>${c.id }</td>
					<td>${c.name }</td>
					<td>${c.week }</td>
					<td>${c.createdate }</td>
					<td>${c.count }</td>
					<td>${c.type }</td>
					<td><a class="layui-btn layui-btn-mini" href="${pageContext.request.contextPath}/course/update/${c.id}">编辑</a>
						<a class="layui-btn layui-btn-danger layui-btn-mini"
						lay-event="del" href="${pageContext.request.contextPath}/course/delete/${c.id}"">删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="layui-box layui-laypage layui-laypage-default" id="layui-laypage-1">
							    <i class="layui-icon"><a href="#" onclick="goPage(${pageBean.pageIndex-1});">&lt;</a></i>
						    <c:forEach begin="${pageBean.startNum}" end="${pageBean.endNum}" step="1" var="i">
						           <c:if test="${pageBean.pageIndex==i}">
						            <span style="color:#009688;font-weight: bold;margin-top:-12px">${i}</span> 
						           </c:if>
						           <c:if test="${pageBean.pageIndex!=i}">
						              <a href="#" onclick="goPage(${i})" style="margin-top:-12px">${i}</a>
						           </c:if>
       						</c:forEach>
							     
							    <i class="layui-icon"><a href="#" onclick="goPage(${pageBean.pageIndex+1});">&gt;</a></i>
							<span class="layui-laypage-skip">到第
							   <input type="text" min="1" value="1" class="layui-input">页
								<button type="button" class="layui-laypage-btn">确定</button>
							</span> 
							<span class="layui-laypage-count">共${pageBean.totalCount}条</span> 
							<span class="layui-laypage-limits"> 
							    <select name="#" onchange="changeSize(this);">
							        <option value="5" selected>5 条/页</option>
									<option value="10">10条/页</option>
									<option value="15">15 条/页</option>
									<option value="20">20 条/页</option>
							</select>
							</span>
						</div>
	</div>
	<script src="${pageContext.request.contextPath}/media/layui/layui.js"></script>
	
	<script type="text/javascript">
	   function deleteCourse(){
		   layui.use('table', function() {
			   layer.confirm('是否确认删除学科?',function(index) {
				   layer.msg("删除成功", {icon : 6});
				   layer.msg("删除失败", {icon : 5});
			   });
		   });
	   }
	</script>
	
	
</body>
</html>