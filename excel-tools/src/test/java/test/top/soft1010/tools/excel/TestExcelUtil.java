package test.top.soft1010.tools.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import top.soft1010.tools.excel.Excel;
import top.soft1010.tools.excel.ExcelBuilder;
import top.soft1010.tools.excel.ExcelParser;
import top.soft1010.tools.excel.SheetBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by bjzhangjifu on 2019/5/20.
 */
public class TestExcelUtil {

    ThreadPoolExecutor executorService =
            new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000));

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    excel();
                }
            });

        }
        //主进程等待10s 等线程跑完
        Thread.sleep(10000);
    }


    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    excel2();
                }
            });

        }
        //主进程等待10s 等线程跑完
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void excel2() {
        try {
            long time = System.currentTimeMillis() + new Random().nextInt(100);
            Workbook workbook = new ExcelParser().parse("temp.xlsx").getWorkbook();
            Sheet sheet = workbook.getSheet("删除的");
            Cell cell = sheet.getRow(0).createCell(0);
            cell.setCellValue(time);
            Excel excel = new ExcelBuilder(workbook).build();
            excel.write("D:\\", "tmp" + time + ".xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void excel() {
        Excel excel = new ExcelBuilder()
                .createWorkBook("excel1.xlsx")
                .createSheet("sheet1")
                .createSheet("sheet2")
                .build();

        try {
            String[] titles1 = new String[]{"1", "2"};
            String[] titles2 = new String[]{"b1", "b2", "b3", "b4", "b5"};

            List<Object[]> datas = new ArrayList<Object[]>();
            datas.add(new Object[]{1, 2, 3, 4, 5, 6});

            List<CellRangeAddress> cellRangeAddresses = new ArrayList<CellRangeAddress>();
            cellRangeAddresses.add(new CellRangeAddress(0, 0, 1, 2));

            new SheetBuilder(excel.getSheet("sheet1"))
                    .addHeader(0, titles1)
                    .addContentData(null, datas, 1);

            new SheetBuilder(excel.getSheet("sheet2"))
                    .addHeader(0, titles2)
                    .addContentData(null, datas, 1)
                    .addMergeCells(cellRangeAddresses);

            excel.write("D:\\", System.currentTimeMillis() + "tmp.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
