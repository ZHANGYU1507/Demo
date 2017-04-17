package zh.com.cn.demo_rxjava.zh.com.cn.mode;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ListBean {

    private List<PagedataBean> pagedata;
    /**
     * query : {"countpage":65,"page":1,"up":"http://www.520m.com.cn/api/question/journalism-list?access-token=ZXUzL4jwA1SPaJ7oDROCqmRO_-lXbE--&page=1","next":"http://www.520m.com.cn/api/question/journalism-list?access-token=ZXUzL4jwA1SPaJ7oDROCqmRO_-lXbE--&page=2"}
     */

    private QueryBean query;

    public List<PagedataBean> getPagedata() {
        return pagedata;
    }

    public void setPagedata(List<PagedataBean> pagedata) {
        this.pagedata = pagedata;
    }

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public static class PagedataBean {
        /**
         * jid : 441
         * title : 杀死狗狗的真凶，竟然是这华丽丽的宠物床！
         * author : 好狗狗
         * create_time : 1487643938
         * coment_num : 0
         * img : ["http://peita.oss-cn-beijing.aliyuncs.com/question/374101487643937.jpg","http://peita.oss-cn-beijing.aliyuncs.com/question/307371487643937.jpg","http://peita.oss-cn-beijing.aliyuncs.com/question/283551487643937.jpg","http://peita.oss-cn-beijing.aliyuncs.com/question/237701487643937.jpg","http://peita.oss-cn-beijing.aliyuncs.com/question/617061487643937.jpg","http://peita.oss-cn-beijing.aliyuncs.com/question/436151487643938.jpg"]
         */

        private String jid;
        private String title;
        private String author;
        private String create_time;
        private String coment_num;
        private List<String> img;

        public String getJid() {
            return jid;
        }

        public void setJid(String jid) {
            this.jid = jid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getComent_num() {
            return coment_num;
        }

        public void setComent_num(String coment_num) {
            this.coment_num = coment_num;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "PagedataBean{" +
                    "jid='" + jid + '\'' +
                    ", title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", coment_num='" + coment_num + '\'' +
                    ", img=" + img +
                    '}';
        }
    }

    public static class QueryBean {
        /**
         * countpage : 65
         * page : 1
         * up : http://www.520m.com.cn/api/question/journalism-list?access-token=ZXUzL4jwA1SPaJ7oDROCqmRO_-lXbE--&page=1
         * next : http://www.520m.com.cn/api/question/journalism-list?access-token=ZXUzL4jwA1SPaJ7oDROCqmRO_-lXbE--&page=2
         */

        private int countpage;
        private int page;
        private String up;
        private String next;

        public int getCountpage() {
            return countpage;
        }

        public void setCountpage(int countpage) {
            this.countpage = countpage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getUp() {
            return up;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "QueryBean{" +
                    "countpage=" + countpage +
                    ", page=" + page +
                    ", up='" + up + '\'' +
                    ", next='" + next + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "pagedata=" + pagedata +
                ", query=" + query +
                '}';
    }
}
