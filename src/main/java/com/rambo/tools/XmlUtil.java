package com.rambo.tools;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;

/**
 * 依赖于 Xstream 的XML工具类
 */
public class XmlUtil {
    /**
     * Description:java 转换成xml
     */
    public static String toXml(Object obj) {
        XStream xstream = new XStream();
        //XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
        //XStream xstream=new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释

        ////如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj);
    }

    /**
     * Description:将传入xml文本转换成Java对象
     */
    public static <T> T xml2Vo(String xmlStr, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        return (T) xstream.fromXML(xmlStr);
    }

    /**
     * Description:写到xml文件中去
     */
    public static boolean toXMLFile(Object obj, String absPath, String fileName) {
        String strXml = toXml(obj);
        String filePath = absPath + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }// end if
        OutputStream ous = null;
        try {
            ous = new FileOutputStream(file);
            ous.write(strXml.getBytes());
            ous.flush();
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        } finally {
            if (ous != null)
                try {
                    ous.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }

    /**
     * Description: 从xml文件读取报文
     */
    public static <T> T toBeanFromFile(String absPath, String fileName, Class<T> cls) throws Exception {
        String filePath = absPath + fileName;
        InputStream ins = null;
        try {
            ins = new FileInputStream(new File(filePath));
        } catch (Exception e) {
            throw new Exception("读{" + filePath + "}文件失败！", e);
        }

        XStream xstream = new XStream(new DomDriver("utf-8"));
        xstream.processAnnotations(cls);
        T obj = null;
        try {
            obj = (T) xstream.fromXML(ins);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new Exception("解析{" + filePath + "}文件失败！", e);
        }
        if (ins != null)
            ins.close();
        return obj;
    }
}