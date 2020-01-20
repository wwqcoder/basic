package test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.service;

import java.util.List;
import java.util.Map;
import com.github.pagehelper.Page;
import test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.bean.QrtzJobDetails;


/**
 * 
 * @author linrx1
 *
 */
public interface QrtzJobDetailsService {
	//增删改
	Map<String, Object> createQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception;
	Map<String, Object> createQrtzJobDetails(List<QrtzJobDetails> qrtzJobDetailsList) throws Exception;
	
	Map<String, Object> updateQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception;
	Map<String, Object> updateQrtzJobDetails(List<QrtzJobDetails> qrtzJobDetailsList) throws Exception;
	
	Map<String, Object> deleteQrtzJobDetails(QrtzJobDetails qrtzJobDetails) throws Exception;
	Map<String, Object> deleteQrtzJobDetails(List<QrtzJobDetails> qrtzJobDetailsList) throws Exception;
	
	//查询
	QrtzJobDetails findQrtzJobDetailsByPrimaryKey(String id);
	Page<QrtzJobDetails> findListByPage(QrtzJobDetails qrtzJobDetails, Page<QrtzJobDetails> page);
	Page<Map<String, Object>> findMapListByPage(QrtzJobDetails qrtzJobDetails, Page<Map<String, Object>> page);
	List<Map<String, Object>> findMapList(QrtzJobDetails qrtzJobDetails);
	List<QrtzJobDetails> findList(QrtzJobDetails qrtzJobDetails);
	
	Map<String, Object> pauseJob(QrtzJobDetails qrtzJobDetails) throws Exception;
	Map<String, Object> resumeJob(QrtzJobDetails qrtzJobDetails) throws Exception;
	
	
}
