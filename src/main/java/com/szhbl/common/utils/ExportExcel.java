package com.szhbl.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Component
public class ExportExcel {
    public static void arrageExcel(String headName, String fileName, String sheetName, String[] colNames, List<String[]> list,
                            HttpServletResponse response) {
        HSSFWorkbook wb = new HSSFWorkbook();// 创建webbook对应Excel文件
        HSSFSheet sheet = wb.createSheet(sheetName);// 在webbook中添加一个sheet,对应Excel文件中的sheet

        // 标题 
        HSSFFont headfont = wb.createFont();
        headfont.setFontName("宋体");
        headfont.setFontHeightInPoints((short) 14);// 字体大小
        headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 创建一个粗体格式
        HSSFCellStyle headstyle = wb.createCellStyle();
        headstyle.setFont(headfont);
        headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        // 设置背景颜色
        headstyle.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
        // solid 填充 foreground 前景色
        headstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // headstyle.setLocked(true);

        // 合并标题行
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colNames.length - 1));// 起始行号，终止行号，起始列号，终止列号

        HSSFRow row = sheet.createRow(0); // 行
        HSSFCell cell;// 单元格
        row.setHeight((short) 0x200);
        cell = row.createCell(0);
        cell.setCellValue(headName);
        cell.setCellStyle(headstyle);
        // 表头
        HSSFCellStyle fontStyle = wb.createCellStyle();// 创建表头单元格格式
        fontStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        fontStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 创建一个下边框格式
        fontStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 创建一个左边框格式
        fontStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 创建一个上边框格式
        fontStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 创建一个右边框格式
        // 设置背景颜色
        fontStyle.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
        // solid 填充 foreground 前景色
        fontStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFFont font = wb.createFont();// 创建字体格式
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 创建一个粗体格式
        fontStyle.setFont(font);// 设置字体格式

        row = sheet.createRow(1);// 行初始化
        // 生成表头
        for (int h = 0; h < colNames.length; h++) {
            cell = row.createCell(h);// 创建单元格
            cell.setCellValue(colNames[h]);// 设置表头名称
            cell.setCellStyle(fontStyle);// 设置单元格字体
            sheet.setColumnWidth(h, 5000);// 设置列宽
        }
        sheet.createFreezePane(0, 2); // 冻结x*2(宽*高)区域中的单元格，这里是冻结标题与表头单元格

        HSSFCellStyle style = wb.createCellStyle();// 创建数据单元格格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 创建一个下边框格式
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 创建一个左边框格式
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 创建一个上边框格式
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 创建一个右边框格式

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HSSFCellStyle dateStyle = wb.createCellStyle();
        dateStyle.cloneStyleFrom(style);
        HSSFDataFormat format = wb.createDataFormat();
        dateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));

        // 生成表格数据
        for (int i = 0; i < list.size(); i++) {
            String[] fls = list.get(i);
            row = sheet.createRow(i + 2);// 创建行
            for (int j = 0; j < fls.length; j++) {

                cell = row.createCell(j);
                // cell.setCellStyle(style);//设置单元格样式-居中

                if (fls[j] != null) {// 字段不为空
                    if (fls[j].length() == 10 && fls[j].charAt(4) == '-' && fls[j].charAt(7) == '-') {
                        try {
                            cell.setCellStyle(dateStyle);
                            cell.setCellValue(sdf.parse(fls[j]));
                        } catch (ParseException e) {
                            System.out.println(e);
                            e.printStackTrace();
                        }
                    } else {
                        cell.setCellStyle(style);
                        cell.setCellValue(fls[j]);// 字段值
                    }
                } else {// 字段为空
                    cell.setCellStyle(style);
                    cell.setCellValue("");// 字段值
                }
            }
        }

        try // 将输出Excel文件保存到指定位置
        {
            String encodedfileName = new String(fileName.getBytes("utf-8"),"ISO-8859-1" );
            response.reset();
            //response.setContentType("application/OCTET-STREAM;charset=gb2312");
            response.setHeader("content-type", "text/plain;charset=UTF-8");
            response.setHeader("pragma", "no-cache");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + encodedfileName + ".xls\"");
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("托书汇总表导出失败",e.toString(),e.getStackTrace());
        }
    }

}
