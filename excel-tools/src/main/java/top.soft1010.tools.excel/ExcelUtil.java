package top.soft1010.tools.excel;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;


/**
 * Created by bjzhangjifu on 2019/1/8.
 */
public final class ExcelUtil {

    public static CellStyle defaultHeadCellStyle(Workbook wb) {
        Font headFont = wb.createFont();
        CellStyle headStyle = wb.createCellStyle();
        headFont.setFontHeightInPoints((short) 10);
        headFont.setFontName("宋体");
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setColor(HSSFColor.WHITE.index);
        headStyle.setFont(headFont);
        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);           //水平居中
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        headStyle.setBorderTop(CellStyle.BORDER_THIN);                //边框
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        return headStyle;
    }

    public static CellStyle defaultContentCellStyle1(Workbook wb) {
        Font contentFont = wb.createFont();
        CellStyle contentStyle = wb.createCellStyle();
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setFontName("宋体");
        contentFont.setColor(HSSFColor.BLACK.index);
        contentStyle.setFont(contentFont);
        contentStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        contentStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);           //水平居中
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        contentStyle.setWrapText(true);                                  //自动换行
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        return contentStyle;
    }

    public static CellStyle defaultContentCellStyle2(Workbook wb) {
        Font contentFont = wb.createFont();
        CellStyle contentStyle = wb.createCellStyle();
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setFontName("宋体");
        contentFont.setColor(HSSFColor.BLACK.index);
        contentStyle.setFont(contentFont);
        contentStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        contentStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);           //水平居中
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
        contentStyle.setWrapText(true);                                  //自动换行
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        return contentStyle;
    }
}
