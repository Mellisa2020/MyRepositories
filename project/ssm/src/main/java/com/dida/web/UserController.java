package com.dida.web;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Role;
import com.dida.bean.Student;
import com.dida.bean.SysUser;
import com.dida.bean.UserRole;
import com.dida.service.RoleService;
import com.dida.service.SysUserService;
import com.dida.service.UserRoleService;
import com.dida.utils.PageHelper;

@Controller
@Scope(value="prototype")
@RequestMapping("/userRole")
public class UserController {
	@Autowired
	SysUserService ss;
	
	@Autowired
	UserRoleService urs;
	
	@Autowired
	RoleService rs;
	
	@RequestMapping("/list/{pageIndex}")
	public String list(String username,String locked,@PathVariable(name="pageIndex")Integer pageIndex,@RequestParam(defaultValue="5")Integer pageSize,Model model) {
		//获取分页参数
		Page<SysUser> page = new Page<SysUser>(pageIndex,pageSize);
		if(username!=null) {
			if(username.trim().equals("")) {
				username=null;
			}
		}
		
		if(locked!=null) {
			if(locked.trim().equals("")) {
				locked=null;
			}
		}
		
		//获取分页结果
			Page<SysUser> results = null;
			
			System.out.println("locked:"+locked);
			System.out.println("username:"+username);
			//按照条件查询结果分页
			if(username==null&&locked==null) {
				results = ss.selectPage(page);
			}else if(username==null && locked!=null) {
				results = ss.selectPage(page, new EntityWrapper<SysUser>().eq("locked", locked));
			}else if(username!=null && locked==null) {
				results = ss.selectPage(page, new EntityWrapper<SysUser>().like("username", username));
			}else {
				results = ss.selectPage(page, new EntityWrapper<SysUser>().like("username", username).eq("locked", locked));
			}
		
		//获取总条数
		Integer totalCount = ((Long)(results.getTotal())).intValue();
		
		//获取分页结果集
		List<SysUser> list = results.getRecords();
		//为用户设置角色列表属性
		for (SysUser sysUser : list) {
			List<UserRole> ur = urs.selectList(new EntityWrapper<UserRole>().eq("sys_user_id",sysUser.getId()));
			List<Role> roles = new ArrayList<Role>();
			for (UserRole userrole : ur) {
				roles.add(rs.selectById(userrole.getSysRoleId()));
			}
			sysUser.setRoles(roles);
		}
		
		//分页工具获取pageBean
		PageHelper<SysUser> pager = new PageHelper<SysUser>(pageIndex,pageSize,totalCount,list,null);
		
		
		model.addAttribute("pageBean", pager);
		model.addAttribute("hasNext", results.hasNext());
		model.addAttribute("hasPrevious", results.hasPrevious());
		model.addAttribute("username", username);
		model.addAttribute("locked",locked);
		
		return "/userlist.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/update/{id}")
	public Map<String, Object> update(@PathVariable(name="id")Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = ss.selectOne(new EntityWrapper<SysUser>().eq("id", id));
		List<Role> roles = rs.selectList(new EntityWrapper<Role>());
		for (Role role : roles) {
			int count = urs.selectCount(new EntityWrapper<UserRole>().eq("sys_user_id", id).eq("sys_role_id", role.getId()));
			if(count>0) {
				role.setFlag(1);
			}else {
				role.setFlag(0);
			}
		}
		
		map.put("user", user);
		map.put("roles", roles);
		
		return map;
	}
	
	@RequestMapping("/updateUserRole")
	public Boolean updateUserRole(SysUser u,Integer role_ids[]) {
		System.out.println(u);
		System.out.println(role_ids.toString());
		return urs.update(u, role_ids);
	}
	
}
