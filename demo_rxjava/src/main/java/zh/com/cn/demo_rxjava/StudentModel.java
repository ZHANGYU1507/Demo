package zh.com.cn.demo_rxjava;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public class StudentModel {
    private String name;
    private int age;
    private List<Course> list;

    public StudentModel(int age, List<Course> list, String name) {
        this.age = age;
        this.list = list;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(List<Course> list) {
        this.list = list;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Course> getList() {
        return list;
    }
}
class Course{
    private String name;
    private String tearcher;

    public Course(String name, String tearcher) {
        this.name = name;
        this.tearcher = tearcher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTearcher() {
        return tearcher;
    }

    public void setTearcher(String tearcher) {
        this.tearcher = tearcher;
    }
}