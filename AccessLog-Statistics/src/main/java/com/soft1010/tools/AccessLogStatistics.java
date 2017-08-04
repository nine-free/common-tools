package com.soft1010.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jifuzhang on 17/8/4.
 */
public class AccessLogStatistics {

    public static void main(String[] args) throws Exception {
        try {
            checkParams(args);
        } catch (Exception e) {
            System.out.println(e);
        }
        AccessLogContext accessLogContext = new AccessLogContext(args);
        System.out.println(accessLogContext.toString());

        read(accessLogContext);
        write(accessLogContext);
        System.out.println("total:"+accessLogContext.getAccessLogResult().getTotal()+"\n"+"unexcepted:"+accessLogContext.getAccessLogResult().getUnexpectedNum());
    }

    private static AccessLogContext read(AccessLogContext accessLogContext) throws Exception {
        File file = new File(accessLogContext.getPath());
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line=null;
        Integer total = 0;
        Integer unexpectedNum = 0;
        String key = "";
        Map<String,List<String>> interfaceListMap = new HashMap<String,List<String>>();
        while((line=br.readLine())!=null){
            total++;
            String[] strs = StringUtils.split(line,accessLogContext.getSeperator());
            if(strs==null || strs.length<accessLogContext.getKeyIndex()){
                unexpectedNum++;
                continue;
            }
            key = strs[accessLogContext.getKeyIndex()-1];
            if(!key.endsWith(".do")){
                unexpectedNum++;
                continue;
            }
            if(interfaceListMap.containsKey(key)){
                interfaceListMap.get(key).add(line);
            }else{
                List<String> list = new ArrayList<String>();
                list.add(line);
                interfaceListMap.put(key,list);
            }
        }
        AccessLogResult accessLogResult = new AccessLogResult(total,unexpectedNum,interfaceListMap);
        accessLogContext.setAccessLogResult(accessLogResult);
        return accessLogContext;
    }

    private static void write(AccessLogContext accessLogContext) throws Exception {
        File filename = new File(accessLogContext.getPath()+".result");
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename),accessLogContext.getEncode())));
        for (String k:accessLogContext.getAccessLogResult().getInterfaceMapList().keySet()
                ) {
            System.out.println("=============key:"+k+"size:"+accessLogContext.getAccessLogResult().getInterfaceMapList().get(k).size()+"===============");
            out.write("=============key:"+k+"   size:"+accessLogContext.getAccessLogResult().getInterfaceMapList().get(k).size()+"===============");
            if(k.contains("edit")|| k.contains("update")){
                for (String str : accessLogContext.getAccessLogResult().getInterfaceMapList().get(k)
                        ) {
                    out.write(str);
                    out.write("\n");
                    out.flush();
                }
            }
        }
        out.close();
    }

    private static void checkParams(String[] args) throws Exception {
        if (args == null || args.length < 3) {
            throw new RuntimeException("输入参数错误 依次输入文件路径 分隔符 第几列作为统计接口");
        }
    }

}
