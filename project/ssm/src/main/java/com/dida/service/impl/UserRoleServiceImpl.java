package com.dida.service.impl;

import com.dida.bean.SysUser;
import com.dida.bean.UserRole;
import com.dida.mapper.SysUserMapper;
import com.dida.mapper.UserRoleMapper;
import com.dida.service.UserRoleService;
import com.dida.web.SysUserWeb;
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
 * @since 2018-11-18
 */
@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	@Autowired
	SysUserMapper sm;
	
	@Autowired
	UserRoleMapper urm;
	
	public boolean update(SysUser user, Integer[] rids) {
		Integer updateById = sm.updateById(user);
		if(updateById>0) {
			urm.delete(new EntityWrapper<UserRole>().eq("sys_user_id", user.getId()));
			for (Integer r : rids) {
				UserRole ur = new UserRole();
				ur.setSysUserId(user.getId());
				ur.setSysRoleId(r);
				urm.insert(ur);
			}
			return true;
		}
		return false;
	}

}
