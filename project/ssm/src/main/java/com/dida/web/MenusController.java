package com.dida.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Permission;
import com.dida.service.PermissionService;
import com.dida.utils.PageHelper;

@Controller
@RequestMapping("/menu")
public class MenusController {
	
	@Autowired
	PermissionService ps;
	
	
	@RequestMapping("/list/{pageIndex}")
	public String showMenus(@PathVariable(name="pageIndex")Integer pageIndex,@RequestParam(defaultValue="5",name="pageSize")Integer pageSize,Model model){
		Page<Permission> page = new Page<Permission>(pageIndex,pageSize);
		Page<Permission> results = ps.selectPage(page);
		
		List<Permission> menus = results.getRecords();
		
		Integer totalCount = ((Long)(results.getTotal())).intValue();
		
		PageHelper<Permission> pager = new PageHelper<Permission>(pageIndex, pageSize, totalCount, menus, null);
		
		
		model.addAttribute("pageBean", pager);
		model.addAttribute("hasPrevious", results.hasPrevious()); //true 是否有上一页
		model.addAttribute("hasNext", results.hasNext());
		
		return "/menus.jsp";
	}
	
	//获取一级菜单
	@ResponseBody
	@RequestMapping("/getOneLevel")
	public List<Permission> getOneLevel(){
		return ps.selectList(new EntityWrapper<Permission>().eq("parentid", "0").eq("available", "1"));
	}
	
	
	//ajax新增菜单
	@ResponseBody
	@RequestMapping("/add")
	public void add(Permission p,HttpServletRequest request,HttpServletResponse response) throws IOException{
		p.setAvailable("1");
		p.setType("menu");
		System.out.println("增加的菜单为"+p);
		System.out.println("增加的菜单父id为"+p.getParentid());
		boolean flag =  ps.insert(p);
		if(flag==true){
			response.getWriter().write("<script>alert('新增成功!');location.href='"+request.getContextPath()+"/menu/list/1';</script>");
		}else {
			response.getWriter().write("<script>alert('新增失败!');location.href='"+request.getContextPath()+"/menu/list/1';</script>");
		}
	}
	
	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable(name="id")Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		Permission p = ps.selectById(id);
		//如果父id为0----即为一级菜单，删除其本身的同时，删除其二级菜单
		//如果父id不为0----即为二级菜单，直接删除
		Boolean flag = false;
		if(p.getParentid()==0) {
			List<Permission> secondMenu = ps.selectList(new EntityWrapper<Permission>().eq("parentid", p.getId()));
			for (Permission permission : secondMenu) {
				ps.deleteById(permission.getId());
			}
			flag = ps.deleteById(p.getId());
		}else {
			flag = ps.deleteById(p.getId());
		}
		
		if(flag==true){
			response.getWriter().write("<script>alert('删除成功!');location.href='"+request.getContextPath()+"/menu/list/1';</script>");
		}else {
			response.getWriter().write("<script>alert('删除失败!');location.href='"+request.getContextPath()+"/menu/list/1';</script>");
		}
	}
	
	@ResponseBody
	@RequestMapping("/getById1/{id}")
	public Permission getById1(@PathVariable(name="id")Integer id) {
		return ps.selectById(id);
	}
	
	@RequestMapping("/updateOneLevelMenu")
	public Boolean updateOneLevelMenu(Permission p) {
		System.out.println("一级菜单更新表单提交："+p);
		return ps.updateById(p);
	}
	
	@ResponseBody
	@RequestMapping("/getById2/{id}")
	public Map<String, Object> getById2(@PathVariable(name="id")Integer id) {
		Permission permission = ps.selectById(id);
		List<Permission> list = ps.selectList(new EntityWrapper<Permission>().eq("parentid", 0));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("secondMenu",permission);
		map.put("fristMenus", list);
		return map;
	}
	
	@RequestMapping("/updateTwoLevelMenu")
	public Boolean updateTwoLevelMenu(Permission p) {
		System.out.println("二级菜单更新表单提交："+p);
		
		return ps.updateById(p);
	}
}
