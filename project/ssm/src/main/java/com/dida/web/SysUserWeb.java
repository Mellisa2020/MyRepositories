package com.dida.web;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dida.bean.Permission;
import com.dida.bean.SysUser;
import com.dida.service.PermissionService;
import com.dida.service.SysUserService;
import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;

import net.sf.ehcache.Statistics;

@Controller
@Scope(value="prototype")
@RequestMapping("/user")
public class SysUserWeb {
	@Autowired
	SysUserService us;
	
	@Autowired
	PermissionService ps;
	
	@RequestMapping("/login")
	public void login(Model model,SysUser u,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		SysUser user = us.selectOne(new EntityWrapper<SysUser>().eq("username", u.getUsername()).eq("password", u.getPassword()));
		if(user!=null){
			model.addAttribute("user",user);
			session.setAttribute("user", user);
			//获取user的菜单
			List<Permission> permissions = ps.getMyMenus(user.getId());
			
			session.setAttribute("permissions",permissions);
			response.getWriter().write("<script>location.href='"+request.getContextPath()+"/index.jsp';</script>");
		
		}else{
			response.getWriter().write("<script>alert('账号或密码错误!);location.href='"+request.getContextPath()+"/login.jsp';</script>");
		}
	}
	
	@RequestMapping("/userInfo/{id}")
	public String userInfo(@PathVariable(name="id")Integer id,Model model,HttpSession session){
		SysUser user = (SysUser) session.getAttribute("user");
		SysUser userInfo = user;
		if(userInfo.getLocked().equals("0")){
			userInfo.setLocked("启用");
		}else{
			userInfo.setLocked("禁用");
		}
		
		if(userInfo.getPhoto()==null||userInfo.getPhoto().equals("")){
			userInfo.setPhoto("init.jpg");
		}
		model.addAttribute("user",userInfo);
		
		return "/user.jsp";
	}
	
	@RequestMapping("/logout")
	public void logout(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
		session.invalidate();
		response.getWriter().write("<script>window.parent.frames.location.href='"+request.getContextPath()+"/login.jsp';</script>");
	}
	
	@RequestMapping("/photo/{id}")
	public String photo(@PathVariable(name="id")String id,HttpSession session){
		SysUser user = (SysUser) session.getAttribute("user");
		if(user.getPhoto()==null||user.getPhoto().equals("")){
			user.setPhoto("init.jpg");
		}
		return "/photo.jsp";
	}
	
	@RequestMapping("/photoImg/{id}")
	public void photoImg(@RequestParam(name="mFile")String mFile,@PathVariable(name="id")String id,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SysUser user = (SysUser) session.getAttribute("user");
		System.out.println("头像路径："+mFile);
		user.setPhoto(mFile);
		us.updateById(user);
		response.getWriter().write("<script>alert('头像修改成功！');location.href='"+request.getContextPath()+"/user/photo/"+user.getId()+"';</script>");
	}
	
	@RequestMapping("/changePwd/{id}")
	public String changePwd(){
		
		return "/password.jsp";
	}
	
	@RequestMapping("/change/{id}")
	public void change(HttpSession session,String oldpwd,String newpwd,String repwd,HttpServletRequest request,HttpServletResponse response) throws IOException{
		SysUser user = (SysUser) session.getAttribute("user");
		if(user.getPassword().equals(oldpwd)){
			if(newpwd.equals(repwd)){
				user.setPassword(newpwd);
				us.updateById(user);
				response.getWriter().write("<script>alert('修改成功！');location.href='"+request.getContextPath()+"/user/changePwd/"+user.getId()+"';</script>");
			}else{
				response.getWriter().write("<script>alert('原密码输入错误！');location.href='"+request.getContextPath()+"/user/changePwd/"+user.getId()+"';</script>");
			}
		}else{
			response.getWriter().write("<script>alert('原密码输入错误！');location.href='"+request.getContextPath()+"/user/changePwd/"+user.getId()+"';</script>");
		}
	}
}

