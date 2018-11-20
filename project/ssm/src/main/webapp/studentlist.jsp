<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>滴答办公系统-学员列表</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/layui/css/layui.css" media="all">
<script src="${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
<script>
//跳转页码
function goPage(pageIndex){
	var pageSize=$("#psize").val();//页码
	$("#studentform").attr("action","${pageContext.request.contextPath}/student/listSearch/"+pageIndex+"?pageSize="+pageSize);
	$("#studentform").submit(); //提交表单参数
}

//更新页面大小
function changeSize(obj){
	var size=obj.value;
	$("#studentform").attr("action","${pageContext.request.contextPath}/student/listSearch/1?pageSize="+size);
	$("#studentform").submit(); //提交表单参数
}

//导入Excel
function exportExcel(){
	$("#studentFrom").attr("action","${pageContext.request.contextPath}/student/exportStudent");
	$("#studentFrom").submit();
}

</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/student/listSearch/1" id="studentfrom">
       
    </form>
<div class="layui-container">
    <div class="layui-row" style="margin-top: 10px">
    	<form action="${pageContext.request.contextPath}/student/listSearch/1" id="studentFrom">
        <div class="layui-col-xs3" style="margin-right: 20px">
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">姓名：</label>
                <div class="layui-input-block">
                    <input type="text" id="searchName" name="searchName" class="layui-input" placeholder="学生姓名" value="${sstuname}">
                </div>
            </div>
        </div>
        <div class="layui-col-xs3" style="margin-right: 20px">
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">班级：</label>
                <div class="layui-input-block">
                    <select name="searchgid" class="layui-input" id="fg">
                        <option value="">--请选择班级--</option>
                        <c:forEach items="${grades }" var="g">
                        	
                        	<c:if test="${g.id==sgid}">
							 	<option selected="selected" value="${g.id }">${g.name}</option>
							 </c:if>
	        				 	<c:if test="${g.id!=sgid}">
							 	<option value="${g.id }">${g.name}</option>
							 </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-col-xs2">
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <input id="search" class="layui-btn" type="submit" value="搜索">
                </div>
            </div>
        </div>
       
        <div class="layui-col-xs2">
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <a class="layui-btn layui-btn-mini layui-btn-mini" href="javascript:exportExcel();" lay-event="detail">导出Excel</a>
                </div>
            </div>
        </div>
         </form>
    </div>
</div>
	
	<div class="layui-container">
		<table class="layui-table" id="tbdata" lay-filter="tbop">
			<thead>
				<tr>
					<td>学号</td>
					<td>姓名</td>
					<td>班级</td>
					<td>性别</td>
					<td>手机号</td>
					<td>邮箱</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageBean.list}" var="stu">
				<tr>
					<td>${stu.no}</td>
					<td>${stu.name}</td>
					<td>${stu.grade.name}</td>
					<td>${stu.sex}</td>
					<td>${stu.phone }</td>
					<td>${stu.email }</td>
					<td><a class="layui-btn layui-btn-mini" href="${pageContext.request.contextPath}/student/update/${stu.id}">编辑</a>
						<a class="layui-btn layui-btn-mini layui-btn-mini" href="${pageContext.request.contextPath}/student/details/${stu.id}" lay-event="detail">查看详情</a>
						<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del" href="${pageContext.request.contextPath}/student/delete/${stu.id}">删除</a>
					</td>
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
			   layer.confirm('是否确认删除学生?',function(index) {
				   layer.msg("删除成功", {icon : 6});
				   layer.msg("删除失败", {icon : 5});
			   });
		   });
	   }
	</script>
	
	
</body>
</html>