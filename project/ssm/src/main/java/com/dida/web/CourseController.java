package com.dida.web;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Course;
import com.dida.bean.Grade;
import com.dida.bean.Student;
import com.dida.service.CourseService;
import com.dida.service.GradeService;
import com.dida.utils.PageHelper;

import net.sf.jsqlparser.statement.insert.Insert;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;
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
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
	GradeService gs;
	
	@Autowired
	CourseService cs;
	
	@RequestMapping("/list/{pageIndex}")
	public String list(@PathVariable(name = "pageIndex") Integer pageIndex,@RequestParam(defaultValue="5")Integer pageSize,Model model){
		Page<Course> page = new Page<Course>(pageIndex,pageSize);
		Page<Course> results = cs.selectPage(page);
		
		int totalCount = ((Long)results.getTotal()).intValue();
		List<Course> courses = results.getRecords();
		
		PageHelper<Course> pager = new PageHelper<Course>(pageIndex, pageSize, totalCount, courses, null);
		model.addAttribute("hasPrevious", results.hasPrevious()); //true 是否有上一页
		model.addAttribute("hasNext", results.hasNext());
		model.addAttribute("pageBean", pager);
		
		return "/courselist.jsp";
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable(name="id") Integer id,Model model){
		Course course = cs.selectById(id);
		model.addAttribute("course",course);
		
		return "/courseupdate.jsp";
	}
	
	@RequestMapping("/updateInfo/{id}")
	public String updateInfo(Course course,@PathVariable(name="id") Integer id,Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
		course.setFlag(1);
		course.setDel(0);
		
		Boolean flag = cs.updateById(course);
		if(flag==true){
			response.getWriter().write("<script>alert('更新成功!');location.href='"+request.getContextPath()+"/course/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('更新失败!');location.href='"+request.getContextPath()+"/course/list/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		return "/courseadd.jsp";
	}
	
	@RequestMapping("/addCourse")
	public String addCourse(Model model,HttpServletRequest request,HttpServletResponse response,Course course) throws IOException{
		course.setFlag(1);
		course.setDel(0);
		
		Boolean flag = cs.insert(course);
		
		if(flag==true){
			response.getWriter().write("<script>alert('增加成功!');location.href='"+request.getContextPath()+"/course/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('增加失败!');location.href='"+request.getContextPath()+"/course/list/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable(name="id")Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		List<Grade> grades = gs.selectList(new EntityWrapper<Grade>().eq("cid", id));
		if(grades.size()==0){
			cs.deleteById(id);
			response.getWriter().write("<script>alert('删除成功！');location.href='"+request.getContextPath()+"/course/list/1';</script>");
		}else{
			response.getWriter().write("<script>alert('该课程还有班级，删除失败！');location.href='"+request.getContextPath()+"/course/list/1';</script>");
		}
		return null;
	}
}

