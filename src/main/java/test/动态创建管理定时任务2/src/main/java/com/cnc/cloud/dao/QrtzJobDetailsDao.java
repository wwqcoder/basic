package test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.dao;

import java.util.List;
import java.util.Map;

import test.动态创建管理定时任务2.src.main.java.com.cnc.cloud.bean.QrtzJobDetails;
import test.动态创建管理定时任务2.src.main.java.com.myron.db.mybatis.annotation.MyBatisRepository;

@MyBatisRepository
public interface QrtzJobDetailsDao {
	//增加记录
	int insert(QrtzJobDetails qrtzJobDetails);
	int insertSelective(QrtzJobDetails qrtzJobDetails);
	int insertByBatch(List<QrtzJobDetails> list);
	//int insertSelectiveByBatch(List<QrtzJobDetails> list);
	//删除记录
	int deleteByPrimaryKey(String id);
	
	//修改记录
	int updateByPrimaryKey(QrtzJobDetails qrtzJobDetails);
	int updateByPrimaryKeySelective(QrtzJobDetails qrtzJobDetails);
	//int updateByBatch(List<QrtzJobDetails> list);
	//int updateSelectiveByBatch(List<QrtzJobDetails> list);
	
	//查询记录
	QrtzJobDetails selectByPrimaryKey(String id);
	
	//查询记录列表
	List<QrtzJobDetails> selectList(QrtzJobDetails qrtzJobDetails);
	
	List<Map<String, Object>> selectMapList(QrtzJobDetails qrtzJobDetails);


}
