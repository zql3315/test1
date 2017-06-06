package com.infosky.sys.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.infosky.common.excel.ExcelRequest;
import com.infosky.common.query.jpa.Searchable;
import com.infosky.common.template.Node;
import com.infosky.framework.entity.dto.DTO;
import com.infosky.framework.service.PagingService;
import com.infosky.framework.service.exception.ServiceException;

/**
 * 
 * EXCEL解析处理类
 * 
 * @version  [版本号, 2014年8月24日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class ExcelResolver<D extends DTO<K>, P, K extends Serializable> {

    private static final Logger logger = LoggerFactory.getLogger(ExcelResolver.class);

    @Autowired
    private NodeResolver<K> resolver;

    /**
     * is->db
     * @param is
     * @param service
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public void doRead2DB(InputStream is, PagingService<D, P, K> service, ExcelRequest excelRequest) {
        Workbook wb = null;
        try {
            String template = excelRequest.getTemplate();
            //解析模板
            Node node = resolver.doRead(template);

            //判断模板文件时EXCEL2003或2007以上版本
            if (excelRequest.getSuffix().equals(".xls")) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }

            //默认第一个sheet为
            Sheet sheet = wb.getSheetAt(excelRequest.getPosition());
            //总行数
            int rowCount = sheet.getPhysicalNumberOfRows();
            //第一行获取业务上总的列数
            int cellNum = sheet.getRow(0).getPhysicalNumberOfCells();

            //临时集合,保存解析后需要提交的部分数据
            List<D> tempMem = Lists.newArrayList();

            //模板从第二行开始为数据行
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                //行数据
                Object[] dataRow = new Object[cellNum];

                //获取每列数据
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                        //公式型
                            case Cell.CELL_TYPE_FORMULA:
                                dataRow[j] = cell.getNumericCellValue();
                                break;
                            //数值型
                            case Cell.CELL_TYPE_NUMERIC:
                                //判断是否为时间类型
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    dataRow[j] = date;
                                } else {
                                    dataRow[j] = cell.getNumericCellValue();
                                }
                                break;
                            // 空值    
                            case Cell.CELL_TYPE_BLANK:
                                dataRow[j] = "";
                                break;
                            //默认字符串型
                            default:
                                dataRow[j] = cell.getStringCellValue();
                                break;
                        }
                    }
                }

                //转化数组为对应的Entity
                D dataModel = (D) resolver.doExecute(node, dataRow);
                //业务上可能还需要填充其他属性
                //dataModel=service.doConvert(dataModel);
                tempMem.add(dataModel);

                //如果达到1000行则提交
                if (tempMem.size() == 1000 || i == (rowCount - 1)) {
                    service.save(tempMem);
                    //清空
                    tempMem = Lists.newArrayList();
                }

            }
        } catch (Exception e) {
            logger.error("解析EXCEL异常", e);
        }
    }

    public static void main(String[] args) {
        String templatePath = "E:\\excel\\demo.xml";
        Map<String, Object> extInfo = new HashMap<String, Object>();

        ExcelRequest excelRequest = new ExcelRequest();
        excelRequest.setPageSize(10);
        excelRequest.setPosition(0);
        excelRequest.setSuffix(".xls");
        excelRequest.setExtInfo(extInfo);
        excelRequest.setTemplate(templatePath);
        //
        //        try
        //        {
        //            String filePath = "E:\\excel\\demo.xls";
        //            InputStream inputStream = new FileInputStream(new File(filePath));
        //            DemoService pagingService = new DemoService();
        ////            ExcelResolver resolver = new ExcelResolver<>();
        ////            resolver.doRead2DB(inputStream, pagingService, excelRequest);
        //        }
        //        catch (FileNotFoundException e)
        //        {
        //            e.printStackTrace();
        //        }

    }

    /**
     * db->filesystem
     * @param os
     * @param service
     * @param s
     * @param page
     * @param template
     * @see [类、类#方法、类#成员]
     */
    public void doWrite2FS(OutputStream os, PagingService<D, P, K> service, Searchable s, P page, String templateName) {
        Workbook wb = null;
        try {
            //因为Excel2003版最大行数是65536行，Excel2007开始的版本最大行数是1048576行，故NPOI导出时候选择了Excel2007
            wb = new XSSFWorkbook();
            String sheetName = "sheetName";
            Sheet sh = wb.createSheet(sheetName);
            logger.info("开始查询导出的数据...");
            List<D> dataList = page != null ? (List<D>) service.findAll(s, page) : (List<D>) service.findAll(s);
            //头写入
            dataList.add(0, null);

            logger.info("开始创建SHEET {} ...", sheetName);
            int i = 0;

            String template = this.getClass().getClassLoader().getResource(templateName).getPath();
            Node node = resolver.doRead(template);

            for (D model : dataList) {
                //项目信息导出
                Object[] data = resolver.doExecute(node, model);
                Row row = sh.createRow(i);

                int cidx = 0;
                for (Object cellValue : data) {
                    Cell c = row.createCell(cidx);

                    if (cellValue instanceof Date) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        c.setCellValue(sdf.format(cellValue));
                    } else if (cellValue instanceof Double) {
                        c.setCellValue((Double) cellValue);
                    } else if (cellValue instanceof Long) {
                        c.setCellValue((Long) cellValue);
                    } else if (cellValue instanceof Integer) {
                        c.setCellValue((Integer) cellValue);
                    } else {
                        c.setCellValue((String) cellValue);
                    }

                    cidx++;
                }

                i++;
            }

            wb.write(os);
            os.flush();
            os.close();

            logger.info("Excel导出结束!");
        } catch (Exception e) {
            logger.error("解析EXCEL异常", e);
            throw new ServiceException("解析EXCEL异常", e);
        }
    }

}
