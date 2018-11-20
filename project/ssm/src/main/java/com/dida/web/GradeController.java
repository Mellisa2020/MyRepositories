package com.dida.web;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Course;
import com.dida.bean.Grade;
import com.dida.bean.Student;
import com.dida.bean.SysUser;
import com.dida.service.CourseService;
import com.dida.service.GradeService;
import com.dida.service.StudentService;
import com.dida.utils.PageHelper;

import java.io.IOException;
import java.util.List;

import javax.print.attribute.standard.Media;
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
 * @since 2018-11-07
 */
@Controller
@RequestMapping("/grade")
public class GradeController {
	
	@Autowired
	GradeService gs;
	
	@Autowired
	CourseService cs;
	
	@Autowired
	StudentService ss;
	
	@RequestMapping(value="/list/{pageIndex}")
	public String list(@PathVariable(name = "pageIndex") Integer pageIndex,@RequestParam(defaultValue="5") Integer pageSize, Model model){
		Page<Grade> page = new Page<Grade>(pageIndex,pageSize);  //封装条件
		Page<Grade> results = gs.selectPage(page); //查询的数据结果集对象
		
		int totalCount= ((Long)results.getTotal()).intValue();  //总条数
		List<Grade> grades = results.getRecords(); //查询到的每页数据
		for (Grade grade : grades) {
			Course course = cs.selectById( grade.getCid());
			grade.setCourse(course);
		}
		
		//封装工具类
		PageHelper<Grade> pager=new PageHelper<Grade>(pageIndex, pageSize, totalCount, grades, null);
		model.addAttribute("hasPrevious", results.hasPrevious()); //true 是否有上一页
		model.addAttribute("hasNext", results.hasNext());
		model.addAttribute("pageBean", pager);
		
		return "/gradelist.jsp"; //转发
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable(name="id") Integer id,Model model){
		System.out.println(id);
		System.out.println(gs.selectById(id).toString());
		Grade grade = gs.selectById(id);
		List<Course> courses = cs.selectList(new EntityWrapper<Course>());
		model.addAttribute("grade",grade);
		model.addAttribute("courses",courses);
		return "/gradeupdate.jsp";
	}
	
	@RequestMapping("/updateInfo/{id}")
	public String updateInfo(Grade g,@PathVariable(name="id") Integer id,Model model,HttpServletResponse response,HttpServletRequest request) throws IOException{
		System.out.println("要修改的班级id:"+id);
		g.setFlag(1);
		g.setDel(0);
		System.out.println(g.toString());
		boolean flag = gs.updateById(g);
		if(flag==true){
			response.getWriter().write("<script>alert('修改成功!');location.href='"+request.getContextPath()+"/grade/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('修改失败!');location.href='"+request.getContextPath()+"/grade/list/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		List<Course> courses = cs.selectList(new EntityWrapper<Course>());
		model.addAttribute("courses",courses);
		return "/gradeadd.jsp";
	}
	
	@RequestMapping("/addGrade")
	public String addGrade(Model model,Grade grade,HttpServletResponse response,HttpServletRequest request) throws IOException{
		grade.setFlag(1);
		grade.setDel(0);
		boolean flag = gs.insert(grade);
		if(flag==true){
			response.getWriter().write("<script>alert('增加成功!');location.href='"+request.getContextPath()+"/grade/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('增加失败!');location.href='"+request.getContextPath()+"/grade/list/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable(name="id")Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		List<Student> students = ss.selectList(new EntityWrapper<Student>().eq("gid", id));
		
		if(students.size()==0){
			gs.deleteById(id);
			response.getWriter().write("<script>alert('删除成功！');location.href='"+request.getContextPath()+"/grade/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('该班级还有学生，删除失败！');location.href='"+request.getContextPath()+"/grade/list/1';</script>");
		}
		return null;
	}
	
}

