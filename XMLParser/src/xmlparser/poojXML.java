package xmlparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Pooja Shah
 */
public class poojXML {

    public static void main(String[] args) throws JSONException {
        //call parseXML function
        
        //There will be an arraylist of actual part names
        ArrayList<String> partNames = new ArrayList<String>();
        partNames.add("B0034");
        
        ArrayList<String> partXMLs = getXML(partNames);
        
        String[] parsedString = parseXML("<!--\n" +
"Parts from the iGEM Registry of Standard Biological Parts\n" +
"-->\n" +
"<rsbpml>\n" +
"<part_list>\n" +
"<part>\n" +
"<part_id>151</part_id>\n" +
"<part_name>BBa_B0034</part_name>\n" +
"<part_short_name>B0034</part_short_name>\n" +
"<part_short_desc>RBS (Elowitz 1999) -- defines RBS efficiency</part_short_desc>\n" +
"<part_type>RBS</part_type>\n" +
"<release_status>Released HQ 2013</release_status>\n" +
"<sample_status>In stock</sample_status>\n" +
"<part_results>Works</part_results>\n" +
"<part_nickname/>\n" +
"<part_rating>1</part_rating>\n" +
"<part_url>http://parts.igem.org/Part:BBa_B0034</part_url>\n" +
"<part_entered>2003-01-31</part_entered>\n" +
"<part_author>Vinay S Mahajan, Voichita D. Marinescu, Brian Chow, Alexander D Wissner-Gross and Peter Carr IAP, 2003</part_author>\n" +   
"<deep_subparts/>\n" +
"<specified_subparts/>\n" +
"<specified_subscars/>\n" +
"<sequences>\n" +
"<seq_data>aaagaggagaaa</seq_data>\n" +
"</sequences>\n" +
"<features>\n" +
"<feature>\n" +
"<id>23325</id>\n" +
"<title/>\n" +
"<type>conserved</type>\n" +
"<direction>forward</direction>\n" +
"<startpos>5</startpos>\n" +
"<endpos>8</endpos>\n" +
"</feature>\n" +
"</features>\n" +
"<parameters>\n" +
"<!--\n" +
" NOTE: Currently, each parameter name can have only one value.\n" +
"-->\n" +
"<!--\n" +
"This will change as we fully support the context of a parameter. RDR 4/2010 \n" +
"-->\n" +
"<parameter>\n" +
"<name>efficiency</name>\n" +
"<value>1</value>\n" +
"<units/>\n" +
"<url/>\n" +
"<id>2480</id>\n" +
"<m_date>2008-11-29 13:15:14</m_date>\n" +
"<user_id>24</user_id>\n" +
"<user_name>registry</user_name>\n" +
"</parameter>\n" +
"<parameter>\n" +
"<name>biology</name>\n" +
"<value>NA</value>\n" +
"<units/>\n" +
"<url/>\n" +
"<id>3314</id>\n" +
"<m_date>2008-11-29 13:15:14</m_date>\n" +
"<user_id>24</user_id>\n" +
"<user_name>registry</user_name>\n" +
"</parameter>\n" +
"</parameters>\n" +
"<categories>\n" +
"<category>//chassis/prokaryote/ecoli</category>\n" +
"<category>//direction/forward</category>\n" +
"<category>//function/coliroid</category>\n" +
"<category>//rbs/prokaryote/constitutive/community</category>\n" +
"<category>//regulation/constitutive</category>\n" +
"<category>//ribosome/prokaryote/ecoli</category>\n" +
"</categories>\n" +
"<twins>\n" +
"<twin>BBa_J34801</twin>\n" +
"<twin>BBa_J70591</twin>\n" +
"<twin>BBa_K773001</twin>\n" +
"<twin>BBa_K783051</twin>\n" +
"</twins>\n" +
"<samples>\n" +
"<!-- Samples have been turned off for now - rdr 2013 -->\n" +
"</samples>\n" +
"<references>\n" +
"<!-- References are not available yet - rdr 2013 -->\n" +
"</references>\n" +
"<groups>\n" +
"<!--\n" +
" Group access information is not yet available - rdr 2013\n" +
"-->\n" +
"</groups>\n" +
"</part>\n" +
"</part_list>\n" +
"</rsbpml>");

        
        //implement writeJSONObject
        JSONArray partInfo = writeJSONObject(parsedString);

        //test print statements for writeJSONObject
        System.out.println(partInfo);

}

    public static String[] parseXML(String XMLstring) {
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
        String URLprefix = "http://parts.igem.org/cgi/xml/part.cgi?part=BBa_";
        
        //For each of the names provided,
        for (String name : partNames) {
            
            String XMLstring = new String();
            String URL = URLprefix + name;
            
            //GET THE XML TEXT FROM THE URL
            
            
            xmlStrings.add(XMLstring);            
        }
        
        return xmlStrings;        
    }
}
