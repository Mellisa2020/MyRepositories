package com.dida.service;

import com.dida.bean.Role;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-11-16
 */
public interface RoleService extends IService<Role> {
	public boolean add(Role r,Integer[] menus_ids,Integer[] permission_ids);

	public boolean update(Role r, Integer[] menus_ids, Integer[] permission_ids);
	
	public boolean delete(Integer id);
}
