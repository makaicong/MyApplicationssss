package com.example.myapplicationssss;

import java.util.List;
import cn.bmob.v3.BmobObject;


public class Good extends BmobObject {

    //private String objectId;//商品Id
    private String goodTitle;//商品标题
    private String goodDescription;//商品描述
    private String goodPrice;//商品价格
    private String goodSchool;//商品所在大学
    private String userName;//商品发布者ID
    private String goodCity;//商品所在的市
    private String goodFirstKind;//商品一级分类
    private String goodSecondkind;//商品二级分类
    private List<String> goodsImages;//商品图片url

    public String getGoodTitle() {
        return goodTitle;
    }

    public void setGoodTitle(String goodTitle) {
        this.goodTitle = goodTitle;
    }

    public String getGoodDescription() {
        return goodDescription;
    }

    public void setGoodDescription(String goodDescription) {
        this.goodDescription = goodDescription;
    }

    public String getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodSchool() {
        return goodSchool;
    }

    public void setGoodSchool(String goodSchool) {
        this.goodSchool = goodSchool;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodCity() {
        return goodCity;
    }

    public void setGoodCity(String goodCity) {
        this.goodCity = goodCity;
    }

    public String getGoodFirstKind() {
        return goodFirstKind;
    }

    public void setGoodFirstKind(String goodFirstKind) {
        this.goodFirstKind = goodFirstKind;
    }

    public String getGoodSecondkind() {
        return goodSecondkind;
    }

    public void setGoodSecondkind(String goodSecondkind) {
        this.goodSecondkind = goodSecondkind;
    }

    public List<String> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<String> goodsImages) {
        this.goodsImages = goodsImages;
    }
}
