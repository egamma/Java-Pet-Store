/* Copyright 2006 Sun Microsystems, Inc. All rights reserved. You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at: http://developer.sun.com/berkeley_license.html
$Id: AutocompleteBean.java,v 1.7 2006-09-20 23:29:34 basler Exp $ */

package com.sun.javaee.blueprints.petstore.controller;

import javax.faces.context.FacesContext;
import com.sun.j2ee.blueprints.ui.autocomplete.CompletionResult;
import com.sun.javaee.blueprints.petstore.model.CatalogFacade;
import com.sun.javaee.blueprints.petstore.model.ZipLocation;
import java.util.List;
import java.util.Map;

public class AutocompleteBean {
    
    static final boolean bDebug=false;
    private static final int ITEMCOUNT = 20;
    private String[] cities = null;
    private String[] zips = null;
    private String[] states = null;
    private CatalogFacade catalogFacade = null;
    
    /** Creates a new instance of AutocompleteBean */
    public AutocompleteBean() {
        FacesContext context=FacesContext.getCurrentInstance();
        Map<String, Object> contextMap = context.getExternalContext().getApplicationMap();
        this.catalogFacade = (CatalogFacade)contextMap.get("CatalogFacade");
        //initCities("a", ITEMCOUNT);
    }
    
    /**
     * right now, it's always accessing the DB for every request.
     * needs to install cache mechanism later
     * returns a decoded str (ex. %20 -> " ")
     */
    private String initCities(String prefix, int itemCount) {
        String decodedStr = null;
        try {
            decodedStr = java.net.URLDecoder.decode(prefix, "UTF-8");
        } catch (Exception e) {
            if (bDebug) System.out.println("URLdecode failed");
        }
        if (decodedStr == null) {
            decodedStr = "a";
        }
        // fetch records
        List<ZipLocation> zipLocations = catalogFacade.getZipCodeLocations(decodedStr, 0, itemCount);
        
        if (zipLocations == null) {
            cities =new String[]{"Init Failed"};
            zips =new String[]{"Init Failed"};
            states =new String[]{"Init Failed"};
            return null;
        }
        
        cities = new String[itemCount];
        zips = new String[itemCount];
        states = new String[itemCount];
        int i=0;
        for (ZipLocation zl : zipLocations) {
            cities[i] = zl.getCity()+", "+zl.getState()+" "+Integer.toString(zl.getZipCode());
            zips[i] = Integer.toString(zl.getZipCode());
            states[i] = zl.getState();
            i++;
        }
        return decodedStr;
    }
    
    public String[] getCities() {
        return cities;
    }
    
    public String[] getZips() {
        return zips;
    }
    
    public String[] getStates() {
        return states;
    }
    
    public void completeCity(FacesContext context, String prefix, CompletionResult result) {
        if (bDebug) {
            System.out.println("Completing City - " + prefix);
            System.out.println("first city : " + cities[0]);
        }
        if (prefix == null || prefix.equals("")) {
            prefix = "a";
        }
        initCities(prefix, ITEMCOUNT);
        //String decodedStr = initCities(prefix, ITEMCOUNT);
        // add items directly rather than calling autocompleteutilities
        //if (decodedStr == null) decodedStr = "a";
        //AutoCompleteUtilities.addMatchingItems(cities, decodedStr, result);
        
        result.addItems(cities);
    }
    
    public void completeState(FacesContext context, String prefix, CompletionResult result) {
        if (bDebug) System.out.println("Completing State - " + prefix);
        if (prefix == null || prefix.equals("")) {
            prefix = "a";
        }
        initCities(prefix, ITEMCOUNT);
        //String decodedStr = initCities(prefix, ITEMCOUNT);
        // add items directly rather than calling autocompleteutilities
        //if (decodedStr == null) decodedStr = "a";
        //AutoCompleteUtilities.addMatchingItems(states, decodedStr, result);
        
        result.addItems(states);
    }
}
