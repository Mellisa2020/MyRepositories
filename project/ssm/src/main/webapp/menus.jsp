<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>滴答办公系统-资源列表</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/media/layui/css/layui.css" media="all">
		<link rel="stylesheet" href="media/css/app.css" media="all">
    	<link rel="stylesheet" href="media/css/font-awesome.min.css">
		<script src="${pageContext.request.contextPath}/media/js/jquery.min.js"></script>
		<script>
			//跳转页码
			function goPage(pageIndex){
				var pageSize=$("#psize").val();//页码
				$("#menusfrom").attr("action","${pageContext.request.contextPath}/menu/list/"+pageIndex+"?pageSize="+pageSize);
				$("#menusfrom").submit(); //提交表单参数
			}
	
			//更新页面大小
			function changeSize(obj){
				var size=obj.value;
				$("#menusfrom").attr("action","${pageContext.request.contextPath}/menu/list/1?pageSize="+size);
				$("#menusfrom").submit(); //提交表单参数
			}
		</script>
	</head>

	<body>
	<form action="${pageContext.request.contextPath}/menu/list/1" id="menusfrom">
       
    </form>
		<div class="layui-container">
			<div class="layui-btn-group">
				<button class="layui-btn layui-btn-norma" onclick="addAuth()">
				<i class="layui-icon">&#xe654;</i>添加权限
			</button>
			</div>
		</div>
		<div class="layui-container">
			<table class="layui-table" id="tbdata" lay-filter="tbop">
				<thead>
					<tr>
						<td>序号</td>
						<td>名称</td>
						<td>页面路径</td>
						<td>图标</td>
						<td>级别</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageBean.list }" var="m">
						<tr>
							<td>${m.id }</td>
							<td>${m.name }</td>
							<td>${m.url }</td>
							<td>${m.icon }</td>
							<c:if test="${m.parentid==0 }"><td>一级菜单</td></c:if>
							<c:if test="${m.parentid!=0 }"><td>二级菜单</td></c:if>
							<td>
							<c:if test="${m.parentid==0 }">
								<a class="layui-btn layui-btn-mini" href="javascript:updateMenu1(${m.id});">编辑</a>
							</c:if>
							<c:if test="${m.parentid!=0 }">
								<a class="layui-btn layui-btn-mini" href="javascript:updateMenu2(${m.id});">编辑</a>
							</c:if>
								<a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del" href="${pageContext.request.contextPath}/menu/delete/${m.id}">删除</a>
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
	
			function deleteMenus() {
				layui.use('table', function() {
					layer.confirm('是否确认删除菜单?', function(index) {
						layer.msg("删除成功", {
							icon: 6
						});
						layer.msg("删除失败", {
							icon: 5
						});
					});
				});
			}
	
