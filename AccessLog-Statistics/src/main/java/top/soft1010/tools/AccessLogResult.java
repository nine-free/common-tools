package top.soft1010.tools;

import java.util.List;
import java.util.Map;

/**
 * Created by jifuzhang on 17/8/4.
 */
public class AccessLogResult {

    private Integer total;
    private Integer unexpectedNum;
    Map<String,List<String>> interfaceMapList;

    public AccessLogResult() {
    }

    public AccessLogResult(Integer total, Map<String, List<String>> interfaceMapList) {
        this.total = total;
        this.interfaceMapList = interfaceMapList;
    }

    public AccessLogResult(Integer total, Integer unexpectedNum, Map<String, List<String>> interfaceMapList) {
        this.total = total;
        this.unexpectedNum = unexpectedNum;
        this.interfaceMapList = interfaceMapList;
    }

    public Integer getUnexpectedNum() {
        return unexpectedNum;
    }

    public void setUnexpectedNum(Integer unexpectedNum) {
        this.unexpectedNum = unexpectedNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Map<String, List<String>> getInterfaceMapList() {
        return interfaceMapList;
    }

    public void setInterfaceMapList(Map<String, List<String>> interfaceMapList) {
        this.interfaceMapList = interfaceMapList;
    }

    @Override
    public String toString() {
        return "AccessLogResult{" +
                "total=" + total +
                ", interfaceMapList=" + interfaceMapList +
                '}';
    }
}
