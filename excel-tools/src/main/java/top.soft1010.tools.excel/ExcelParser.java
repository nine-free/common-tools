package top.soft1010.tools.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

/**
 * Created by bjzhangjifu on 2019/5/20.
 */
public class ExcelParser {

    private Workbook workbook;

    public Workbook getWorkbook() {
        return workbook;
    }

    public ExcelParser parse(String fileName) throws IOException {
        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/" + fileName));
        } else {
            workbook = new XSSFWorkbook(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/" + fileName));
        }
        return this;
    }

}
