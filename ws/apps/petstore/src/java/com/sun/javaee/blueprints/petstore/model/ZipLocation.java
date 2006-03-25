package com.sun.javaee.blueprints.petstore.model;

import javax.persistence.*;

/**
 * This class represents the data used for autocomplete of a 
 * user input for zipcode, city, state.
*/
@Entity
public class ZipLocation implements java.io.Serializable {

    private int zipCode;
    private String city;
    private String state;
         
    public ZipLocation() { }   

    @Id
    public int getZipCode() {
        return zipCode;
    }
    
    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }
  }



