package com.dida.web;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Course;
import com.dida.bean.Grade;
import com.dida.bean.Student;
import com.dida.service.GradeService;
import com.dida.service.StudentService;
import com.dida.utils.CreateFileUtils;
import com.dida.utils.ExcelUtils;
import com.dida.utils.PageHelper;
import com.mysql.fabric.Response;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityListeners;
import javax.print.attribute.standard.PageRanges;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2018-11-09
 */
@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService ss;
	
	@Autowired
	GradeService gs;
	
	@RequestMapping("/listSearch/{pageIndex}")
	public String listSearch(@PathVariable(name="pageIndex")Integer pageIndex,String searchName,Integer searchgid,@RequestParam(defaultValue="5")Integer pageSize,Model model){
		//设置分页条件
		Page<Student> page = new Page<Student>(pageIndex,pageSize);
		Page<Student> results =null;
		
		//按照条件查询结果分页
		if(searchName!=null&&searchgid!=null){
			results = ss.selectPage(page, new EntityWrapper<Student>().like("name", searchName).eq("gid", searchgid));
		}else if(searchName==null&&searchgid!=null){
			results = ss.selectPage(page, new EntityWrapper<Student>().eq("gid", searchgid));
			
		}else{
			results = ss.selectPage(page, new EntityWrapper<Student>().like("name", searchName));
		}
		
		//总条数
		int totalCount = ((Long)( results.getTotal())).intValue();
		
		//获取每页的结果集
		List<Student> searchStudents = results.getRecords();
		for (Student student : searchStudents) {
			Grade grade = gs.selectById(student.getGid());
			student.setGrade(grade);
		}
		
		//查询班级下拉框获取所有班级
		List<Grade> grades = gs.selectList(new EntityWrapper<Grade>());
		model.addAttribute("grades",grades);
		
		//获取pageBean
		PageHelper<Student> pager = new PageHelper<Student>(pageIndex, pageSize,totalCount,searchStudents, null);
		
		
		model.addAttribute("hasPrevious", results.hasPrevious()); //true 是否有上一页
		model.addAttribute("hasNext", results.hasNext());
		model.addAttribute("pageBean", pager);
		
		//查询跳转后查询信息回显
		model.addAttribute("sstuname", searchName);
		model.addAttribute("sgid", searchgid);
		
		return "/studentlist.jsp";
		
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable(name="id") Integer id,Model model){
		System.out.println(id);
		System.out.println(gs.selectById(id).toString());
		Student student = ss.selectById(id);
		List<Grade> grades = gs.selectList(new EntityWrapper<Grade>());
		model.addAttribute("grades",grades);
		model.addAttribute("student",student);
		return "/studentupdate.jsp";
	}
	
	@RequestMapping("/updateInfo/{id}")
	public String updateInfo(Student student,@PathVariable(name="id") Integer id,Model model,HttpServletResponse response,HttpServletRequest request) throws IOException{
		System.out.println("要修改的学生id:"+id);
		System.out.println(student.toString());
		boolean flag = ss.updateById(student);
		if(flag==true){
			response.getWriter().write("<script>alert('修改成功!');location.href='"+request.getContextPath()+"/student/listSearch/1';</script>");
		}else{
			response.getWriter().write("<script>alert('修改失败!');location.href='"+request.getContextPath()+"/student/listSearch/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/add")
	public String add(Model model){
		List<Grade> grades = gs.selectList(new EntityWrapper<Grade>());
		model.addAttribute("grades",grades);
		return "/studentadd.jsp";
	}
	
	@RequestMapping("/addInfo")
	public String addInfo(Student student,HttpServletRequest request,HttpServletResponse response) throws IOException{
		student.setFlag(1);
		student.setDel(0);
		Boolean flag = ss.insert(student);
		
		
		if(flag==true){
			response.getWriter().write("<script>alert('增加成功！');location.href='"+request.getContextPath()+"/student/listSearch/1';</script>");
		}else{
			response.getWriter().write("<script>alert('增加失败！');location.href='"+request.getContextPath()+"/student/listSearch/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/details/{id}")
	public String details(Model model,@PathVariable(name="id")Integer id){
		Student student = ss.selectById(id);
		Grade grade = gs.selectById(student.getGid());
		student.setGrade(grade);
		model.addAttribute("student",student);
		return "/studentdetails.jsp";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable(name="id")Integer id,HttpServletResponse response,HttpServletRequest request) throws IOException{
		Boolean flag = ss.deleteById(id);
		if(flag==true){
			response.getWriter().write("<script>alert('删除成功！');location.href='"+request.getContextPath()+"/student/listSearch/1';</script>");
		}else{
			response.getWriter().write("<script>alert('删除失败！');location.href='"+request.getContextPath()+"/student/listSearch/1';</script>");
		}
		return null;
	}
	
	@RequestMapping("/goimport")
	public String goimport(Model model){
		List<Grade> grades = gs.selectList(new EntityWrapper<Grade>());
		model.addAttribute("grades",grades);
		return "/studentimport.jsp";
	}
	
	@RequestMapping("/importStudent")
	public void importStudent(@RequestParam(name="mFile") MultipartFile file,Integer gid,HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException{
		System.out.println("要上传的班级是："+gid);
		String excelPath = "";
		File createDir = CreateFileUtils.createDirExcel(request.getSession().getServletContext());
		System.out.println("创建的目录为："+createDir);
		if(!file.isEmpty()){
			String filename = file.getOriginalFilename();
			String createName = CreateFileUtils.createName(filename);
			File f = new File(createDir,createName);
			excelPath = "media/excel/" + new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())+"/"+createName;
			file.transferTo(f);
		}
		String realPath = request.getSession().getServletContext().getRealPath(excelPath);
		System.out.println("文件上传成功："+realPath);
		
		List<Student> students = ExcelUtils.readFromExcel(realPath);
		for (Student student : students) {
			student.setDel(0);
			student.setGid(gid);
			System.out.println(student.toString());
		}
		boolean flag = ss.insertBatch(students);
		
		if(flag==true){
			response.getWriter().write("<script>alert('导入成功！');location.href='"+request.getContextPath()+"/student/listSearch/1'</script>");
		}else {
			response.getWriter().write("<script>alert('导入失败！');location.href='"+request.getContextPath()+"/student/listSearch/1'</script>");
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/exportStudent")
	public void exportStudent(String searchName,Integer searchgid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Student> students = null;
		if(searchName!=null&&searchgid!=null){
			students = ss.selectList(new EntityWrapper<Student>().like("name", searchName).eq("gid", searchgid));
		}else if(searchName==null&&searchgid!=null){
			students = ss.selectList(new EntityWrapper<Student>().eq("gid", searchgid));
			
		}else{
			students = ss.selectList(new EntityWrapper<Student>().like("name", searchName));
		}
		
		System.out.println("searchgid:"+searchgid);
		System.out.println("searchName:"+searchName);
		System.out.println("要导出的学生");
		for (Student student : students) {
			System.out.println(student.toString());
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = wb.createSheet("学生信息表");
		
		int rowCount = 0; // 行数 默认第一行
		sheet.addMergedRegion( new CellRangeAddress(0, 0, 0, 10)  ); //合并单元格
		HSSFRow rowTitle = sheet.createRow(rowCount++);
		HSSFCell cellTile = rowTitle.createCell(0);
		
		HSSFCellStyle cellStyle = wb.createCellStyle(); 
		cellStyle.setFillForegroundColor((short) 13);// 设置背景色  
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中  
		cellTile.setCellStyle(cellStyle);
		
		cellTile.setCellValue("学生信息表");
		
		
		HSSFRow rowHead = sheet.createRow(rowCount++);  //标题行
		String[] titiles={"学号","姓名","性别","出生日期","身份证号","毕业学校","学历","邮箱","QQ","电话","入学日期"};
		for (int i = 0; i < titiles.length; i++) {
			rowHead.createCell(i).setCellValue(titiles[i]);
		}
		
		// 遍历集合对象创建行和单元格
		for (int i = 0; i < students.size(); i++) {
			
			Student student = students.get(i);// 取出Student对象
			// 创建行
			HSSFRow row = sheet.createRow(rowCount++);
			// 开始创建单元格并赋值
			HSSFCell noCell = row.createCell(0); // 创建第一个单元格
			noCell.setCellValue(student.getNo());

			HSSFCell nameCell = row.createCell(1);
			nameCell.setCellValue(student.getName());

			HSSFCell sexCell = row.createCell(2);
			sexCell.setCellValue(student.getSex());

			HSSFCell birthday = row.createCell(3);
			birthday.setCellValue(student.getBirthday());

			HSSFCell cardnoCell = row.createCell(4);
			cardnoCell.setCellValue(student.getCardno());

			HSSFCell schoolCell = row.createCell(5);
			schoolCell.setCellValue(student.getSchool());

			HSSFCell educationCell = row.createCell(6);
			educationCell.setCellValue(student.getEducation());

			HSSFCell emailCell = row.createCell(7);
			emailCell.setCellValue(student.getEmail());

			HSSFCell qqCell = row.createCell(8);
			qqCell.setCellValue(student.getQq());

			HSSFCell phoneCell = row.createCell(9);
			phoneCell.setCellValue(student.getPhone());

			HSSFCell dateCell = row.createCell(10);
			dateCell.setCellValue(student.getCreatedate());
		}

		String fileName="学生信息表"+System.currentTimeMillis()+".xls";//文件名
	
		//生成Excel并提供下载
		String userAgent=request.getHeader("User-Agent");
		if(userAgent.contains("Safari")){
			response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8")) ;
		}else{
			response.addHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"ISO-8859-1")) ;
		}
		ServletOutputStream outs = response.getOutputStream(); //输出流
		wb.write(outs);
		outs.close();//关闭流
		
		response.getWriter().write("<script>alert('导出成功！');location.href='" + request.getContextPath() + "/student/listSearch/1'</script>");
		
		
	}
	
	
	
}

