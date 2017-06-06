package com.infosky.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 导出Excel
 * @author n004883
 */
public class ExportExcel {

    /**
     * 设置导出文件信息
     * @param response
     * @return
     * @throws IOException
     */
    public static OutputStream setExportFileInfo(HttpServletResponse response) throws IOException {
        //以系统当前时间为excel的名字
        String fileName = Long.toString(System.currentTimeMillis()) + ".xls";

        //获得输出流
        OutputStream outputStream = response.getOutputStream();
        response.reset();

        //设置导出excel提示框的名字以及内容的编码方式
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.setContentType("application/msexcel; charset=UTF-8");

        return outputStream;
    }

    /**
     * 创建Excel表格
     * @param outputStream 输出流
     * @param columnNames excel的第一行标题
     * @param mapLst 导出信息
     * @param bottom_row_str excel的最后一行标题
     */
    public static void createExcel(OutputStream outputStream, String[] columnNames, List<Map<String, String>> dataLst, String[] bottom_row_str) {
        WritableWorkbook writableWorkbook = null;

        try {
            //创建可写入的Excel工作簿
            writableWorkbook = Workbook.createWorkbook(outputStream);

            //创建工作表
            WritableSheet sheet = writableWorkbook.createSheet("create", 0);

            WritableCellFormat wcf = new WritableCellFormat();
            wcf.setAlignment(jxl.format.Alignment.CENTRE); // 设置对齐方式  

            WritableCell cell = null;

            //创建标题单元格
            for (int i = 0; i < columnNames.length; i++) {
                sheet.setColumnView(i, 20);//根据内容自动设置列宽

                cell = new Label(i, 0, columnNames[i].trim());
                sheet.addCell(cell);
                cell.setCellFormat(wcf);
            }

            //创建数据单元格
            for (int j = 1; j <= dataLst.size(); j++) {
                for (int i = 0; i < columnNames.length; i++) {
                    Map<String, String> data = dataLst.get(j - 1);
                    cell = new Label(i, j, String.valueOf(data.get(columnNames[i]) == null ? "" : data.get(columnNames[i])));
                    sheet.addCell(cell);
                    cell.setCellFormat(wcf);
                }
            }

            //创建底部单元格
            if (bottom_row_str != null) {
                for (int i = 0; i < bottom_row_str.length; i++) {
                    sheet.addCell(new Label(i, dataLst.size() + 1, bottom_row_str[i]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writableWorkbook != null) {
                    writableWorkbook.write();
                    writableWorkbook.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
