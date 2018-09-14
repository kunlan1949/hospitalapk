package com.dommy.Data;

public class Handle {

    private Long id;
    private String bednum;
    private String name;
    private String gender;
    private String age;
    private String contact;
    private String doctor;
    private String IDcard;
    private String ill;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBednum() {
        return bednum;
    }
    public void setBednum(String bednum) {
        this.bednum = bednum;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
    public String getIDcard() {
        return IDcard;
    }
    public void setIDcard(String IDcard) {
        this.IDcard= IDcard;
    }
    public String getIll() {
        return ill;
    }
    public void setIll(String ill) {
        this.ill = ill;
    }

    public Handle(Long id, String bednum, String name,String gender,String age,String contact,String doctor,String IDcard,String ill) {
        super();
        this.id = id;
        this.bednum = bednum;
        this.name = name;
        this.gender=gender;
        this.age=age;
        this.contact=contact;
        this.doctor=doctor;
        this.IDcard=IDcard;
        this.ill=ill;
    }
    public Handle(String name, String doctor) {
        super();
        this.name = name;
        this.doctor = doctor;
    }
    public Handle() {
        super();
    }
    public String toString() {
        return "[编号: " + id + ", 床位: " + bednum + ", 名字: " + name+  ",性别" + gender+",联系方式:"+contact+",主治医生:"+doctor+",身份证号:"+IDcard+",病情描述:"+ill+"]";
    }
}
