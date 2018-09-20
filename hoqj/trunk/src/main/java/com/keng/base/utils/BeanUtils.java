package com.keng.base.utils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Jsoup;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanUtils {
    private static Log                log = LogFactory.getLog(BeanUtils.class);
    private final static ObjectMapper jsonObjectMapper;
    private final static SAXBuilder   xmlBuilder;
    static {
        xmlBuilder = new SAXBuilder();
        jsonObjectMapper = new ObjectMapper();
        // 解析器支持解析单引号
        jsonObjectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        // 解析器支持解析结束符
        jsonObjectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * 类型强转
     * 
     * @param fieldType
     * @param param
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public static <T> T typeCast(Class<T> fieldType, String param) {
        T value = null;
        try {
            if (param == null) {
                return null;
            }
            if (fieldType == String.class) {
                value = (T) param;
            } else if (fieldType == Integer.class) {
                value = (T) Integer.valueOf(param);
            } else if (fieldType == Double.class) {
                value = (T) Double.valueOf(param);
            } else if (fieldType == Long.class) {
                value = (T) Long.valueOf(param);
            } else if (fieldType == Character.class) {
                value = (T) Character.valueOf(param.toCharArray()[0]);
            } else if (fieldType == Boolean.class) {
                value = (T) Boolean.valueOf(param);
            } else if (fieldType == Float.class) {
                value = (T) Float.valueOf(param);
            } else if (fieldType == Short.class) {
                value = (T) Short.valueOf(param);
            } else if (fieldType == Date.class) {
                value = (T) parse(param, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            }
        } catch (Exception e) {
            return null;
        }
        return value;
    }

    /**
     * 页面提交表单转换成为Bean
     * 
     * @param request
     * @param cls
     * @return
     * @throws Exception
     */
    public static <T> T formToBean(HttpServletRequest request, Class<T> cls) throws Exception {
        T bean = cls.newInstance();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String methodName = "set";
            if (Character.isUpperCase(fieldName.charAt(1))) {
                methodName += fieldName;
            } else {
                methodName += fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            }
            Method method = cls.getDeclaredMethod(methodName, field.getType());
            String req_param = request.getParameter(fieldName);
            try {
                method.invoke(bean, typeCast(field.getType(), req_param));
            } catch (Exception e) {
                log.debug("[" + fieldName + "]" + e.getClass().getName() + "-->" + e.getMessage());
            }
        }
        return bean;
    }

    /**
     * 将Entity转换成为Map
     * 
     * @param bean
     * @return
     */
    public static <T> Map<String, String> beanToMap(T bean) {
        Class<? extends Object> cls = bean.getClass();
        Field[] fields = cls.getDeclaredFields();
        Map<String, String> data = new HashMap<String, String>();
        for (Field field : fields) {
            try {
                String fieldName = field.getName();
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method method = cls.getMethod(methodName);
                Object value = method.invoke(bean);
                if (value != null) {
                    data.put(fieldName, value.toString());
                }
            } catch (Exception e) {
            }
        }
        return data;
    }

    /**
     * 将JSON字符串转换为Map对象。 <b>强制转换</b>，将从字符串中寻找Json对象
     * 
     * @param text
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseJsonFromText(String text) throws Exception {
        text = text.trim();
        if (!text.startsWith("{")) {
            text = text.substring(text.indexOf('{'));
        }
        if (!text.endsWith("}")) {
            text = text.substring(0, text.lastIndexOf('}') + 1);
        }
        return jsonObjectMapper.readValue(text, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static List<Object> parseJsonFromTextAsList(String text) throws Exception {
        text = text.trim();
        if (!text.startsWith("[")) {
            text = text.substring(text.indexOf('['));
        }
        if (!text.endsWith("]")) {
            text = text.substring(0, text.lastIndexOf(']') + 1);
        }
        return jsonObjectMapper.readValue(text, List.class);
    }

    public static org.jsoup.nodes.Document parseTextToHtmlDoc(String text) throws Exception {
        return Jsoup.parse(text);
    }

    /**
     * 将JSON字符串转换为Map对象
     * 
     * @param text
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseTextToJson(String text) throws Exception {
        return jsonObjectMapper.readValue(text, Map.class);
    }

    /**
     * 将XML字符串转换为Element对象
     * 
     * @param text
     * @return
     * @throws Exception
     */
    public static Element parseTextToXml(String text) throws Exception {
        Document doc = xmlBuilder.build(text);
        if (doc != null) return doc.getRootElement();
        return null;
    }

    /**
     * 字符串转换为Date
     * 
     * @param str 格式为：Tue Mar 23 16:49:40 CST 2010
     * @param pattern 格式：EEE MMM dd HH:mm:ss zzz yyyy
     * @param locale　Locale.US
     * @return
     */
    public static Date parse(String str, String pattern, Locale locale) {
        if (str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期
     * 
     * @param date
     * @param pattern
     * @param locale
     * @return
     */
    public static String format(Date date, String pattern, Locale locale) {
        if (date == null || pattern == null) {
            return null;
        }
        return new SimpleDateFormat(pattern, locale).format(date);
    }
    /*
     * public static void main(String[] args) throws ParseException { Demo demo = new Demo();
     * demo.setAddress("redis测试"); demo.setDid(1L); // demo.setCount(22.0);
     * demo.setCreateDate(parse("Tue Mar 23 16:49:40 CST 2010", "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US));
     * System.out.println(format(demo.getCreateDate(), "yyyy-MM-dd HH:mm:ss", Locale.CHINA));
     * System.out.print(String.format("%tF %<tT%n", demo.getCreateDate()));
     * System.out.println(BeanUtils.beanToMap(demo)); }
     */
}
