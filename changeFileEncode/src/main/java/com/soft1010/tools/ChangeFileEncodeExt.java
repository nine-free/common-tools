package com.soft1010.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jifuzhang on 17/7/26.
 */
public class ChangeFileEncodeExt {


    //main 入口
    public static void main(String[] args) {
        if (args == null || args.length != 4) {
            System.out.println("参数错误");
            System.exit(0);
        }
        System.out.println("原始目录 " + args[0] + " \n原始编码:" + args[1] + " \n目标目录:" + args[2] + " \n目标编码:" + args[3]);
        long start = System.currentTimeMillis();
        ChangeFileEncode.ChangeFileEncodeContext<String> changeFileEncodeContext = new ChangeFileEncode.ChangeFileEncodeContext();
        changeFileEncodeContext.setSrcPath(args[0]);
        changeFileEncodeContext.setSrcEncode(args[1]);
        changeFileEncodeContext.setDestPath(args[2]);
        changeFileEncodeContext.setDestEncode(args[3]);
        validate(changeFileEncodeContext);
        if (StringUtils.isNotBlank(changeFileEncodeContext.getMsg())) {
            System.out.println("error msg:" + changeFileEncodeContext.getMsg());
            System.exit(0);
        }
        handleDirectory(changeFileEncodeContext);

        System.out.println("处理完成 total file num =" + changeFileEncodeContext.getTotalFileNum().intValue() + "\n 耗时=" + (System.currentTimeMillis() - start) + "ms");
    }

    //校验
    private static void validate(ChangeFileEncode.ChangeFileEncodeContext changeFileEncodeContext) {
        if (StringUtils.isBlank(changeFileEncodeContext.getSrcPath())) {
            changeFileEncodeContext.setMsg("原始路径为空");
            return;
        }
        if (StringUtils.isBlank(changeFileEncodeContext.getSrcEncode())) {
            changeFileEncodeContext.setMsg("原始编码为空");
            return;
        }
        if (StringUtils.isBlank(changeFileEncodeContext.getDestEncode())) {
            changeFileEncodeContext.setMsg("目标编码为空");
            return;
        }
        if (StringUtils.isBlank(changeFileEncodeContext.getDestPath())) {
            changeFileEncodeContext.setMsg("目标路径为空");
            return;
        }

        File file = new File(changeFileEncodeContext.getSrcPath());

        if (!file.exists()) {
            changeFileEncodeContext.setMsg("目标路径不存在");
            return;
        }
        File destFile = new File(changeFileEncodeContext.getDestPath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    //处理逻辑
    private static void handleDirectory(ChangeFileEncode.ChangeFileEncodeContext changeFileEncodeContext) {
        File file = new File(changeFileEncodeContext.getCurrentPath());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File filetmp : files
                    ) {
                changeFileEncodeContext.setCurrentPath(filetmp.getPath());

                if (filetmp.isFile()) {
                    changeFileEncodeContext.getTotalFileNum().incrementAndGet();
                    handleEncode(changeFileEncodeContext);
                }
                if (filetmp.isDirectory()) {
                    handleDirectory(changeFileEncodeContext);
                }
            }
        }
    }

    private static void handleEncode(ChangeFileEncode.ChangeFileEncodeContext changeFileEncodeContext) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(changeFileEncodeContext.getCurrentPath())), changeFileEncodeContext.getSrcEncode()));
            File filename = new File(changeFileEncodeContext.getCurrentPath().replace(changeFileEncodeContext.getSrcPath(),changeFileEncodeContext.getDestPath()));
            if(!filename.getParentFile().exists()){
                filename.getParentFile().mkdirs();
            }
            filename.createNewFile();
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), changeFileEncodeContext.getDestEncode())));
            String line = null;
            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile("gbk");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    System.out.println(line);
                    line = StringUtils.replace(line, changeFileEncodeContext.getSrcEncode(), changeFileEncodeContext.getDestEncode());
                }

                out.write(line);
                out.write("\n");
                out.flush();
            }
            br.close();
            out.close();
//            new File(changeFileEncodeContext.getCurrentPath()).delete();
//            filename.renameTo(new File(changeFileEncodeContext.getCurrentPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ChangeFileEncodeContext<T> {
        private String srcPath; //原始目录
        private String destPath; //目标目录
        private String currentPath; //当前处理的目录
        private String srcEncode; //原始编码
        private String destEncode; //目标编码
        private AtomicInteger totalFileNum = new AtomicInteger(0); //文件数
        private T msg; //msg

        public T getMsg() {
            return msg;
        }

        public void setMsg(T msg) {
            this.msg = msg;
        }

        public String getCurrentPath() {
            if (StringUtils.isBlank(currentPath)) {
                return getSrcPath();
            }
            return currentPath;
        }

        public void setCurrentPath(String currentPath) {
            this.currentPath = currentPath;
        }

        public String getSrcPath() {
            return srcPath;
        }

        public void setSrcPath(String srcPath) {
            this.srcPath = srcPath;
        }

        public String getDestPath() {
            return destPath;
        }

        public void setDestPath(String destPath) {
            this.destPath = destPath;
        }

        public String getSrcEncode() {
            return srcEncode;
        }

        public void setSrcEncode(String srcEncode) {
            this.srcEncode = srcEncode;
        }

        public String getDestEncode() {
            return destEncode;
        }

        public void setDestEncode(String destEncode) {
            this.destEncode = destEncode;
        }

        public AtomicInteger getTotalFileNum() {
            return totalFileNum;
        }

        public void setTotalFileNum(AtomicInteger totalFileNum) {
            this.totalFileNum = totalFileNum;
        }
    }


}
