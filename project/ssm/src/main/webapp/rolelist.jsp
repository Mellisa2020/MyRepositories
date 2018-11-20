<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>滴答办公系统-角色列表</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/layui/css/layui.css" ${pageContext.request.contextPath}/media="all">
<script src="${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
</head>
<body>
	<div class="layui-container">
			<div class="layui-btn-group">
				<button class="layui-btn layui-btn-norma" onclick="addRole()">
				<i class="layui-icon">&#xe654;</i>添加角色
			</button>
			</div>
	</div>
	<div class="layui-container">
		<table class="layui-table" id="tbdata" lay-filter="tbop">
			<thead>
				<tr>
					<td>序号</td>
					<td>角色名称</td>
					<td>角色备注</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pageBean.list}" var="role">
				<tr>
					<td>${role.id }</td>
					<td>${role.name }</td>
					<td>${role.remark }</td>
					<td>
						<a class="layui-btn layui-btn-mini" href="#" onclick="showPermissions(${role.id});">编辑</a>
						<a class="layui-btn layui-btn-danger layui-btn-mini"
						lay-event="del" onclick="deleteCourse(${role.id});">删除</a>
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
	   function deleteCourse(id){
		   $.get("${pageContext.request.contextPath}/role/delete/"+id,null,function(obj){
			   if(obj==true){
				   alert("删除成功！");
			   }else{
				   alert("删除失败！");
			   }
			   window.location.reload(true);
		   });
		   /* layui.use('table', function() {
			   layer.confirm('是否确认删除角色?',function(index) {
				   layer.msg("删除成功", {icon : 6});
				   layer.msg("删除失败", {icon : 5});
			   });
		   }); */
	   }
	   
	   var form;
	   
	   function showPermissions(id){
		   $.get("${pageContext.request.contextPath}/role/updaterole/"+id,null,function(obj){
			   var role = obj.role;
			   var menus = obj.menus;
			   var permissions = obj.permissions;
			   $("#id_update").val(role.id);
				$("#name_update").val(role.name);
				$("#remark_update").val(role.remark);
			   
				for(var i = 0;i<menus.length;i++){
					if(menus[i].parentid==0){
						if(menus[i].flag==1){
							$("#update_menus").append("<input type='checkbox' name='menus_ids' value='"+menus[i].id+"' title='"+menus[i].name+"' checked>");
						}else{
							$("#update_menus").append("<input type='checkbox' name='menus_ids' value='"+menus[i].id+"' title='"+menus[i].name+"'>");
						}
						for(var j = 0;j < menus.length;j++){
							if(menus[j].parentid==menus[i].id){
								if(menus[j].flag==1){
									$("#update_menus").append("</br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type='checkbox' name='permission_ids' value='"+menus[j].id+"' title='"+menus[j].name+"' checked>");
								}else{
									$("#update_menus").append("</br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type='checkbox' name='permission_ids' value='"+menus[j].id+"' title='"+menus[j].name+"'>");
								}
								
							}
						}
					}
				} 
				
				for(var i = 0;i<permissions.length;i++){
					if(permissions[i].flag==1){
						$("#update_per").append("<input type='checkbox' name='menus_ids' value='"+permissions[i].id+"' title='"+permissions[i].name+"' checked>");
					}else{
						$("#update_per").append("<input type='checkbox' name='menus_ids' value='"+permissions[i].id+"' title='"+permissions[i].name+"'>");
					}
				}
				
				
				layui.use('table', function() {
					form=layui.form;
					layer.open({
						area: ['500px', '480px'],
						title: '更新角色',
						type: 1,
						content: $('#dvlay_update'), //这里content是一个普通的String
						btn: ['更新', '取消'],
						yes:function(){
							$.ajax({
								url:"${pageContext.request.contextPath}/role/update",
								data:$("#fm1_update").serialize(),
								type:"post",
								dataType:"json",
								success:function(reslut){
									if(reslut==true){
										alert("更新角色成功！");
									}else{
										alert("更新角色失败！");
									}
								}
							});
							window.location.reload(true);
						},
						cancel: function() {
							window.location.reload(true);
						}
					});
		    });
		   });
		   
		   
	   	  
	   }
	   
	   
	function addRole(){
		$.get("${pageContext.request.contextPath}/role/addrole",null,function(obj){
			var menus = obj.menus;
			var permissions = obj.permissions;
			
			for(var i = 0;i<menus.length;i++){
				if(menus[i].parentid==0){
					$("#add_menus").append("<input type='checkbox' name='menus_ids' value='"+menus[i].id+"' title='"+menus[i].name+"'>");
					for(var j = 0;j < menus.length;j++){
						if(menus[j].parentid==menus[i].id){
							$("#add_menus").append("</br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type='checkbox' name='permission_ids' value='"+menus[j].id+"' title='"+menus[j].name+"'>");
						}
					}
				}
			} 
			
			for(var i = 0;i<permissions.length;i++){
				$("#add_per").append("<input type='checkbox' name='menus_ids' value='"+permissions[i].id+"' title='"+permissions[i].name+"'>");
			}
			layui.use('table', function() {
				form=layui.form;
				layer.open({
					area: ['500px', '480px'],
					title: '添加角色',
					type: 1,
					content: $('#dvlay_add'), //这里content是一个普通的String
					btn: ['新增', '取消'],
					yes:function(){
						$.ajax({
							url:"${pageContext.request.contextPath}/role/add",
							data:$("#fm1_add").serialize(),
							type:"post",
							dataType:"json",
							success:function(result){
								if(result==true){
									alert("添加角色成功！");
								}else{
									alert("添加角色失败！");
								}
								window.location.reload(true);
							}
						
						});
					},
					cancel: function() {
						window.location.reload(true);
					}
				});
	    });
			
			
		});
		
	}	   
	   
	   
	</script>
</body>
</html>

<div style="display: none;margin-top: 10px;width: 480px" id="dvlay_add">
    <form id="fm1_add" class="layui-form layui-form-pane" method="post">
        <div class="layui-form-item" pane >
            <label class="layui-form-label">角色名称：</label>
            <div class="layui-input-inline">
            	<input id="id" name="id" class="layui-input" type="hidden">
                <input id="id" name="name" class="layui-input">
            </div>
        </div>
         <div class="layui-form-item" pane >
            <label class="layui-form-label">角色备注：</label>
            <div class="layui-input-inline">
                <input id="id" name="remark" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" pane>
            <label class="layui-form-label">菜单：</label>
            <div class="layui-input-inline" id="add_menus">
            </div>
        </div>
        <div class="layui-form-item" pane>
            <label class="layui-form-label">权限：</label>
            <div class="layui-input-inline" id="add_per">
            </div>
        </div>
    </form>
</div>


<div style="display: none;margin-top: 10px;width: 480px" id="dvlay_update">
    <form id="fm1_update" class="layui-form layui-form-pane" method="post">
        <div class="layui-form-item" pane >
            <label class="layui-form-label">角色名称：</label>
            <div class="layui-input-inline">
            	<input id="id_update" name="id" class="layui-input" type="hidden">
                <input id="name_update" name="name" class="layui-input" >
            </div>
        </div>
         <div class="layui-form-item" pane >
            <label class="layui-form-label">角色备注：</label>
            <div class="layui-input-inline">
                <input id="remark_update" name="remark" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item" pane>
            <label class="layui-form-label">菜单：</label>
            <div class="layui-input-inline" id="update_menus">
            </div>
        </div>
        <div class="layui-form-item" pane>
            <label class="layui-form-label">权限：</label>
            <div class="layui-input-inline" id="update_per">
            </div>
        </div>
    </form>
</div>


