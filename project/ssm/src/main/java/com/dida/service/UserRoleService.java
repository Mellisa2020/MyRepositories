package com.dida.service;

import com.dida.bean.SysUser;
import com.dida.bean.UserRole;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2018-11-18
 */
public interface UserRoleService extends IService<UserRole> {
	public boolean update(SysUser user, Integer[] rids);
}
