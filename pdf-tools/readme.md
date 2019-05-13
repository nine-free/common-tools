## 汇总一些pdf tools
生成pdf有多种方法，这里demo一个通过变量填充带表单的pdf模板的方法，相对比较简单，也比较干净（相对于一个一个元素的拼接出来一个pdf文件）

## 适用场景
#### 需求方提供给我们对应的word或者pdf模板，根据模板添加对应的数据之后导出pdf文件

## maven依赖
```
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itextpdf</artifactId>
            <version>5.4.3</version>
        </dependency>
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itext-asian</artifactId>
            <version>5.2.0</version>
        </dependency>
```

## 步骤
#### 1、需求方提供模板 如：test.docx
![image](http://soft1010.top/img/pdf-template-word.png)
#### 2、使用office打开之后保存为test.pdf
#### 3、使用adobe Acrobat 添加表单test_form.pdf
![image](http://soft1010.top/img/pdf-template-form.png)
#### 4、使用工具类填充模板文件 test_form.pdf 
示例中支持文本框、checkbox、radio、插入图片，添加水印的话，google一下会有很多现成的代码

## 注意点
1、checkbox在编辑表单时注意添加选项-导出值不能使用中文 这里使用的true
![image](http://soft1010.top/img/pdf-templater-checkbox.png)
2、注意图片的处理方式,具体参考代码

## 代码

```
    /**
     * 
     * @param dataMap 表单参数
     * @param templateFilePath 模板路径
     * @throws Exception
     */
    public static void createPDFAndDownload(Map<String, Object> dataMap, String templateFilePath) throws Exception {
        /* 使用中文字体 */
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
        fontList.add(bf);

        PdfReader reader = new PdfReader(PdfUtil.class.getClassLoader().getResourceAsStream("templates/" + templateFilePath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper ps = new PdfStamper(reader, bos);
        AcroFields fields = ps.getAcroFields();
        fields.setSubstitutionFonts(fontList);
        fillData(fields, dataMap, ps);
        /* 必须要调用这个，否则文档不会生成的 */
        ps.setFormFlattening(true);
        ps.close();

        //输出到本地指定目录
        File file = new File("D:\\result.pdf");
        bos.writeTo(new FileOutputStream(file));
        bos.close();

        //直接输出到response下载
//        response.setHeader("Content-Disposition",
//                "attachment; filename=" + new String(fileName.getBytes(charset), "iso8859-1"));
//        byte[] result = bos.toByteArray();
//        response.setContentLength(result.length);
//        OutputStream os = null;
//        os = response.getOutputStream();
//        os.write(result, 0, result.length);
//        os.flush();
//        os.close();

    }
```
填充数据
```
   /**
     * 
     * @param fields
     * @param data
     * @param ps
     * @throws IOException
     * @throws DocumentException
     */
    public static void fillData(AcroFields fields, Map<String, Object> data, PdfStamper ps) throws IOException, DocumentException {
        for (String key : data.keySet()) {
            if (key.endsWith("Checkbox")) {
                fields.setField(key, "" + data.get(key));
            } else if (key.endsWith("Image")) {
                int pageNo = fields.getFieldPositions(key).get(0).page;
                Rectangle signRect = fields.getFieldPositions(key).get(0).position;
                float x = signRect.getLeft();
                float y = signRect.getBottom();
                // 读图片
                //key resources 下的相对路径
                Image image = Image.getInstance(PdfUtil.class.getClassLoader().getResource((String) data.get(key)));
                // 获取操作的页面
                PdfContentByte under = ps.getOverContent(pageNo);
                // 根据域的大小缩放图片
                image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                // 添加图片
                image.setAbsolutePosition(x, y);
                under.addImage(image);
            } else if (key.endsWith("Radio")) {
                String value = data.get(key) + "";
                fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
            } else {
                String value = data.get(key) + "";
                fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
            }
        }
    }
```
main方法测试
```
    public static void main(String[] args) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //文本
        dataMap.put("code", "1234");
        //checkbox
        dataMap.put("checkbox1", "true");
        //插入图片 非水印
        dataMap.put("testImage", "images/1.gif");
        //radio
        dataMap.put("testRadio", "select1");
        dataMap.put("testRadio", "select2");
        try {
            createPDFAndDownload(dataMap, "test_form.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
[demo](https://github.com/nine-free/common-tools)

