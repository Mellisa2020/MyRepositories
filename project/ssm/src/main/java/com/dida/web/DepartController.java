package com.dida.web;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Depart;
import com.dida.bean.Emp;
import com.dida.service.DepartService;
import com.dida.service.EmpService;
import com.dida.utils.PageHelper;
import com.mysql.fabric.Response;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2018-11-12
 */
@Controller
@RequestMapping("/depart")
public class DepartController{

	@Autowired
	DepartService ds;
	
	@Autowired
	EmpService es;
	
	@RequestMapping("/list/{pageIndex}")
	public String list(@PathVariable(name="pageIndex")Integer pageIndex,@RequestParam(defaultValue="5")Integer pageSize,HttpServletRequest request,HttpServletResponse response){
		Page<Depart> page = new Page<Depart>(pageIndex,pageSize);
		Page<Depart> result = ds.selectPage(page);
		
		Integer totalCount = ((Long)(result.getTotal())).intValue();
		
		List<Depart> departs = result.getRecords();
		
		PageHelper<Depart> pager = new PageHelper<Depart>(pageIndex, pageSize, totalCount, departs, null);
		
		request.setAttribute("pageBean", pager);
		request.setAttribute("hasNext", result.hasNext());
		request.setAttribute("hasPrevious", result.hasPrevious());
		
		return "/departlist.jsp";
	}
	
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable(name="id")Integer id,HttpServletRequest request,HttpServletResponse response){
		Depart depart = ds.selectById(id);
		request.setAttribute("depart", depart);
		
		return "/departupdate.jsp";
	}
	
	@RequestMapping("/updateInfo/{id}")
	public void updateInfo(Depart depart,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Boolean flag = ds.updateById(depart);
		if(flag==true){
			response.getWriter().write("<script>alert('更改成功！');location.href='"+request.getContextPath()+"/depart/list/1';</script>");
			
		}else {
			response.getWriter().write("<script>alert('更改失败！');location.href='"+request.getContextPath()+"/depart/list/1';</script>");
		}
	}
	
	@RequestMapping("/delete/{id}")
	public void delete(@PathVariable(name="id")Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Emp> emps = es.selectList(new EntityWrapper<Emp>().eq("did", id));
		if(emps.size()==0){
			ds.deleteById(id);
			response.getWriter().write("<script>alert('删除成功！');location.href='"+request.getContextPath()+"/depart/list/1';</script>");
		}else {
			response.getWriter().write("<script>alert('部门还有员工，删除失败！');location.href='"+request.getContextPath()+"/depart/list/1';</script>");
		}
	}
	@RequestMapping("/add")
	public String add(){
		return "/departadd.jsp";
	}
	
	@RequestMapping("/addInfo")
	public void addInfo(Depart depart,HttpServletRequest request,HttpServletResponse response) throws IOException{
		depart.setDel(0);
		depart.setFlag(1);
		Boolean flag = ds.insert(depart);
		System.out.println(depart.toString());
		if(flag==true){
			response.getWriter().write("<script>alert('新增成功！');location.href='"+request.getContextPath()+"/depart/list/1';</script>");
		}else {
			response.getWriter().write("<script>alert('新增失败！');location.href='"+request.getContextPath()+"/depart/list/1';</script>");
		}
	}
}

