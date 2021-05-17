package com.cedric.strideup.models_dao.extra_dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

@Entity
@Table( name="topics")
@Data
public class Topic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int topicsId;
    
    private String topicIdString;
    private String name;

    public Topic( int dataId , int topicsId , JSONObject obj ) {

        this.dataId = dataId;
        this.topicsId = topicsId;
        this.topicIdString = obj.getString("id");
        this.name = obj.getString("name");
    }
    
}
