package com.drmtx.app.util;

import java.util.List;
import java.util.Map;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;

public class JsonUtil {

	public static List<Object> parseJsonAsList(String jsonStr) {
		JsonParser jParser = new JacksonJsonParser();
		List<Object> jsonList = jParser.parseList(jsonStr);
		return jsonList;
	}
	

	public static void getAllJsonAttrValuesList(List<Object> jsonList, String jspAttribute, StringBuilder sb) {
		for (Object obj : jsonList) {
			//System.out.println("Object type:" + obj.getClass() + ", " + obj);
			if (obj instanceof List) {
				getAllJsonAttrValuesList((List) obj, jspAttribute,sb);
			} else if (obj instanceof Map) {
				getAllJsonAttrValuesMap((Map) obj, jspAttribute,sb);
			}
		}
	}

	public static void getAllJsonAttrValuesMap(Map<Object, Object> jsonMap, String jspAttribute, StringBuilder sb) {
		for (Object obj : jsonMap.keySet()) {
			//System.out.println("Object type:" + obj.getClass() + ", " + obj);
			Object objValue = jsonMap.get(obj);
			if (obj.equals(jspAttribute)) {
				sb.append(" ").append(objValue);
//				System.out.println("Key => " + obj);
//				System.out.println("Value => " + objValue);
			} else {
				if (objValue instanceof List) {
					getAllJsonAttrValuesList((List) objValue, jspAttribute,sb);
				} else if (objValue instanceof Map) {
					getAllJsonAttrValuesMap((Map) objValue, jspAttribute,sb);
				}
			}
		}
	}

}
