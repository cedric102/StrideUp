package com.cedric.strideup.models_dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table( name="data_string")
@Data
public class DataString {

    @Id 
    @Column( name = "parkCode" , unique=true , columnDefinition="VARCHAR(45)" )
    private String parkCode; 
    
    @Column( columnDefinition="TEXT" )
    private String body;

    public DataString() {}
    
    public DataString( String parkCode , String body ) {

        this.parkCode = parkCode;
        this.body = body;
    }

}
