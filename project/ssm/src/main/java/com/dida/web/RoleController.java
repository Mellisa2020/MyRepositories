package com.dida.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Permission;
import com.dida.bean.Role;
import com.dida.bean.RolePermission;
import com.dida.mapper.RoleMapper;
import com.dida.service.PermissionService;
import com.dida.service.RolePermissionService;
import com.dida.service.RoleService;
import com.dida.utils.PageHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2018-11-16
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	RoleService rs;

	@Autowired
	PermissionService ps;
	
	@Autowired
	RolePermissionService rps;
	

	@RequestMapping("/list/{pageIndex}")
	public String list(@PathVariable(name = "pageIndex") Integer pageIndex,
			@RequestParam(defaultValue = "5") Integer pageSize, Model model) {
		Page<Role> page = new Page<Role>(pageIndex, pageSize);
		Page<Role> result = rs.selectPage(page, new EntityWrapper<Role>().eq("available", "1"));

		Integer totalCount = ((Long) (result.getTotal())).intValue();
		List<Role> roles = result.getRecords();
		PageHelper<Role> pager = new PageHelper<Role>(pageIndex, pageSize, totalCount, roles, null);

		model.addAttribute("pageBean", pager);
		model.addAttribute("hasNext", result.hasNext());
		model.addAttribute("hasPrevious", result.hasPrevious());

		return "/rolelist.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/addrole")
	public Map<String, Object> addRole() {
		// 获取所有菜单
		List<Permission> menus = ps.selectList(new EntityWrapper<Permission>().eq("type", "menu"));
		// 获取所有权限
		List<Permission> permissions = ps.selectList(new EntityWrapper<Permission>().eq("type", "permission"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menus",menus);
		map.put("permissions", permissions);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Boolean add(Role role,Integer[] menus_ids, Integer[] permission_ids) {
		role.setAvailable("1");
		return rs.add(role, menus_ids, permission_ids);
	}
	
	@ResponseBody
	@RequestMapping("/updaterole/{id}")
	public Map<String, Object> updateRole(@PathVariable(name="id")Integer id) {
		//获取角色
		Role r = rs.selectById(id);
		
		rps.selectList(new EntityWrapper<RolePermission>().eq("sys_role_id", r.getId()));
		
		
		
		// 获取所有菜单
		List<Permission> menus = ps.selectList(new EntityWrapper<Permission>().eq("type", "menu"));
		for (Permission menu : menus) {
			Integer menuId = menu.getId();
			int count = rps.selectCount(new EntityWrapper<RolePermission>().eq("sys_role_id", id).eq("sys_permission_id",menuId ));
			if(count>0) {
				menu.setFlag(1);
			}
		}
		
		// 获取所有权限
		List<Permission> permissions = ps.selectList(new EntityWrapper<Permission>().eq("type", "permission"));
		for (Permission permission : permissions) {
			Integer permissionId = permission.getId();
			int count = rps.selectCount(new EntityWrapper<RolePermission>().eq("sys_role_id", id).eq("sys_permission_id",permissionId ));
			if(count>0) {
				permission.setFlag(1);
			}
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("role", r);
		map.put("menus",menus);
		map.put("permissions", permissions);
		return map;
	}
	

	@ResponseBody
	@RequestMapping("/update")
	public Boolean update(Role r,Integer[] menus_ids, Integer[] permission_ids) {
		r.setAvailable("1");
		return rs.update(r, menus_ids, permission_ids);
	}
	
	@ResponseBody
	@RequestMapping("/delete/{id}")
	public Boolean delete(@PathVariable(name="id")Integer id) {
		System.out.println("要删除的角色："+id);
		return rs.delete(id);
	}
	
	
}
