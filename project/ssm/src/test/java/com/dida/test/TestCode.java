/**
 * 
 */
package com.dida.test;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/*
 * 自动生成 mapper service controller  bean
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestCode {

	@Autowired
	DataSource ds;

	@Test
	public void testGenerator() {
		// 1. 全局配置
		GlobalConfig config = new GlobalConfig();
		config.setActiveRecord(false) // 是否支持AR模式  活动记录
				.setAuthor("") // 作者
				.setOutputDir("\\Users\\chenjingyun\\project\\ssm\\src\\main\\java") // 生成路径
				.setFileOverride(true) // 文件覆盖
				.setIdType(IdType.AUTO) // 主键策略
				.setServiceName("%sService") // Service 命名   XXXService
				.setBaseResultMap(true)
				.setBaseColumnList(true);
		
		//2. 数据源配置
	    DataSourceConfig  dsConfig  = new DataSourceConfig();
		dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
						.setDriverName("com.mysql.jdbc.Driver")
						.setUrl("jdbc:mysql://127.0.0.1:3306/didadb?useUnicode=true&characterEncoding=utf8")
						.setUsername("root")
						.setPassword("123456");
		
		//3. 策略配置
		StrategyConfig stConfig = new StrategyConfig();
				stConfig.setCapitalMode(true) //全局大写命名
								.setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
								.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
								.setTablePrefix("t_")
								.setInclude("t_user_role");  // 生成的表  要逆向生成的表是？
				
				
				//4. 包名策略配置 
				PackageConfig pkConfig = new PackageConfig();
				pkConfig.setParent("com.dida")
								.setMapper("mapper")
								.setService("service")
								.setController("web")
								.setEntity("bean")
								.setXml("mapper");
				
				//5. 整合配置
				AutoGenerator  ag = new AutoGenerator();
				ag.setGlobalConfig(config)
						  .setDataSource(dsConfig)
						  .setStrategy(stConfig)
						  .setPackageInfo(pkConfig);
				//6. 执行
				ag.execute();

	}
}
