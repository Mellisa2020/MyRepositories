package com.dida.web;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dida.utils.CreateFileUtils;

/*
 * 流程管理相关
 * 
 * */
@Controller
@Scope(scopeName="prototype")
@RequestMapping("/process")
public class ProcessController {

	@Autowired
	ProcessEngine pe;
	/*
	 * 01-部署流程图
	 */
	@RequestMapping("/deploy")
	public void deploy(@RequestParam("mFile") MultipartFile file,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String excelPath = "";
		File createDir = CreateFileUtils.createDirModel(request.getSession()
				.getServletContext(), "process");
		if (!file.isEmpty()) {
			String filename = file.getOriginalFilename();
			String createName = CreateFileUtils.createName(filename);
			File f = new File(createDir, createName);
			excelPath = "media/process/"
					+ new SimpleDateFormat("yyyy-MM-dd").format(Calendar
							.getInstance().getTime()) + "/" + createName;
			file.transferTo(f);
		}
		String realPath = request.getSession().getServletContext()
				.getRealPath(excelPath);// 获取服务器的路径
		System.out.println("文件上传成功:" + realPath);
		
		// 部署流程
		// 方式二：读取zip压缩文件
		DeploymentBuilder deploymentBuilder = pe.getRepositoryService().createDeployment();
		FileInputStream fis = new FileInputStream(realPath);
		ZipInputStream zipInputStream = new ZipInputStream(fis);
		deploymentBuilder.addZipInputStream(zipInputStream);
		String name = "请假流程";
		deploymentBuilder.name(name);
		Deployment deployment = deploymentBuilder.deploy();
		System.out.println("部署成功,ID:" + deployment.getId());
	}
	
	/*
	 * 02-查询最新的流程定义
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		ProcessDefinitionQuery query = pe.getRepositoryService().createProcessDefinitionQuery();
		query.orderByProcessDefinitionVersion().asc();
		List<ProcessDefinition> list = query.list();
		model.addAttribute("list", list);
		return "/flow_ProcessDefinitionList.jsp";
	}
	
}
