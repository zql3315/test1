package com.infosky.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * 导出工具类
 * @author hjc
 *
 */
public class ExportUtil {

    public static void exportExcel(List<?> list, String fileName, HttpServletResponse response, String columnNames[], String keys[]) throws IOException {
        List<Map<String, Object>> excelList = createExcelRecord(list, fileName);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(excelList, keys, columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null) bis.close();
            if (bos != null) bos.close();
        }
    }

    public static List<Map<String, Object>> createExcelRecord(List<?> list, String fileName) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", fileName);
        listmap.add(map);

        if (list.isEmpty()) {
            return listmap;
        }

        Class<?> className = list.get(0).getClass();

        try {
            Method[] methodArray = className.getMethods();

            for (int j = 0; j < list.size(); j++) {
                Object obj = list.get(j);
                obj.getClass().getDeclaredFields();
                Map<String, Object> mapValue = new HashMap<String, Object>();

                for (Method method : methodArray) {
                    String methodName = method.getName();

                    if (methodName.startsWith("get")) {
                        mapValue.put(getFiledName(methodName), method.invoke(obj));
                    }
                }

                listmap.add(mapValue);
            }
        } catch (Exception e) {

        }
        return listmap;
    }

    private static String getFiledName(String methodName) {
        Character firstChar = methodName.charAt(3);
        return Character.toLowerCase(firstChar) + methodName.substring(4);
    }

    /**
     * 把一个字符串的第一个字母大写
     * @param fildeName
     * @return
     * @throws Exception
     */
    public static String getMethodName(String filedName) throws Exception {
        byte[] items = filedName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
