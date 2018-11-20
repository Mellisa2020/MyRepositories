package com.dida.test;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.plugins.Page;
import com.dida.bean.Grade;
import com.dida.bean.SysUser;
import com.dida.mapper.GradeMapper;
import com.dida.mapper.SysUserMapper;
import com.dida.service.GradeService;
import com.dida.service.SysUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test01 {
	private final static Logger LOG = LogManager.getLogger(Test01.class);
	
	@Autowired
	GradeService gs;
	
	@Test
	public void pageShow(){
		Page<Grade> page = new Page<Grade>(2,3);
		Page<Grade> grades = gs.selectPage(page);
		System.out.println(page.getTotal());
		List<Grade> grade = page.getRecords();
		for (Grade g : grade) {
			System.out.println(g.toString());
		}
	}
	
	@Test
	public void test1() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProcessEngine pe = (ProcessEngine) ctx.getBean("pe");
		System.out.println(pe);
	}

}
