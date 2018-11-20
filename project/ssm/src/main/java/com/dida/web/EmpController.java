package com.dida.web;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Course;
import com.dida.bean.Depart;
import com.dida.bean.Emp;
import com.dida.bean.Grade;
import com.dida.bean.Student;
import com.dida.service.DepartService;
import com.dida.service.EmpService;
import com.dida.utils.PageHelper;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2018-11-12
 */
@Controller
@RequestMapping("/emp")
public class EmpController {
	
	@Autowired
	EmpService es;
	
	@Autowired
	DepartService ds;
	
	@RequestMapping(value="/list/{pageIndex}")
	public String list(@PathVariable(name = "pageIndex") Integer pageIndex,@RequestParam(defaultValue="5") Integer pageSize, Model model){
		Page<Emp> page = new Page<Emp>(pageIndex,pageSize);  //封装条件
		Page<Emp> results = es.selectPage(page); //查询的数据结果集对象
		
		int totalCount= ((Long)results.getTotal()).intValue();  //总条数
		List<Emp> emps = results.getRecords(); //查询到的每页数据
		for (Emp emp : emps) {
			Depart depart = ds.selectById(emp.getDid());
			emp.setDepart(depart);
		}
		
		//封装工具类
		PageHelper<Emp> pager=new PageHelper<Emp>(pageIndex, pageSize, totalCount, emps, null);
		model.addAttribute("hasPrevious", results.hasPrevious()); //true 是否有上一页
		model.addAttribute("hasNext", results.hasNext());
		model.addAttribute("pageBean", pager);
		
		return "/emplist.jsp"; //转发
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable(name="id") Integer id,Model model){
		Emp emp = es.selectById(id);
		List<Depart> departs = ds.selectList(new EntityWrapper<Depart>());
		model.addAttribute("emp",emp);
		model.addAttribute("departs",departs);
		return "/empupdate.jsp";
	}
	
	@RequestMapping("/updateInfo/{id}")
	public String updateInfo(Emp emp,@PathVariable(name="id") Integer id,Model model,HttpServletResponse response,HttpServletRequest request) throws IOException{
		emp.setFlag(1);
		emp.setDel(0);
		boolean flag = es.updateById(emp);
		if(flag==true){
			response.getWriter().write("<script>alert('修改成功!');location.href='"+request.getContextPath()+"/emp/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('修改失败!');location.href='"+request.getContextPath()+"/emp/list/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		List<Depart> departs = ds.selectList(new EntityWrapper<Depart>());
		model.addAttribute("departs",departs);
		return "/empadd.jsp";
	}
	
	@RequestMapping("/addEmp")
	public String addEmp(Model model,Emp emp,HttpServletResponse response,HttpServletRequest request) throws IOException{
		emp.setFlag(1);
		emp.setDel(0);
		boolean flag = es.insert(emp);
		if(flag==true){
			response.getWriter().write("<script>alert('增加成功!');location.href='"+request.getContextPath()+"/emp/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('增加失败!');location.href='"+request.getContextPath()+"/emp/list/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable(name="id")Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		Boolean flag = es.deleteById(id);
		
		if(flag==true){
			response.getWriter().write("<script>alert('删除成功！');location.href='"+request.getContextPath()+"/emp/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('删除失败！');location.href='"+request.getContextPath()+"/emp/list/1';</script>");
		}
		return null;
	}
}

