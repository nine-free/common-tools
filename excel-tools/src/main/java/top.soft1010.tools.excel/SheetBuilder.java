package top.soft1010.tools.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

import static top.soft1010.tools.util.CommonUtils.checkNotNull;

/**
 * Created by bjzhangjifu on 2019/5/20.
 */
public final class SheetBuilder {

    private Sheet sheet;

    public SheetBuilder(Sheet sheet) {
        this.sheet = sheet;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public SheetBuilder addHeader(int i, String[] titles) {
        addHeader(i, titles, ExcelUtil.defaultHeadCellStyle(sheet.getWorkbook()));
        return this;
    }

    /**
     * @param rowNum
     * @param titles
     * @param cellStyle
     * @return
     */
    public SheetBuilder addHeader(int rowNum, String[] titles, CellStyle cellStyle) {
        checkNotNull(sheet, "sheet 为空");
//        if (cellStyle == null) {
//            cellStyle = ExcelUtil.defaultHeadCellStyle(sheet.getWorkbook());
//        }
        int length = 0;
        if (titles == null || (length = titles.length) == 0)
            return this;
        Row row = sheet.createRow(rowNum);
        Cell cell = null;
        for (int i = 0; i < length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(titles[i]);
        }
        return this;
    }

    /**
     * @param cellStyle
     * @param datas
     * @param beginRow
     * @return
     */
    public SheetBuilder addContentData(CellStyle cellStyle, List<Object[]> datas, int beginRow) {
        checkNotNull(sheet, "sheet 为空");
        CellStyle contentStyle1 = null;
        CellStyle contentStyle2 = null;
//        if (cellStyle != null) {
//            contentStyle1 = cellStyle;
//            contentStyle2 = cellStyle;
//        } else {
//            contentStyle1 = ExcelUtil.defaultContentCellStyle1(sheet.getWorkbook());
//            contentStyle2 = ExcelUtil.defaultContentCellStyle2(sheet.getWorkbook());
//        }
        int size = 0;
        if (datas == null || (size = datas.size()) == 0)
            return this;

        Row row = null;
        Cell cell = null;
        Object objs[] = null;
        int length = 0;
        for (int i = 0; i < size; i++) {
            objs = datas.get(i);
            if (objs == null || (length = objs.length) == 0)
                continue;
            row = sheet.createRow(beginRow++);
            row.setZeroHeight(false);
            for (int j = 0; j < length; j++) {
                cell = row.createCell(j);
//                if (i % 2 == 0) {
//                    cell.setCellStyle(contentStyle1);
//                } else {
//                    cell.setCellStyle(contentStyle2);
//                }
                //默认都设置为字符串 根据具体情况调整为数字，日期等
                cell.setCellValue(objs[j] == null ? "" : objs[j].toString());
            }
        }
        return this;
    }

    public SheetBuilder addCell(int row, int col, Object value, CellStyle cellStyle) {
        checkNotNull(sheet, "sheet 为空");
        Row row1 = sheet.getRow(row) == null ? sheet.createRow(row) : sheet.getRow(row);
        Cell cell = row1.getCell(col) == null ? row1.createCell(col) : row1.getCell(col);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
        cell.setCellValue(value == null ? "" : value.toString());
        return this;
    }

    /**
     * @param cellRangeAddresses
     * @return
     */
    public SheetBuilder addMergeCells(List<CellRangeAddress> cellRangeAddresses) {
        checkNotNull(sheet, "sheet 为空");
        for (CellRangeAddress cellRangeAddress : cellRangeAddresses
                ) {
            sheet.addMergedRegion(cellRangeAddress);
        }
        return this;
    }

}
