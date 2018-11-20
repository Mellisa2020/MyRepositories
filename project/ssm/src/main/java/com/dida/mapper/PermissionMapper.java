package com.dida.mapper;

import com.dida.bean.Permission;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-11-14
 */
public interface PermissionMapper extends BaseMapper<Permission> {
	public List<Permission> getMyMenus(int id); 
}
