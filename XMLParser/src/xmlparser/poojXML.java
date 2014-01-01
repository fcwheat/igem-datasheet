package xmlparser;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/**
 * @author Pooja Shah
 */
public class poojXML {

    public static void main(String[] args) throws JSONException {
        
        //There will be an arraylist of actual part names
        ArrayList<String> partNames = new ArrayList<String>();
        partNames.add("K1114107");
        
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
}
