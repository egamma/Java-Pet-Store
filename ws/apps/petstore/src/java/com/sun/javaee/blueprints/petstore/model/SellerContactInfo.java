/* Copyright 2006 Sun Microsystems, Inc. All rights reserved. You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at: http://developer.sun.com/berkeley_license.html
$Id: SellerContactInfo.java,v 1.3 2006-05-03 21:48:59 inder Exp $ */

package com.sun.javaee.blueprints.petstore.model;

import javax.persistence.*;

@Entity        
public class SellerContactInfo implements java.io.Serializable {
    
    private String contactInfoID;
    private String lastName;
    private String firstName;
    private String email;
    
    public SellerContactInfo() { }
    public SellerContactInfo(String firstName, String lastName,
            String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    @TableGenerator(name="CONTACTINFO_ID_GEN",
            table="ID_GEN",
            pkColumnName="GEN_KEY",
            valueColumnName="GEN_VALUE",
            pkColumnValue="CONTACT_INFO_ID",
            allocationSize=1)
            @GeneratedValue(strategy=GenerationType.TABLE,generator="CONTACTINFO_ID_GEN")
            @Id
            public String getContactInfoID() {
        return contactInfoID;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public void setContactInfoID(String contactInfoID) {
        this.contactInfoID = contactInfoID;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
}



