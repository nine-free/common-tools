package top.soft1010.tools.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static top.soft1010.tools.util.CommonUtils.checkNotNull;

/**
 * Created by bjzhangjifu on 2019/5/20.
 */
public final class ExcelBuilder {

    private Workbook workbook;

    private Map<String, Sheet> sheetMap = new ConcurrentHashMap<String, Sheet>();

    public Excel build() {
        checkNotNull(workbook, "workbook is null");
        return new Excel(this);
    }

    public ExcelBuilder() {
    }

    public ExcelBuilder(Workbook workBook) {
        this.workbook = workBook;
    }

    public ExcelBuilder(String fileName) {
        createWorkBook(fileName);
    }

    public ExcelBuilder createWorkBook(String fileName) {
        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        return this;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public ExcelBuilder createSheet(String fileName, String sheetName) {
        createWorkBook(fileName).createSheet(sheetName);
        return this;
    }

    public ExcelBuilder createSheet(String sheetName) {
        checkNotNull(workbook, "workbook is null");
        sheetMap.put(sheetName, workbook.createSheet(sheetName));
        return this;
    }

    public ExcelBuilder createSheets(String... sheetNames) {
        for (String sheetName : sheetNames
                ) {
            createSheet(sheetName);
        }
        return this;
    }

    public Sheet getSheet(String sheetName) {
        return sheetMap.get(sheetName);
    }

}
