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
        partNames.add("J23100");
        partNames.add("I13453");
        partNames.add("B0033");
        partNames.add("B0034");
        partNames.add("E1010");
        partNames.add("E0040");
        partNames.add("E0030");
        partNames.add("B0015");
        
        //Get part XML pages from Parts Registry
        ArrayList<String> partXMLs = getXML(partNames);
        
        //Parse through XML pages for relevant info
        String[] parsedString = parseXML(partXMLs);
        
        //Write relevant info to JSON Object for client
        JSONArray partInfo = writeJSONObject(parsedString);

        //Test print statements for writeJSONObject
        System.out.println(partInfo);

}

    public static String[] parseXML(ArrayList<String> partXMLs) {
        //go through ArrayList and pull out each string?
        
        //parse XML and convert to String array
        StringBuilder jsonStringOUT = new StringBuilder();
        String[] inputXMLLines = XMLstring.split("\n");

        //initialize variables for relevant data
        String partNameString = "";
        String partSummaryString = "";
        String partTypeString = "";
        String partURLString = "";
        String partDateString = "";
        String partAuthorString = "";
        String seqDataString = "";
        String directionString = "";
        String cellType = "";

        //find relevant info and set to variable as String
        for (String line : inputXMLLines) {
            if (line.contains("<part_name>")) {
                int start = line.indexOf(">") + 1;
                int end = line.indexOf("</part_name>");
                jsonStringOUT.append(line);
                partNameString = jsonStringOUT.substring(start, end);
            } else if (line.contains("<part_short_desc>")) {
                jsonStringOUT.replace(0, 1000, line);
                int start = line.indexOf(">") + 1;
                int end = line.indexOf("</");
                partSummaryString = jsonStringOUT.substring(start, end);
            } else if (line.contains("<part_type>")) {
                jsonStringOUT.replace(0, 1000, line);
                int start = line.indexOf(">") + 1;
                int end = line.indexOf("</");
                partTypeString = jsonStringOUT.substring(start, end);
            } else if (line.contains("<part_url>")) {
                jsonStringOUT.replace(0, 1000, line);
                int start = line.indexOf(">") + 1;
                int end = line.indexOf("</");
                partURLString = jsonStringOUT.substring(start, end);
            } else if (line.contains("<part_entered>")) {
                jsonStringOUT.replace(0, 1000, line);
                int start = line.indexOf(">") + 1;
                int end = line.indexOf("</");
                partDateString = jsonStringOUT.substring(start, end);
            } else if (line.contains("<part_author>")) {
                jsonStringOUT.replace(0, 1000, line);
                int start = line.indexOf(">") + 1;
                int end = line.indexOf("</");
                partAuthorString = jsonStringOUT.substring(start, end);
            } else if (line.contains("<seq_data>")) {
                jsonStringOUT.replace(0, 1000, line);
                int start = line.indexOf(">") + 1;
                int end = line.indexOf("</");
                seqDataString = jsonStringOUT.substring(start, end);
            } else if (line.contains("<direction>")) {
                jsonStringOUT.replace(0, 1000, line);
                int start = line.indexOf(">") + 1;
                int end = line.indexOf("</");
                directionString = jsonStringOUT.substring(start, end);
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
    
    public static ArrayList<String> getXML(ArrayList<String> partNames) {
        
        ArrayList<String> xmlStrings = new ArrayList<String>();
        
        //For each of the names provided,
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
        } 
        
        catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
        
        finally {
        }
    }
}
