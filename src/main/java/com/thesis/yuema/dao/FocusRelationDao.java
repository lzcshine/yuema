package com.thesis.yuema.dao;

import java.util.List;
import java.util.Map;

import com.thesis.yuema.entity.FocusRelation;

/**
 * @author:lzc
 * 2015-1-19 下午12:10:14
 */

public interface FocusRelationDao {

	boolean addFocusRelation(FocusRelation bean);
	
	List<Map<String,Object>> getFocusListByUserId(int userId);
}
