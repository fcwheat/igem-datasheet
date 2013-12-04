package xmlparser;

import java.util.ArrayList;
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
 *
 * @author Pooja Shah
 */
public class poojXML {

    public static String parseXML(String XMLstring) {
    //parse XML and convert to JSON object

        StringBuilder newXML = new StringBuilder();

        //parse XML, turn into string
        String[] inputXMLLines = XMLstring.split("\n");
        for (String line : inputXMLLines) {
            newXML.append(line);
        }
        String newXMLString = newXML.toString();
        
        JSONObject jsonObject = null;
        try {
            //convert string to JSON (I think I made an array of JSON objects)
            jsonObject = new JSONObject();
        } catch (JSONException ex) {
            Logger.getLogger(poojXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("locations");
        } catch (JSONException ex) {
            Logger.getLogger(poojXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject explrObject = jsonArray.getJSONObject(i);
            } catch (JSONException ex) {
                Logger.getLogger(poojXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return newXMLString;
    }
}
