package com.cedric.strideup.models_dao.extra_dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

import lombok.Data;

@Entity
@Table( name="exceptions")
@Data
public class Except {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dataId;
    private int operationHourId;
    private int exceptionId;
    
    private String exceptionHours;
    private String startDate;
    private String name;
    private String endDate;
    
    public Except( int dataId , int operationHourId , int exceptionId , JSONObject obj ) {

        this.dataId = dataId;
        this.operationHourId = operationHourId;
        this.exceptionId = exceptionId;
        
    //    JSONObject except = obj.getJSONObject("exceptionHours");
        this.startDate = obj.getString("startDate");
        this.name = obj.getString("name");
        this.endDate = obj.getString("endDate");
        
    }
    
}
