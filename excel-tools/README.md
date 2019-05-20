## 1、poi生成excel文件 2、填充excel模板导出数据

## maven依赖
```
		<dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>${poi.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>${poi.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml-schemas</artifactId>
            <version>${poi.version}</version>
        </dependency>

```

## 运用 直接上代码
#### 直接生成excel文件
```
		Excel excel = new ExcelBuilder()
                .createWorkBook("excel1.xlsx") //创建workbook
                .createSheet("sheet1")  //添加sheet1
                .createSheet("sheet2")  //添加sheet2
                .build();

        try {
            String[] titles1 = new String[]{"1", "2"};
            String[] titles2 = new String[]{"b1", "b2", "b3", "b4", "b5"};

            List<Object[]> datas = new ArrayList<Object[]>();
            datas.add(new Object[]{1, 2, 3, 4, 5, 6});

            //合并单元格
            List<CellRangeAddress> cellRangeAddresses = new ArrayList<CellRangeAddress>();
            cellRangeAddresses.add(new CellRangeAddress(0, 0, 1, 2));

            //为sheet1添加header 添加数据
            new SheetBuilder(excel.getSheet("sheet1"))
                    .addHeader(0, titles1)
                    .addContentData(null, datas, 1);
            //为sheet2添加header  添加数据 合并单元格操作
            new SheetBuilder(excel.getSheet("sheet2"))
                    .addHeader(0, titles2)
                    .addContentData(null, datas, 1)
                    .addMergeCells(cellRangeAddresses);

            //输出excel到指定文件
            excel.write("D:\\", System.currentTimeMillis() + "tmp.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
```

#### 根据已有的excel模板填充数据得到导出文件
```
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

```
```
 		Workbook workbook = new ExcelParser().parse("temp.xlsx").getWorkbook();//解析指定文件为workbook
        Sheet sheet = workbook.getSheet("删除的"); //获取指定sheet
        Cell cell = sheet.getRow(0).createCell(0); //创建单元格
        cell.setCellValue(time); //为单元格设置内容
        Excel excel = new ExcelBuilder(workbook).build(); //
        excel.write("D:\\", "tmp" + time + ".xlsx"); //输出填充数据之后的excel文件
```


注意以上代码用到的类可以在github对应的excel-tools module找到 
[demo](https://github.com/nine-free/common-tools)

