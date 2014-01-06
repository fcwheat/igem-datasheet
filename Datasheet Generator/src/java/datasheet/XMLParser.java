/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datasheet;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Pooja Shah
 */

public class XMLParser {

    public static void main(String[] args) throws JSONException {
        
        //There will be an arraylist of actual part names
        ArrayList<String> partNames = new ArrayList<String>();
        partNames.add("K1114000");
        
        //Get part XML pages from Parts Registry
        ArrayList<String> partXMLs = getXML(partNames);
        
        //Parse through XML pages for relevant info
        String[] parsedString = parseXML(partXMLs);
       
        //Write relevant info to JSON Object for client
        JSONArray partInfo = writeJSONObject(parsedString);

        //Test print statements for writeJSONObject
        System.out.println(partInfo);
    
    }

    public static ArrayList<String> getXML(ArrayList<String> partNames) {
        
        ArrayList<String> xmlStrings = new ArrayList<String>();
        
        //For each of the names provided, create a part Document
        //uses getPartDocument method 
        for (String name : partNames) { 
            Document partDocument = getPartDocument(name);
            xmlStrings.add(partDocument.toString());            
        }
        
        return xmlStrings;        
    }
    
    //given a part name, create a document object corresponding to the DOM
    public static Document getPartDocument(String partName) {
        
        Document partDoc;
        try {
            partDoc = Jsoup.connect("http://parts.igem.org/xml/part." + partName)
                    .timeout(10000000)
                    .parser(Parser.xmlParser())
                    .get();
            return partDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }
   
    public static String[] parseXML(ArrayList<String> XMLstring) {
        
        //parse XML and convert to String Builder
        StringBuilder jsonStringOUT = new StringBuilder();
        
        //initialize variables for relevant data
        String partNameString = "";
        String partSummaryString = "";
        String partTypeString = "";
        String partDateString = "";
        String partAuthorString = "";
        String seqDataString = "";
        
        //Parse XML, find relevant info, and set to variable as String
        for (String line : XMLstring) {
            if (line.contains("<part_name>")) {               
                int start = line.indexOf("<part_name>") + 11;
                int end = line.indexOf("</part_name>");
                jsonStringOUT.append(line);
                partNameString = jsonStringOUT.substring(start, end).replaceAll("\n", "").trim();
            }
            if (line.contains("<part_short_desc>")) {
                int start = line.indexOf("<part_short_desc>") + 17;
                int end = line.indexOf("</part_short_desc>");
                partSummaryString = jsonStringOUT.substring(start, end).replaceAll("\n", "").trim();
            } 
            if (line.contains("<part_type>")) {
                int start = line.indexOf("<part_type>") + 11;
                int end = line.indexOf("</part_type>");
                partTypeString = jsonStringOUT.substring(start, end).replaceAll("\n", "").trim();            
            } 
            if (line.contains("<part_entered>")) {
                int start = line.indexOf("<part_entered>") + 14;
                int end = line.indexOf("</part_entered>");
                partDateString = jsonStringOUT.substring(start, end).replaceAll("\n", "").trim();
            }
            if (line.contains("<part_author>")) {
                int start = line.indexOf("<part_author>") + 13;
                int end = line.indexOf("</part_author");
                partAuthorString = jsonStringOUT.substring(start, end).replaceAll("\n", "").trim();
            }
            if (line.contains("<seq_data>")) {
                int start = line.indexOf("<seq_data>") + 10;
                int end = line.indexOf("</seq_data");
                seqDataString = jsonStringOUT.substring(start, end).replaceAll("\n", "").trim();
            }
        }

        //put all variables into a String array
        String[] partInfoStrArr = {partNameString, partSummaryString, 
            partTypeString, partDateString, partAuthorString, seqDataString};

        return partInfoStrArr;    //return String array of relevant info
    }

    public static JSONArray writeJSONObject(String[] partInfoStrArr) throws JSONException {
        //take elements from array in String form and convert to JSON objects       
        
        //initialize all JSONObjects
        JSONObject name;
        JSONObject summary;
        JSONObject deviceType;        
        JSONObject date;
        JSONObject authors;
        JSONObject sequence;
                
        //make JSONObjects {string : value}
        name = new JSONObject().put("name", partInfoStrArr[0]);
        summary = new JSONObject().put("summary", partInfoStrArr[1]);       
        deviceType = new JSONObject().put("deviceType", partInfoStrArr[2]);
        date = new JSONObject().put("date", partInfoStrArr[3]);
        authors = new JSONObject().put("authors", partInfoStrArr[4]);
        sequence = new JSONObject().put("sequence", partInfoStrArr[5]);

        //put each piece of info (JSONObject) into JSONArray
        JSONArray partsInfoJSON = new JSONArray();
        partsInfoJSON.put(name);
        partsInfoJSON.put(summary);
        partsInfoJSON.put(deviceType);
        partsInfoJSON.put(date);
        partsInfoJSON.put(authors);
        partsInfoJSON.put(sequence);
        
        return partsInfoJSON; //return JSONArray of JSONobjects of relevant info
    }
    
//    public class CommunicationExampleServlet extends HttpServlet {
//
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//        
//        protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//        String data = request.getParameter("data");
//        //print out data posted to server
//        System.out.println(data.toString());
//        }
//
//        protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, JSONArray partsInfoJSON)
//            throws ServletException, IOException, JSONException {
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        
//        //create a new json object
//        //JSONObject toReturn = new JSONObject();
//        
//        //add new key value pair to json object        
//        //toReturn.put("message", "received from the server");
//            
//        //return the json object as a string
//        //out.write(toReturn.toString());
//        out.write(partsInfoJSON.toString());
//        
//        }
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response, JSONArray partsInfoJSON)
//            throws ServletException, IOException {
//        try {
//            processGetRequest(request, response, partsInfoJSON);
//        } catch (JSONException ex) {
//            Logger.getLogger(CommunicationExampleServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processPostRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//    
//    }
}
