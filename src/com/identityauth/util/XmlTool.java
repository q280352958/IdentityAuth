package com.identityauth.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlTool {
	private static boolean isAttr = false;

	public static Map<String, Object> xml2Map(String str)
			throws DocumentException {
		Map<String, Object> map = null;
		if (!str.isEmpty()) {
			Document doc = DocumentHelper.parseText(str);
			// map = XmlTool.dom2Map(doc);
			map = new HashMap<String, Object>();
			Element root = doc.getRootElement();
			map.put(root.getName(), dom2Map(root, root.attributes()));
		}
		return map;
	}

	public static Map<String, Object> dom2Map(Document doc, List attr) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();

		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {

			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), dom2Map(e, e.attributes()));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Map dom2Map(Element e, List attr) {
		Map map = new HashMap();
		for (int i = 0; i < attr.size(); i++) {
			// 根节点属性的取得
			Attribute item = (Attribute) attr.get(i);
			map.put("-" + item.getName(), item.getText());
		}
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = dom2Map(iter,iter.attributes());
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	public static void map2Xml(Map<String, Object> map, StringBuffer sb) {
		try {
			for (Iterator it = map.keySet().iterator(); it.hasNext();) {
				Object key = it.next();
				Object obj = map.get(key);
				if (obj instanceof Map) {
					sb.append("<" + key + ">");
					map2Xml((Map<String, Object>) obj, sb);
					sb.append("</" + key + ">");

				} else if (obj instanceof List) {
					for (int i = 0; i < ((List) obj).size(); i++) {
						sb.append("<" + key + ">");
						map2Xml((Map<String, Object>) ((List) obj).get(i), sb);
						sb.append("</" + key + ">");
					}
				} else {
					if (key.toString().contains("-")) {
						sb.deleteCharAt(sb.length() - 1);
						key = key.toString().replace("-", "");
						sb.append(" " + key + "=");
						sb.append("\"" + obj + "\" ");
						isAttr = true;
					} else {
						if (isAttr) {
							isAttr = false;
							sb.append(">");
						}
						sb.append("<" + key + ">");
						sb.append(obj);
						sb.append("</" + key + ">");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
