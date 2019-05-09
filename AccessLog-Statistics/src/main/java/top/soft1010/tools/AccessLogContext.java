package top.soft1010.tools;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by jifuzhang on 17/8/4.
 */
public  class AccessLogContext{
    private String path;
    private String seperator;
    private Integer keyIndex;
    private String encode = "utf-8"; //默认utf-8

    private AccessLogResult accessLogResult;

    public AccessLogContext() {
    }

    public AccessLogContext(String[] args) {
        this.path = args[0];
        this.seperator = args[1];
        this.keyIndex = Integer.valueOf(args[2]);
        if(args.length>3){
            this.encode = args[3];
        }
    }

    public AccessLogContext(String path, String seperator, Integer keyIndex) {
        this.path = path;
        this.seperator = seperator;
        this.keyIndex = keyIndex;
    }

    public AccessLogContext(String path, String seperator, Integer keyIndex, String encode) {
        this.path = path;
        this.seperator = seperator;
        this.keyIndex = keyIndex;
        this.encode = encode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSeperator() {
        if( StringUtils.equals(";",seperator)){
            return " ";
        }
        return seperator;
    }

    public void setSeperator(String seperator) {
        this.seperator = seperator;
    }

    public Integer getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(Integer keyIndex) {
        this.keyIndex = keyIndex;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public AccessLogResult getAccessLogResult() {
        return accessLogResult;
    }

    public void setAccessLogResult(AccessLogResult accessLogResult) {
        this.accessLogResult = accessLogResult;
    }

    @Override
    public String toString() {
        return "AccessLogContext{" +
                "path='" + path + '\'' +
                ", seperator='" + seperator + '\'' +
                ", keyIndex=" + keyIndex +
                '}';
    }
}
