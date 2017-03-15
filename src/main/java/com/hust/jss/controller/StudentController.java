package com.hust.jss.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hust.jss.entity.Result;
import com.hust.jss.service.ResultService;
import com.hust.jss.service.StudentService;
import com.hust.jss.utils.Config;
import com.hust.jss.utils.UploadUtils;

@Controller
public class StudentController {
	
	@Autowired
	private ResultService resultService;
	
	//学生上交作业
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest request,
			@RequestParam(value="taskid") int taskid,
			@RequestParam(value = "uploadfile", required = false) MultipartFile[] uploadfile)
	{
		String currentID=(String) request.getSession().getAttribute("id");
		String road=Config.task+taskid+"/"+currentID;
		UploadUtils up = new UploadUtils();
		if(up.uploadUtils(uploadfile, road))
		{
			//获得当前用户id
			System.out.println("$$$$$"+taskid);
			//根据作业ID，和用户ID。修改result表中的submit状态
			Result result = new Result();
			result.setStuId(currentID);
			result.setTaskId(taskid);
			result.setSubmit(true);
			try {
				resultService.updateResult(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return"redirect:/joblist";  //返回作业列表
		
	}
	
	
	

}
