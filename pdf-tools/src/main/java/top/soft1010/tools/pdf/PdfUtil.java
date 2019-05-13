package top.soft1010.tools.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PdfUtil {

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

    /**
     *
     * @param dataMap
     * @param templateFilePath
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
}
