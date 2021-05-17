package com.cedric.strideup.models_dao.extra_dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

@Entity
@Table( name="images")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int imageId;
    
    private String credit;
    private String title;
    private String altText;
    private String caption;
    private String url;
    
    public Image( int dataId , int imageId , JSONObject obj ) {

        this.dataId = dataId;
        this.imageId = imageId;
        
        this.credit = obj.getString("credit");
        this.title = obj.getString("title");
        this.altText = obj.getString("altText");
        this.caption = obj.getString("caption");
        this.url = obj.getString("url");
        
    }
    
}
