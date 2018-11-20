package com.dida.service.impl;

import com.dida.bean.Permission;
import com.dida.bean.Role;
import com.dida.bean.RolePermission;
import com.dida.mapper.RoleMapper;
import com.dida.mapper.RolePermissionMapper;
import com.dida.service.RoleService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2018-11-16
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
	
	@Autowired
	RoleMapper rm;
	
	@Autowired
	RolePermissionMapper rpm;
	
	
	//得到一个角色对象  一个菜单数组  一个权限数组
	public boolean add(Role r, Integer[] menus_ids, Integer[] permission_ids) {
		Integer count = rm.insert(r);
		if(count>0) {
			for(int i = 0;i<menus_ids.length;i++) {
				RolePermission rp = new RolePermission();
				rp.setSysRoleId(r.getId());
				rp.setSysPermissionId(menus_ids[i]);
				rpm.insert(rp);
			}
			for(int i = 0;i<permission_ids.length;i++) {
				RolePermission rp = new RolePermission();
				rp.setSysRoleId(r.getId());
				rp.setSysPermissionId(permission_ids[i]);
				rpm.insert(rp);
			}
			return true;
		}
		return false;
	}

	public boolean update(Role r, Integer[] menus_ids, Integer[] permission_ids) {
		Integer count =  rm.updateById(r);
		if(count>0) {
			rpm.delete(new EntityWrapper<RolePermission>().eq("sys_role_id", r.getId()));
			for(int i = 0;i<menus_ids.length;i++) {
				RolePermission rp = new RolePermission();
				rp.setSysRoleId(r.getId());
				rp.setSysPermissionId(menus_ids[i]);
				rpm.insert(rp);
			}
			for(int i = 0;i<permission_ids.length;i++) {
				RolePermission rp = new RolePermission();
				rp.setSysRoleId(r.getId());
				rp.setSysPermissionId(permission_ids[i]);
				rpm.insert(rp);
			}
			return true;
		}
		return false;
	}

	public boolean delete(Integer id) {
		Integer count1 = rpm.delete(new EntityWrapper<RolePermission>().eq("sys_role_id", id));
		if(count1>0) {
			Integer count2 = rm.delete(new EntityWrapper<Role>().eq("id", id));
			if(count2>0) {
				return true;
			}
		}
		return false;
	}
	
}
