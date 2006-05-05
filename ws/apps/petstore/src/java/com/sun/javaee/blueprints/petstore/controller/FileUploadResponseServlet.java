/* Copyright 2006 Sun Microsystems, Inc. All rights reserved. You may not modify, use, reproduce, or distribute this software except in compliance with the terms of the License at: http://developer.sun.com/berkeley_license.html
$Id: FileUploadResponseServlet.java,v 1.5 2006-05-05 20:15:23 inder Exp $ */

package com.sun.javaee.blueprints.petstore.controller;


import com.sun.javaee.blueprints.petstore.model.FileUploadResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FileUploadResponseServlet extends HttpServlet {
    
    private static final String FILE_UL_RESPONSE = "fileuploadResponse";
    
    private String constructJsonEntry(String key, String value) {
        String dq = "\"";
        return (key + " : " + dq + value + dq);
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        // xml or json
        String reqContentType = request.getParameter("reqContentType");
        
        // there must be a session already, but just in case.
        HttpSession session = request.getSession(true);
        
        Boolean cInvalid = (Boolean)session.getAttribute("captchaInvalid");
        if (cInvalid == null) {
            cInvalid = new Boolean(false);
        }
        
        if (reqContentType!=null && reqContentType.equals("xml")) {
            response.setContentType("text/xml;charset=UTF-8");
        } else {
            response.setContentType("text/javascript");
        }
        response.setHeader("Pragma", "No-Cache");
        response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
        response.setDateHeader("Expires", 1);
        PrintWriter writer = response.getWriter();
        
        StringBuffer sb=new StringBuffer();
        if (cInvalid.booleanValue()) {
            // captcha was invalid
            response.setStatus(response.SC_UNAUTHORIZED);
            if (reqContentType!=null && reqContentType.equals("xml")) {
                sb.append("<response>");
                sb.append("<message>");
                sb.append("Plase enter the correct captcha string");
                sb.append("</message>");
                sb.append("</response>");
            } else {
                sb.append("{");
                sb.append(constructJsonEntry("message", "Please enter the correct captcha string"));
                sb.append("}");
            }
        } else {
            // captcha was valid
            FileUploadResponse flr = (FileUploadResponse)session.getAttribute(FILE_UL_RESPONSE);
            if (flr != null) {
                if (reqContentType!=null && reqContentType.equals("xml")) {
                    sb.append("<response>");
                    sb.append("<itemid>");
                    sb.append(flr.getItemId());
                    sb.append("</itemid>");
                    sb.append("<productId>");
                    sb.append(flr.getProductId());
                    sb.append("</productId>");
                    sb.append("<message>");
                    sb.append(flr.getMessage());
                    sb.append("</message>");
                    sb.append("<status>");
                    sb.append(flr.getStatus());
                    sb.append("</status>");
                    sb.append("<duration>");
                    sb.append(flr.getDuration());
                    sb.append("</duration>");
                    sb.append("<duration_string>");
                    sb.append(flr.getDurationString());
                    sb.append("</duration_string>");
                    sb.append("<start_date>");
                    sb.append(flr.getStartDate());
                    sb.append("</start_date>");
                    sb.append("<end_date>");
                    sb.append(flr.getEndDate());
                    sb.append("</end_date>");
                    sb.append("<upload_size>");
                    sb.append(flr.getUploadSize());
                    sb.append("</upload_size>");
                    sb.append("<thumbnail>");
                    sb.append(flr.getThumbnail());
                    sb.append("</thumbnail>");
                    sb.append("</response>");
                } else {
                    sb.append("{");
                    sb.append(constructJsonEntry("itemid", flr.getItemId()) + ",\n");
                    sb.append(constructJsonEntry("productid", flr.getProductId()) + ",\n");
                    sb.append(constructJsonEntry("message", flr.getMessage()) + ",\n");
                    sb.append(constructJsonEntry("status", flr.getStatus()) + ",\n");
                    sb.append(constructJsonEntry("duration", flr.getDuration()) + ",\n");
                    sb.append(constructJsonEntry("duration_string", flr.getDurationString()) + ",\n");
                    sb.append(constructJsonEntry("start_date", flr.getStartDate()) + ",\n");
                    sb.append(constructJsonEntry("end_date", flr.getEndDate()) + ",\n");
                    sb.append(constructJsonEntry("upload_size", flr.getUploadSize()) + ",\n");
                    sb.append(constructJsonEntry("thumbnail", flr.getThumbnail()));
                    sb.append("}");
                }
                
            }
        }
        try {
            writer.write(sb.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
