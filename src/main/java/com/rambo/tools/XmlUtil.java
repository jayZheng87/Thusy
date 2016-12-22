package com.rambo.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtil {
    public static String formatXml(Document document, String charset, boolean istrans) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        StringWriter sw = new StringWriter();
        XMLWriter xw = new XMLWriter(sw, format);
        xw.setEscapeText(istrans);
        try {
            xw.write(document);
            xw.flush();
            xw.close();
        } catch (IOException e) {
            System.out.println("格式化XML文档发生异常，请检查！");
            e.printStackTrace();
        }

        return sw.toString();
    }

    public static void addVO(Element element, Object vo, String[] fields) {
        Class<? extends Object> clazz = vo.getClass();
        String className = clazz.getName().split("\\.")[clazz.getName().split("\\.").length - 1];
        className = className.substring(0, 1).toLowerCase() + className.substring(1);
        Element beanElement = element.addElement(className);
        Method[] methods = clazz.getMethods();// 所有方法
        Object value = null;
        String value4Insert = "";
        String methodStr = "";
        try {
            for (String field : fields) {
                methodStr = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
                for (Method method : methods) {
                    if (method.getName().equals(methodStr)) {
                        value = method.invoke(vo);
                        if (value != null && value != "") {
                            if (value.getClass() == Integer.class) {
                                value4Insert = value.toString();
                            } else {
                                value4Insert = value.toString();
                            }
                            beanElement.addElement(field).setText(value4Insert);
                        } else {
                            beanElement.addElement(field).setText("");
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void addVOnoSort(Element element, Object vo, String name) {
        Class<? extends Object> clazz = vo.getClass();
        Element beanElement = element.addElement(name);
        Method[] methods = clazz.getMethods();// 所有方法
        Object value = null;
        String value4Insert = "";
        String field = "";
        try {
            for (Method method : methods) {
                if (method.getName().indexOf("get") > -1 && !method.getName().equals("getClass") && !method.getName().equals("getFields")) {
                    field = method.getName().substring(3);
                    field = field.substring(0, 1).toLowerCase() + field.substring(1);
                    value = method.invoke(vo);
                    if (value != null && value != "") {
                        if (value.getClass() == Integer.class) {
                            value4Insert = value.toString();
                        } else {
                            value4Insert = value.toString();
                        }
                        beanElement.addElement(field).setText(value4Insert);
                    } else {
                        beanElement.addElement(field).setText("");
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /*
     * 模拟通过地址获取response文件的body部分
     */
    public static Map<String, Object> getReqBody(String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            SAXReader xr = new SAXReader();
            Document doc = xr.read(url);
            Element service = doc.getRootElement();
            Document docbody = DocumentHelper.parseText(service.element("body").getText());
            map.put("success", docbody);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            map.put("error", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /*
     * 模拟通过地址获取response文件的body部分
     */
    public static Map<String, Object> getBody(String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            SAXReader xr = new SAXReader();
            Document doc = xr.read(url);
            Element service = doc.getRootElement();
            String rtmCode = service.element("head").element("rtn_msg").elementText("Code");
            if (rtmCode.equals("000")) {
                // System.out.println(rtmCode);
                Document docbody = DocumentHelper.parseText(service.element("body").getText());
                map.put("success", docbody);
            } else {
                map.put("error", service.element("head").element("rtn_msg").elementText("Message"));
            }

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            map.put("error", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /*
     * 模拟通过文本获取response文件的body部分
     */
    public static Map<String, Object> getBodyFromText(String text) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Document doc = DocumentHelper.parseText(text);
            Element service = doc.getRootElement();
            String rtmCode = service.element("head").elementText("rtn_code");
            String code = service.element("head").element("rtn_msg").elementText("Code");
            if (rtmCode.equals("0") && code.equals("000")) {
                if (!service.element("body").getText().equals("")) {
                    Document docbody = DocumentHelper.parseText(service.element("body").getText());
                    map.put("success", docbody);
                } else {
                    map.put("success", "");
                }
            } else {
                String message = service.element("head").element("rtn_msg").elementText("Message");
                String reason = service.element("head").element("rtn_msg").elementText("Reason");
                map.put("error", message + "(" + reason + ")");
            }
        } catch (DocumentException e) {
            map.put("error", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /*
     *
     */
    public static Object getVOFromElement(Class<?> clazz, Element element) {
        Method[] methods = clazz.getMethods();// 所有方法
        String fieldname = "";
        Object vo = null;
        Field field = null;
        try {
            vo = clazz.newInstance();
            for (Method method : methods) {

                if (method.getName().indexOf("set") > -1 && !method.getName().equals("setClass") && !method.getName().equals("setFields")) {
                    fieldname = method.getName().substring(3);
                    fieldname = fieldname.substring(0, 1).toLowerCase() + fieldname.substring(1);
                    field = clazz.getDeclaredField(fieldname);
                    if (field.getType() == String.class && element.element(fieldname) == null) {
                        method.invoke(vo, "");
                    }
                    if (!(element.element(fieldname) == null || element.elementText(fieldname).equals(""))) {
                        if (field.getType() == String.class) {
                            method.invoke(vo, element.elementText(fieldname));
                        } else if (field.getType() == Long.class) {
                            method.invoke(vo, Long.parseLong(element.elementText(fieldname)));
                        } else if (field.getType() == Integer.class) {
                            method.invoke(vo, Integer.parseInt(element.elementText(fieldname)));
                        } else if (field.getType() == Double.class) {
                            method.invoke(vo, Double.parseDouble(element.elementText(fieldname)));
                        } else if (field.getType() == List.class) {
                            List<Object> subList = new ArrayList<Object>();
                            Type fc = field.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
                            if (fc == null)
                                continue;
                            if (fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
                            {
                                ParameterizedType pt = (ParameterizedType) fc;
                                Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0]; // 【4】
                                // 得到泛型里的class类型对象。
                                for (Object subElement : element.element(fieldname).elements()) {
                                    subList.add(getVOFromElement(genericClazz, (Element) subElement));
                                }
                            }
                            method.invoke(vo, subList);
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    /**
     * Title: getListFromElement
     * Description: 将报文 Gridelement,转换为 多个Gridlb ,放如一个 list 中
     * ex:  List<Object> bdcxmList = XmlUtil.getListFromElement(BdcxmdjxxGridlbVO.class, bdcxmdjxxGrid);
     * param: TODO (入参描述)
     * return: TODO (返回类型和参数描述)
     * date：2015年7月12日上午10:24:37
     */
    public static List<Object> getListFromElement(Class<?> clazz, Element element) {
        List<Object> list = new ArrayList<Object>();
        for (Object subElement : element.elements()) {
            list.add(getVOFromElement(clazz, (Element) subElement));
        }
        return list;
    }

    public static void addExpand(Document doc, String name, String value) {
        Element expand = doc.getRootElement().element("head").addElement("expand");
        expand.addElement("name").setText(name);
        expand.addElement("value").setText(value);
    }

    /**
     * Title: voFromJSONObject
     * Description: 将 JsonObject 转为相应的 bean
     * date：2015年7月12日上午10:22:14
     */
    public static Object voFromJSONObject(Class<?> clazz, JsonObject jo) {
        Gson gson = new Gson();
        return gson.fromJson(jo, clazz);
    }

    public static String escapeToXML(String string) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, len = string.length(); i < len; i++) {
            char c = string.charAt(i);
            switch (c) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '"':
                    sb.append("\"");
                    break;
                default:
                    sb.append(c);
            }
        }
        try {
            return sb.toString();
        } finally {
            sb = null;
        }
    }
}
