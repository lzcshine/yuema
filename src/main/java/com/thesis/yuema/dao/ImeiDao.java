package com.thesis.yuema.dao;

import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.ImeiInfo;

/**
 * @author:lzc
 * 2015-1-12 下午6:31:46
 */

public interface ImeiDao {

	boolean addImeiInfo(ImeiInfo imei);
	
	List<Map<String,Object>> getImeiInfoByImeiValue(String imei);
}
