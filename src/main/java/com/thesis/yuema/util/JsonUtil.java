package com.thesis.yuema.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

/**
 * @author lzc
 * json的工具类
 */
public class JsonUtil {
	private static final String JACKSON_OBJECT_MAPPER_BEAN_NAME = "jacksonObjectMapper";
	private static ObjectMapper mapper;
	public static ObjectMapper getMapper() {
		return (ObjectMapper) SpringEntityUtil
				.getBean(JACKSON_OBJECT_MAPPER_BEAN_NAME);
	}
	
	static {
		mapper = getMapper();
	}
	
	/**
	 * 得到一个新的ObjectMapper
	 * @return 新的ObjectMapper
	 */
	public static ObjectMapper newMapper(){
		mapper = new ObjectMapper();
		return mapper;
	}
	
	/**
	 * 重新创建一个ObjectMapper
	 */
	public static void buildMapper(){
		mapper = new ObjectMapper();
	}
	
	/**
	 * 将POJO转换为JSON的方法
	 * @param object - 要转换的POJO
	 * @return POJO对应的JSON
	 */
	public static String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 排除指定属性并将对象转换成json字符串格式的方法
	 * 
	 * @param filterBean
	 *            - 需要转换的对象(注意，需要在要转换的对象中定义JsonFilter注解)
	 * @param filterBeanName
	 * 			  - 对象中定义的JsonFilter注解的名字
	 * @param properties2Exclude
	 *            - 需要排除的属性         
	 */
	@SuppressWarnings("deprecation")
	public String toJsonStrWithExcludeProperties(Object filterBean,
			String filterBeanName, String[] properties2Exclude)
			throws JsonGenerationException, JsonMappingException, IOException {
		FilterProvider filters = new SimpleFilterProvider()
				.addFilter(filterBeanName, SimpleBeanPropertyFilter
						.serializeAllExcept(properties2Exclude));
		String json = mapper.filteredWriter(filters).writeValueAsString(
				filterBean);
		return json;
	}
	/**
	 * 将对象的指定属性转换成json字符串格式的方法
	 * @param filterBean
	 *            - 需要转换的对象(注意，需要在要转换的对象中定义JsonFilter注解)
	 * @param filterBeanName
	 * 			  - 对象中定义的JsonFilter注解的名字
	 * @param properties
	 *            - 需要转换的属性
	 */
	@SuppressWarnings("deprecation")
	public String toJsonStrWithProperties(Object filterBean,
			String filterBeanName, String[] properties)
			throws JsonGenerationException, JsonMappingException, IOException {
		FilterProvider filters = new SimpleFilterProvider()
				.addFilter(filterBeanName, SimpleBeanPropertyFilter
						.filterOutAllExcept(properties));
		String json = mapper.filteredWriter(filters).writeValueAsString(
				filterBean);
		return json;
	}

	/**
	 * 将JSON字符串转换为对象
	 * @param json - 要转换为对象的json语句
	 * @param clazz - 对象的class类型
	 * @return JSON字符串相应的对象
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将JSON字符串转换为对象
	 * @param json - 要转换的json对象
	 * @param typeReference - 要转换的类型
	 * @return 指定类型的json格式
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference)
			throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(json, typeReference);
	}

	/**
	 * 将另一个属性添加到已有的json的方法
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static String converString(String arg0, String arg1){
		return arg0.substring(0, arg0.length() - 1) + "," + arg1 + "}";
	}
	
	public static JsonNode getJsonNode(String json){
		try {
			return getMapper().readTree(json);
		} catch (JsonProcessingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}