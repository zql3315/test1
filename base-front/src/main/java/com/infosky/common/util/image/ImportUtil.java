package com.infosky.common.util.image;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.infosky.common.util.ExportUtil;
import com.infosky.common.util.ParseXMLUtil;

/**
 * 导入工具类
 * @author hjc
 *
 */
public class ImportUtil<T> {

    public List<T> resultList = new ArrayList<T>();

    public String message;

    public String errorCode;

    public static final File xmlFile = new File(Thread.currentThread().getContextClassLoader().getResource("importConfig.xml").getPath());

    @SuppressWarnings("unchecked")
    public ImportUtil(InputStream file, String code, int sheetIndex) {
        try {
            ParseXMLUtil parse = new ParseXMLUtil(xmlFile, code);

            Class<?> importClass = parse.getCla();//得到class

            Map<String, String> columnMap = parse.getColumnMap().get(code);

            Workbook book = WorkbookFactory.create(file);
            Sheet sheet = book.getSheetAt(sheetIndex);

            int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row ros = sheet.getRow(i);
                T obj = (T) importClass.newInstance();//实例化为一个对象
                for (int j = 0; j < coloumNum; j++) {
                    //得到对象中的方法
                    String method = "set" + ExportUtil.getMethodName(columnMap.get(String.valueOf(j)));
                    Field field = importClass.getDeclaredField(columnMap.get(String.valueOf(j)));
                    Method m = importClass.getDeclaredMethod(method, field.getType());

                    if (field.getType() == int.class) {
                        //得到Excel中的值
                        ros.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                        String value = ros.getCell(j).getStringCellValue();

                        m.invoke(obj, Integer.valueOf(value));
                    } else {
                        //得到Excel中的值
                        String value = ros.getCell(j).toString();
                        m.invoke(obj, value);
                    }

                }
                resultList.add(obj);
            }

            errorCode = "0000";

        } catch (Exception e) {
            message = "导入失败，请检查Excel内容与配置关系是否正确！";
            errorCode = "0001"; //表示导入错误
        }
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
