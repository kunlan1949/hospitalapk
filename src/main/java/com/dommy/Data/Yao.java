package com.dommy.Data;

public class Yao {
    private Long id;
    private String bednum_item;
    private String name_item;
    private String speed_item;
    private String save_item;




    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBednum_item() {
        return bednum_item;
    }
    public void setBednum_item(String bednum_item ){
        this.bednum_item = bednum_item;
    }
    public String getName_item() {
        return name_item;
    }
    public void setName_item(String name_item) {
        this.name_item = name_item;
    }
    public String getSpeed_item() {
        return speed_item;
    }
    public void setSpeed_item(String speed_item) {
        this.speed_item = speed_item;
    }
    public String getSave_item() {
        return save_item;
    }
    public void setSave_item(String save_item) {
        this.save_item = save_item;
    }


    public Yao(Long id, String bednum_item, String name_item,String speed_item,String save_item) {
        super();
        this.id = id;
        this.bednum_item = bednum_item;
        this.name_item = name_item;
        this.speed_item=speed_item;
        this.save_item=save_item;
    }
}
