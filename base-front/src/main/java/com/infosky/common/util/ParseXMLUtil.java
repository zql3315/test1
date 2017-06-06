package com.infosky.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析XML工具类
 * @author hjc
 *
 */
public class ParseXMLUtil {

    public Map<String, Map<String, String>> columnMap = new HashMap<String, Map<String, String>>();

    public Class<?> cla;

    /**开始解析xml文件**/
    public ParseXMLUtil(File xmlFilePath, String code) {
        FileInputStream in = null;
        try {
            if (xmlFilePath == null) {
                throw new FileNotFoundException();
            }
            SAXReader reader = new SAXReader();
            in = new FileInputStream(xmlFilePath);
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            Iterator<?> itEntity = root.elements("entity").iterator();
            while (itEntity.hasNext()) {
                Element entity = (Element) itEntity.next();

                if (entity.attributeValue("code").equals(code)) {
                    parseEntity(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**开始解析entity**/
    public void parseEntity(Element entity) {
        if (entity != null) {
            try {
                String code = entity.attributeValue("code");
                String className = entity.attributeValue("class");
                //得到Class
                cla = Class.forName(className);

                Iterator<?> itColumn = entity.elements("column").iterator();
                Map<String, String> col = new HashMap<String, String>();
                while (itColumn.hasNext()) {
                    Element column = (Element) itColumn.next();

                    if (column != null) {
                        String cell = column.attributeValue("cell");
                        String columnCode = column.attributeValue("code");
                        col.put(cell, columnCode);
                    }
                }
                columnMap.put(code, col);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Map<String, String>> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, Map<String, String>> columnMap) {
        this.columnMap = columnMap;
    }

    public Class<?> getCla() {
        return cla;
    }

    public void setCla(Class<?> cla) {
        this.cla = cla;
    }

}