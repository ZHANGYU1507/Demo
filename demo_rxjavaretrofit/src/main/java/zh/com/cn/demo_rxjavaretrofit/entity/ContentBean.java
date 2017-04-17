package zh.com.cn.demo_rxjavaretrofit.entity;

/**
 * Created by Administrator on 2016/11/29.
 */
public class ContentBean {
    private int id;
    private int valid;
    private String body;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "ContentBean{" +
                "id=" + id +
                ", valid=" + valid +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
