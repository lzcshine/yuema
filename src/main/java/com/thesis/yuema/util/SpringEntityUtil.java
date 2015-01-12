package com.thesis.yuema.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;



/**
 * 通过Spring获取Entity的工具类
 * @author 
 *
 */
public class SpringEntityUtil implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringEntityUtil.applicationContext = applicationContext;	
	}
	
	public void destroy() throws Exception {
		applicationContext = null;
	}

	/**
	 * 获取applicationContext
	 * @return applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例
	 * @param name - 配置文件中Bean的名称
	 * @return bean实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

}