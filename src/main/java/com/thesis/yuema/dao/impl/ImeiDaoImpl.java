package com.thesis.yuema.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.thesis.yuema.dao.ImeiDao;
import com.thesis.yuema.entity.ImeiInfo;

/**
 * @author:lzc
 * 2015-1-12 下午6:33:52
 */
@Repository("imeiDaoImpl")
public class ImeiDaoImpl extends BaseDaoImpl<ImeiInfo> implements ImeiDao {

	@Override
	public boolean addImeiInfo(ImeiInfo imei) {
		return this.save(imei);
	}

	@Override
	public List<Map<String, Object>> getImeiInfoByImeiValue(String imei) {
		// TODO Auto-generated method stub
		return null;
	}

}
