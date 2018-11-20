package com.dida.service.impl;
import java.util.List;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dida.bean.Permission;
import com.dida.mapper.PermissionMapper;
import com.dida.service.PermissionService;

public class MenusServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	public List<Permission> getMyMenus(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
