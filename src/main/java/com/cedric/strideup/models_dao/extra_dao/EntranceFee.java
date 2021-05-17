package com.cedric.strideup.models_dao.extra_dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

@Entity
@Table( name="entrance_fees")
@Data
public class EntranceFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int entranceFeeId;

    private String cost;

    @Column( columnDefinition="TEXT" )
    private String description;
    private String title;
    
    public EntranceFee( int dataId , int entranceFeeId , JSONObject obj ) {

        this.dataId = dataId;
        this.entranceFeeId = entranceFeeId;
        this.cost = obj.getString("cost");
        this.description = obj.getString("description");
        this.title = obj.getString("title");
        
    }
    
}
