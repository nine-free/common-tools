package top.soft1010.tools.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import top.soft1010.tools.util.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static top.soft1010.tools.util.CommonUtils.checkNotNull;

/**
 * Created by bjzhangjifu on 2019/5/20.
 */
public final class Excel {

    private ExcelBuilder excelBuilder;

    public Excel(ExcelBuilder excelBuilder) {
        this.excelBuilder = excelBuilder;
    }

    public Workbook getWorkBook() {
        return excelBuilder.getWorkbook();
    }

    public Sheet getSheet(String sheetName) {
        checkNotNull(sheetName, "sheetName is null");
        return excelBuilder.getSheet(sheetName);
    }

    public String write(String filePath, String fileName) {
        try {
            FileUtil.getFile(write(), filePath, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public byte[] write() throws IOException {
        byte[] result = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelBuilder.getWorkbook().write(out);
        result = out.toByteArray();
        return result;
    }


}
