package test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.github.pagehelper.Page;
import test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.bean.QrtzJobDetails;
import test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.service.QrtzJobDetailsService;

//@CrossOrigin(origins={"http://localhost:8080"}, methods={RequestMethod.GET, RequestMethod.POST})
@CrossOrigin(origins={"*"}, methods={RequestMethod.GET, RequestMethod.POST})
@Api(value = "/qrtzJobDetails", tags = "定时任务操作接口")
@Controller
@RequestMapping("/qrtzJobDetails")
public class QrtzJobDetailsController {
	//private static final Logger logger = LoggerFactory.getLogger(QrtzJobDetailsController.class);
	
	@Autowired
	private QrtzJobDetailsService qrtzJobDetailsService;
	

	@ApiOperation(value = "查询定时任务", notes = "根据id获取用户信息", httpMethod = "POST", response = QrtzJobDetails.class)
	@RequestMapping("/listByPage")
	@ResponseBody
	public  Map<String, Object> listByPage(String filter, QrtzJobDetails qrtzJobDetails, Page<Map<String, Object>> page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		//设置默认排序属性
		//page.setDefaultSort("createTime", "desc");
		page = this.qrtzJobDetailsService.findMapListByPage(qrtzJobDetails, page);
		map.put("studentdata", page);
	    map.put("number", page.getTotal());
		return map;
	}
	
	@ApiOperation(value = "添加定时任务", notes = "动态添加定时任务", httpMethod = "POST", response = QrtzJobDetails.class)
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addQrtzJobDetails(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();

		map = this.qrtzJobDetailsService.createQrtzJobDetails(qrtzJobDetails);
		map.put("success", true);
		map.put("msg", "定时任务添加成功");
		return map;
	}
	
	@ApiOperation(value = "修改定时任务", notes = "动态修改定时任务", httpMethod = "POST", response = QrtzJobDetails.class)
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> updateQrtzJobDetails(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = this.qrtzJobDetailsService.updateQrtzJobDetails(qrtzJobDetails);
		return map;
	}
	
	@ApiOperation(value = "删除定时任务", notes = "动态删除定时任务,先暂停再删除", httpMethod = "POST", response = QrtzJobDetails.class)
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> deleteQrtzJobDetails(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map = this.qrtzJobDetailsService.deleteQrtzJobDetails(qrtzJobDetails);
		return map;
	}
	
	@ApiOperation(value = "暂停定时任务", notes = "暂停定时任务", httpMethod = "POST", response = QrtzJobDetails.class)
	@RequestMapping("/pause")
	@ResponseBody
	public Map<String, Object> pauseJob(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map = this.qrtzJobDetailsService.pauseJob(qrtzJobDetails);
		return map;
	}
	
	@ApiOperation(value = "恢复定时任务", notes = "恢复暂停的定时任务", httpMethod = "POST", response = QrtzJobDetails.class)
	@RequestMapping("/resume")
	@ResponseBody
	public Map<String, Object> resumeJob(QrtzJobDetails qrtzJobDetails, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map = this.qrtzJobDetailsService.resumeJob(qrtzJobDetails);
		return map;
	}
	
}