//添加权限事件-----------------------------------------------------------------					
			var form;

			function addAuth() {
				layui.use('table', function() {
					form=layui.form;
					
					//给单选框添加事件
					form.on('radio(level)', function (data) {
						changePid(data.value);
					});
					
					form.on('select(parent)', function (obj) {
						setPid(obj);
					});
					//弹出div
					layer.open({
						area: ['500px', '380px'],
						title: '权限页面新增',
						type: 1,
						content: $('#add_permission'), //这里content是一个普通的String
						btn: ['新增', '取消'],
						yes:function(index, layero){
							$("#fm1_add").attr("action","${pageContext.request.contextPath}/menu/add");
							$("#fm1_add").submit();
						},
						cancel: function() {
							alert("取消新增！");
							$("#dvl1").css("display", "none");
						}
					});
				});
			}
			
			function changePid(i) {
				$("#dvl1").css("display", "block");
				form.render();
				if(i == -1) {
					//给下拉框添加数据
					$.get("${pageContext.request.contextPath}/menu/getOneLevel", null, function(arr) {
						for(i = 0; i < arr.length; i++) {
							$("#spid").append("<option value=\"" + arr[i].id + "\">" + arr[i].name + "</option>");
						}
						$("#dvl1").css("display", "block");
						form.render();
					});
				} else {
					$("#dvl1").css("display", "none");
					$("#pid").val(i);
				}
			}

			function setPid(obj) {
				$("#pid").val(obj.value);
			}
			
			//更新1级菜单
			function updateMenu1(id) {
				$.get("${pageContext.request.contextPath}/menu/getById1/"+id, null, function(obj) {
					$("#id_udpate1").val(obj.id);
					$("#name_udpate1").val(obj.name);
					$("#icon_udpate1").val(obj.icon);
					$("#url_udpate1").val(obj.url);
					
					layui.use('table', function() {
						form=layui.form;
						layer.open({
							area: ['500px', '380px'],
							title: '一级菜单更新',
							type: 1,
							content: $('#dvupdate1'), //这里content是一个普通的String
							btn: ['更新', '取消'],
							yes:function(index, layero){
								$.ajax({
									url:"${pageContext.request.contextPath}/menu/updateOneLevelMenu",
									data:$("#fm1_update1").serialize(),
									dataType:"json",
									type:"post",
									success:function(result){
										if(result==true){
											alert("更新成功！");
										}else{
											alert("更新失败！");
										}
									}
								});
								layer.close(index);//关闭窗口
								window.location.reload(true);
							},
							cancel: function() {}
						});
					});
					
				});
			}
			
			//更新2级菜单
			function updateMenu2(id) {
				$.get("${pageContext.request.contextPath}/menu/getById2/"+id,null,function(obj){
					
					var secondMenu = obj.secondMenu;
					var firstMenus = obj.fristMenus;
					
					for(i = 0; i < firstMenus.length; i++) {
						$("#spid_udapte2").append("<option value=\"" + firstMenus[i].id + "\">" + firstMenus[i].name + "</option>");
					}
					
					$("#id_udpate2").val(secondMenu.id);
					$("#name_udpate2").val(secondMenu.name);
					$("#icon_udpate2").val(secondMenu.icon);
					$("#url_udpate2").val(secondMenu.url);
					
					$("#spid_udapte2").val(secondMenu.parentid); //选择下拉框
					
					
					layui.use('table', function() {
						form=layui.form;
						
						form.on('select(parent)', function (obj) {
							setPid(obj);
						});
						
						layer.open({
							area: ['500px', '380px'],
							title: '二级菜单更新',
							type: 1,
							content: $('#dvupdate2'), //这里content是一个普通的String
							btn: ['更新', '取消'],
							yes:function(index, layero){
								$.ajax({
									url:"${pageContext.request.contextPath}/menu/updateTwoLevelMenu",
									type:"post",
									data:$("#fm2_update2").serialize(),
									dataType:"json",
									success:function(reslut){
										if(reslut==true){
											alert("更新成功！");
										}else{
											alert("更新失败！");
										}
									}
								});
								layer.close(index);//关闭窗口
								window.location.reload(true);
							},
							cancel: function() {}
						});
					});
				});
			}
		</script>
	</body>
</html>

<!-- 添加权限div -->
<div style="display: none;margin-top: 10px;width: 480px" id="add_permission">
	<form id="fm1_add" class="layui-form " method="post">
		<div class="layui-form-item">
			<label class="layui-form-label">名称：</label>
			<div class="layui-input-inline">
				<input name="name" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">图标：</label>
			<div class="layui-input-inline">
				<input name="icon" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">路径：</label>
			<div class="layui-input-inline">
				<input name="url" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">级别：</label>
			<div class="layui-input-inline">
				<input type="radio" name="pid" value="0" lay-filter="level" title="一级" checked>
				<input type="radio" name="pid" value="-1" lay-filter="level" title="二级">
			</div>
		</div>
		<!--获取到父id的值 -->
		<input type="hidden" name="parentid" id="pid" value="0">
		
		<div class="layui-form-item" id="dvl1" style="display: none">
			<label class="layui-form-label">上级路径：</label>
			<div class="layui-input-inline">
				<select id="spid" lay-filter="parent">
				</select>

			</div>
		</div>
	</form>
</div>

<div style="display: none;margin-top: 10px;width: 480px" id="dvupdate1">
	<form id="fm1_update1" class="layui-form " method="post">
		<div class="layui-form-item">
			<label class="layui-form-label">名称：</label>
			<div class="layui-input-inline">
				<input type="hidden" name="id" class="layui-input" id="id_udpate1">
				<input name="name" class="layui-input" id="name_udpate1">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">图标：</label>
			<div class="layui-input-inline">
				<input name="icon" class="layui-input" id="icon_udpate1">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">路径：</label>
			<div class="layui-input-inline">
				<input name="url" class="layui-input" id="url_udpate1">
			</div>
		</div>
	</form>
</div>

<div style="display: none;margin-top: 10px;width: 480px" id="dvupdate2">
	<form id="fm2_update2" class="layui-form " method="post">
		<div class="layui-form-item" id="dvl1">
			<label class="layui-form-label">上级路径：</label>
			<div class="layui-input-inline">
				
				<select id="spid_udapte2" lay-filter="parent" name="parentid">
					
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">名称：</label>
			<div class="layui-input-inline">
				<input name="id" class="layui-input" id="id_udpate2" type="hidden">
				<input name="name" class="layui-input" id="name_udpate2">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">图标：</label>
			<div class="layui-input-inline">
				<input name="icon" class="layui-input" id="icon_udpate2">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">路径：</label>
			<div class="layui-input-inline">
				<input name="url" class="layui-input" id="url_udpate2">
			</div>
		</div>
	</form>
</div>