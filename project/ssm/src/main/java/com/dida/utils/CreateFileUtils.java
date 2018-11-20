package com.dida.utils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mysql.fabric.xmlrpc.base.Data;

public class CreateFileUtils {
	
	//创建照片目录
	public static File createDir(ServletContext context){
		String realPath = context.getRealPath("/media/photo");
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		File file = new File(realPath,date);
		if(!file.exists()){
			file.mkdir();
		}
		return file;
	}
	
	//创建excle目录
		public static File createDirExcel(ServletContext context){
			String realPath = context.getRealPath("/media/excel");
			String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			File file = new File(realPath,date);
			if(!file.exists()){
				file.mkdir();
			}
			return file;
		}
	
		public static File createDirModel(ServletContext context, String string) {
			String realPath = context.getRealPath("/media/"+string);
			String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			File file = new File(realPath,date);
			if(!file.exists()){
				file.mkdir();
			}
			return file;
		}
	//创建文件名--区分同名文件，在文件名前加上当前的时间
		public static String createName(String name){
			return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime());
		}

		
}
