package com.identityauth.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.lang.model.type.ErrorType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonTool {
	private static ObjectMapper objectMapper;
	private static String reString;
	private static JsonGenerator jsonGenerator;

	/**
	 * 对象转json
	 * 
	 * @param qryBean
	 * @return
	 */
	public static String obj2Json(Object obj) {
		objectMapper = new ObjectMapper();
		try {
			reString = objectMapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reString;
	}

	/**
	 * json字符串转对象
	 * 
	 * @param json
	 * @param obj
	 * @return
	 */
	public static Object json2Obj(String json, Class<?> obj) {
		Object reObj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			reObj = mapper.readValue(json, obj);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return reObj;
	}

	/**
	 * json转map
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(String json) throws Exception {
		objectMapper = new ObjectMapper();
		Map<String, Object> maps = null;
		boolean bool = false;
		if (json != null && json.length() > 0) {
			try {
				maps = (Map<String, Object>) objectMapper.readValue(json,
						Map.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
				bool = true;
			} catch (JsonMappingException e) {
				e.printStackTrace();
				bool = true;
			} catch (IOException e) {
				e.printStackTrace();
				bool = true;
			} finally {
				if (bool) {
					throw new Exception();
				}
			}
		}
		return maps;
	}

	public static String map2Json(Map<String, Object> map) {
		String json = "";
		try {
			StringWriter stringWriter = new StringWriter();
			objectMapper = new ObjectMapper();
			jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(
					stringWriter);
			jsonGenerator.writeObject(map);
			json = stringWriter.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static void main(String[] args) {
		try {
			Map<String, Object> map = json2Map("{\"SvcRetCont\": {\"BusiRet\": {\"RetCode\": \"1\",\"RetDesc\": \"1\",\"OrderId\": \"1\",\"DoneCode\": \"1\",\"UserId\": \"1\",\"OfferInsList\": {\"OfferInsInfo\": [\"1\",\"2\",\"3\"]},\"ProdInsList\": { \"ProdInsInfo\": \"1\" }}}}");
			System.out.println(map2Json(map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
