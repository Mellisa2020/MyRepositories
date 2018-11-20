<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>滴答办公系统-用户列表</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/layui/css/layui.css" ${pageContext.request.contextPath}/media="all">
<script src="${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
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
	   
	   //启用禁用
	   function changeState(){
	   	   layui.use('table', function() {
			   layer.confirm('确认禁用吗?',function(index) {
				   layer.msg("操作成功", {icon : 6});
			   });
		   });
	   }
	   
	    var form;
		   
	    //弹出编辑框
		   function showUpdate(id){
			   $.get("${pageContext.request.contextPath}/userRole/update/"+id, null, function(obj) {
				   var user=obj.user;
				   $("#update_id").val(user.id);
				   $("#update_usercode").val(user.usercode);
				   $("#update_username").val(user.username);
				   
				   var roles=obj.roles;
				   for(var j=0;j<roles.length;j++){
					   if(roles[j].flag==true){
					      $("#dv1_update").append("<input type='checkbox' name='role_ids' value='"+roles[j].id+"' title='"+roles[j].name+"' checked>");
					   }else{
						   $("#dv1_update").append("<input type='checkbox' name='role_ids' value='"+roles[j].id+"' title='"+roles[j].name+"' >"); 
					   }
				   }
				   //---------------------------------------------------------
				   
				   layui.use('table', function() {
						form=layui.form;
						layer.open({
							area: ['500px', '480px'],
							title: '用户编辑',
							type: 1,
							content: $('#dvlay_update'), //这里content是一个普通的String
							btn: ['更新', '取消'],
							yes:function(index, layero){
								$.ajax({
									url:"${pageContext.request.contextPath}/userRole/updateUserRole",
									type:"post",
									dataType:"json",
									data:$("#fm1_update").serialize(),  
									success:function(result){
										if(result==true){
											layer.msg("更新成功", {icon : 6},function(){
												window.location.reload();//刷新页面
											});
										}else{
											layer.msg("更新失败", {icon : 5},function(){
												window.location.reload();//刷新页面
											});
										}
									}
								});
								layer.close(index);//关闭窗口
								window.location.reload();//刷新页面
							},
							cancel: function() {
								window.location.reload();//刷新页面
							}
						});
			       });
			   });
			   
			   
		   	 
		   }
	</script>
	
</head>
<body>
<div class="layui-container">

    <div class="layui-row" style="margin-top: 10px">
    <form action="${pageContext.request.contextPath}/userRole/list/1">
        <div class="layui-col-xs3" style="margin-right: 20px">
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">姓名：</label>
                <div class="layui-input-block">
                    <input type="text" id="no" class="layui-input" placeholder="用户姓名" name="username" value="${username}">
                </div>
            </div>
        </div>
        <div class="layui-col-xs3" style="margin-right: 20px">
            <div class="layui-form-item layui-form-text">
                <label class="layui-form-label">用户状态：</label>
                <div class="layui-input-block">
                    <select class="layui-input" id="fg" name="locked">
                       	    <option value="">请选择</option>
                       	<c:if test="${locked==null}">
	                        <option value="0">启用</option>
	                        <option value="1">无效</option>
                        </c:if>
                    	<c:if test="${locked=='0'}">
	                        <option value="0" selected="selected">启用</option>
	                        <option value="1">无效</option>
                        </c:if>
                        <c:if test="${locked=='1'}">
	                        <option value="0">启用</option>
	                        <option value="1" selected="selected">无效</option>
                        </c:if>
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
            </form>
    </div>

</div>
	
	<div class="layui-container">
		<table class="layui-table" id="tbdata" lay-filter="tbop">
			<thead>
				<tr>
					<td>序号</td>
					<td>工号</td>
					<td>姓名</td>
					<td>职位</td>
					<td>状态</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
			
			<c:forEach items="${pageBean.list}" var="ur">
				<tr>
					<td>${ur.id}</td>
					<td>${ur.usercode}</td>
					<td>${ur.username}</td>
					<td>
					<c:forEach items="${ur.roles}" var="u">
						${u.name}&nbsp;&nbsp;&nbsp;
					</c:forEach>
					</td>
					<td><span style="color: #1E9FFF">
					<c:if test="${ur.locked=='0' }">启用</c:if>
					<c:if test="${ur.locked=='1' }">禁用</c:if>
					</span></td>
					<td><a class="layui-btn layui-btn-mini" href="#" onclick="showUpdate(${ur.id});">编辑</a>
						<a class="layui-btn layui-btn-danger layui-btn-mini" href="javascript:changeState();" lay-event="detail">禁用</a>
						<a class="layui-btn layui-btn-danger layui-btn-mini"
						lay-event="del" onclick="deleteCourse();">删除</a>
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
	
	
</body>
</html>

<div style="display: none;margin-top: 10px;width: 480px" id="dvlay_update">
    <form id="fm1_update" class="layui-form layui-form-pane" >
        <div class="layui-form-item" pane >
            <label class="layui-form-label">工号：</label>
            <div class="layui-input-inline">
                <input id="update_id" name="id" type="hidden" class="layui-input">
                <input id="update_usercode" name="usercode" class="layui-input">
            </div>
        </div>
         <div class="layui-form-item" pane >
            <label class="layui-form-label">姓名：</label>
            <div class="layui-input-inline">
                <input id="update_username" name="username" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" pane>
            <label class="layui-form-label">职位：</label>
            <div class="layui-input-inline" id="dv1_update">
                
            </div>
        </div>
    </form>
</div>