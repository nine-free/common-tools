package com.soft1010.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jifuzhang on 17/7/25.
 * 修改文件的编码
 */
public class ChangeFileEncode {

    //main 入口
    public static void main(String[] args) {
        if(args==null || args.length!=3){
            System.out.println("参数错误");
            System.exit(0);
        }
        System.out.println("目标目录 "+args[0]+" \n原始编码:"+args[1] +" \n目标编码:"+args[2]);
        long start = System.currentTimeMillis();
        ChangeFileEncodeContext<String> changeFileEncodeContext = new ChangeFileEncodeContext();
        changeFileEncodeContext.setPath(args[0]);
        changeFileEncodeContext.setDestEncode(args[2]);
        changeFileEncodeContext.setSrcEncode(args[1]);
        validate(changeFileEncodeContext);
        if(StringUtils.isNotBlank(changeFileEncodeContext.getMsg())){
            System.out.println("error msg:"+changeFileEncodeContext.getMsg());
            System.exit(0);
        }
        handleDirectory(changeFileEncodeContext);

        System.out.println("处理完成 total file num ="+changeFileEncodeContext.getTotalFileNum().intValue()+"\n 耗时="+(System.currentTimeMillis()-start)+"ms");
    }

    //校验
    private static void validate(ChangeFileEncodeContext changeFileEncodeContext){
        if(StringUtils.isBlank(changeFileEncodeContext.getPath())){
            changeFileEncodeContext.setMsg("目标路径为空");
            return ;
        }
        if(StringUtils.isBlank(changeFileEncodeContext.getSrcEncode())){
            changeFileEncodeContext.setMsg("原始编码为空");
            return ;
        }
        if(StringUtils.isBlank(changeFileEncodeContext.getSrcEncode())){
            changeFileEncodeContext.setMsg("目标编码为空");
            return ;
        }

        File file = new File(changeFileEncodeContext.getPath());
        if(!file.exists()){
            changeFileEncodeContext.setMsg("目标路径不存在");
            return ;
        }
    }

    //处理逻辑
    private static void handleDirectory(ChangeFileEncodeContext changeFileEncodeContext){
        File file = new File(changeFileEncodeContext.getCurrentPath());
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File filetmp:files
                    ) {
                changeFileEncodeContext.setCurrentPath(filetmp.getPath());

                if(filetmp.isFile()){
                    changeFileEncodeContext.getTotalFileNum().incrementAndGet();
                    handleEncode(changeFileEncodeContext);
                }
                if(filetmp.isDirectory()){
                    handleDirectory(changeFileEncodeContext);
                }
            }
        }
    }

    private static void handleEncode(ChangeFileEncodeContext changeFileEncodeContext){
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File(changeFileEncodeContext.getCurrentPath())),changeFileEncodeContext.getSrcEncode()));
            File filename = new File(changeFileEncodeContext.getCurrentPath()+".tmp");
            filename.createNewFile();
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename),changeFileEncodeContext.getDestEncode())));
            String line=null;
            while((line=br.readLine())!=null){
                out.write(line);
                out.write("\n");
                out.flush();
            }
            br.close();
            out.close();
            new File(changeFileEncodeContext.getCurrentPath()).delete();
            filename.renameTo(new File(changeFileEncodeContext.getCurrentPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ChangeFileEncodeContext<T>{
        private String path; //目录
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
            if(StringUtils.isBlank(currentPath)){
                return getPath();
            }
            return currentPath;
        }

        public void setCurrentPath(String currentPath) {
            this.currentPath = currentPath;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
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
