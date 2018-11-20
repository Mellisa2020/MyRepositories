package com.dida.service;

import com.dida.bean.Permission;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-11-14
 */
public interface PermissionService extends IService<Permission> {
	public List<Permission> getMyMenus(int id); 
}
