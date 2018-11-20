package com.dida.service.impl;

import com.dida.bean.Permission;
import com.dida.mapper.PermissionMapper;
import com.dida.service.PermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-11-14
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

	@Autowired
	PermissionMapper pm;
	
	public List<Permission> getMyMenus(int id) {
		// TODO Auto-generated method stub
		return pm.getMyMenus(id);
	}

}
