package zh.com.cn.demo_rxjava;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
public class WeathBean {

    private String resultcode;
    private String reason;
    private  Result result;
    private String status;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Result{
        private  String company;
        private  String com;
        private String no;
        private List<list> list;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public List<list> getList() {
            return list;
        }

        public void setList(List<list> list) {
            this.list = list;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }
    }

    public static class list{
        private String datetime;
        private String remark;
        private String zone;

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
