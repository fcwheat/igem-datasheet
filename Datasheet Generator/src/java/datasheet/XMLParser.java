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
        
        //parse XML and convert to String array
        StringBuilder jsonStringOUT = new StringBuilder();
        
        //initialize variables for relevant data
        String partNameString = "";
        String partSummaryString = "";
        String partTypeString = "";
        String partURLString = "";
        String partDateString = "";
        String partAuthorString = "";
        String seqDataString = "";
        String directionString = "";
        
        //find relevant info and set to variable as String
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
            if (line.contains("<part_url>")) {
                int start = line.indexOf("<part_url>") + 10;
                int end = line.indexOf("</part_url>");
                partURLString = jsonStringOUT.substring(start, end).replaceAll("\n", "").trim();
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
          if (line.contains("<direction>")) {
                int start = line.indexOf("<direction>") + 11;
                int end = line.indexOf("</direction>");
                directionString = jsonStringOUT.substring(start, end).replaceAll("\n", "").trim();
          }
        }

        //put all variables into a String array
        String[] partInfoStrArr = {partNameString, partSummaryString, 
            partTypeString, partURLString, partDateString, 
            partAuthorString, seqDataString, directionString};

        return partInfoStrArr;    //return String array
    }

    public static JSONArray writeJSONObject(String[] partInfoStrArr) throws JSONException {
        //take elements from array in String form and convert to JSON objects       
        
        //initialize all JSONObjects
        JSONObject partNameJSON;
        JSONObject partSummaryJSON;
        JSONObject partTypeJSON;        
        JSONObject partURLJSON;
        JSONObject partDateJSON;
        JSONObject partAuthorJSON;
        JSONObject seqDataJSON;
        JSONObject directionJSON;
                
        //make JSONObjects {string : value}
        partNameJSON = new JSONObject().put("partName", partInfoStrArr[0]);
        partSummaryJSON = new JSONObject().put("partSummary", partInfoStrArr[1]);       
        partTypeJSON = new JSONObject().put("partType", partInfoStrArr[2]);
        partURLJSON = new JSONObject().put("partURL", partInfoStrArr[3]);
        partDateJSON = new JSONObject().put("partDate", partInfoStrArr[4]);
        partAuthorJSON = new JSONObject().put("partAuthor", partInfoStrArr[5]);
        seqDataJSON = new JSONObject().put("seqData", partInfoStrArr[6]);
        directionJSON = new JSONObject().put("direction", partInfoStrArr[7]);

        //put each piece of info (JSONObject) into JSONArray
        JSONArray partInfoJSON = new JSONArray();
        partInfoJSON.put(partNameJSON);
        partInfoJSON.put(partSummaryJSON);
        partInfoJSON.put(partTypeJSON);
        partInfoJSON.put(partURLJSON);
        partInfoJSON.put(partDateJSON);
        partInfoJSON.put(partAuthorJSON);
        partInfoJSON.put(seqDataJSON);
        partInfoJSON.put(directionJSON);
        
        return partInfoJSON; //return JSONArray of JSONobjects of relevant info
    }
    
    public class CommunicationExampleServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
        
        protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        String data = request.getParameter("data");
        //print out data posted to server
        System.out.println(data.toString());
        }

        protected void processGetRequest(HttpServletRequest request, HttpServletResponse response, JSONArray partInfoJSON)
            throws ServletException, IOException, JSONException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        //create a new json object
        //JSONObject toReturn = new JSONObject();
        
        //add new key value pair to json object        
        //toReturn.put("message", "received from the server");
            
        //return the json object as a string
        //out.write(toReturn.toString());
        out.write(partInfoJSON.toString());
        
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response, JSONArray partInfoJSON)
            throws ServletException, IOException {
        try {
            processGetRequest(request, response, partInfoJSON);
        } catch (JSONException ex) {
            Logger.getLogger(CommunicationExampleServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    }
}
