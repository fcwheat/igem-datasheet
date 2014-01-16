/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class AuthenticationServlet extends HttpServlet {

    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        initPasswordHash(); //initialize passwords and users
        try {
            //get the user and password values from the incoming json object
            String user = request.getParameter("user");
            String password = request.getParameter("password");
            if (passwordHash.containsKey(user) && password.equals(passwordHash.get(user))) {
                //if the password matches, return a cookie with an authenticated value
                
                //create a cookie
                Cookie authenticateCookie = new Cookie("authenticate", "authenticated");
                //set the values of the cookie
                Cookie userCookie = new Cookie("user", user);
                authenticateCookie.setMaxAge(60 * 60); //cookie lasts for an hour
                //add the cookie to the response
                response.addCookie(authenticateCookie);
                response.addCookie(userCookie);
                //redirect client to the same page
                response.sendRedirect("index.html");
            } else {
                //if the password matches, return a cookie with a failed authentication value
                Cookie authenticateCookie = new Cookie("authenticate", "failed");
                authenticateCookie.setMaxAge(60 * 60); //cookie lasts for an hour
                response.addCookie(authenticateCookie);
                response.sendRedirect("index.html");
            }
        } finally {
            out.close();
        }
    }

    /**
     * Processes requests for HTTP <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect("index.html");
        PrintWriter out = response.getWriter();
        try {
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processGetRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processPostRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void initPasswordHash() {
        if (passwordHash == null) {
            passwordHash = new HashMap();
            String filePath = this.getServletContext().getRealPath("/") + "/WEB-INF/restricted/";
            File[] filesInDirectory = new File(filePath).listFiles();
            for (File currentFile : filesInDirectory) {
                String path = currentFile.getAbsolutePath();
                String fileExtension = path.substring(path.lastIndexOf(".") + 1, path.length()).toLowerCase();
                if ("txt".equals(fileExtension)) {
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(currentFile.getAbsolutePath()));
                        String line = reader.readLine();
                        while (line != null) {
                            if (!line.contains("#")) {
                                String[] tokens = line.split(",");
                                passwordHash.put(tokens[0], tokens[1]);
                            }
                            line = reader.readLine();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            reader.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            }
        }
    }
    HashMap<String, String> passwordHash;
}
