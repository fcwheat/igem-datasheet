/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datasheet;

import org.json.JSONObject;

/**
 *
 * @author Pooja
 * This class parses data from the client 
 */
public class DataSheetHTML {

    /** This method is a practice method for parsing html and adding the red class to all of the elements **/
    public static String parseHTML(String HTMLstring) {

//        String newHTMLstring;
        StringBuilder newHTML = new StringBuilder();

        //This string should be added to the StringBuilder in the html body
        //note this line must be enclosed within the body and head elements - google html to see what I mean
        String redClassString = "<style>.red{color:red}</style>";

        //System.out.println("This is a print statement to your console - look at the GlassFish Server tab below");
        //System.out.println("redClassString: " + redClassString);

        //System.out.println("HTMLstring: " + HTMLstring);

        String[] inputHTMLLines = HTMLstring.split("\n");
        for (String line : inputHTMLLines) {
            // HERE IS WHERE YOU WILL PERFORM OPERATIONS AS YOU PARSE THE INPUT HTML LINE-BY-LINE AND ADD TO newHTML StringBuilder
            newHTML.append(line);
         
            //if a line contains class, add the red class
            if (line.contains("class=\"")) {
                line = line.replaceAll("class=\"","class=\"red ");
                newHTML.append(line);       
            }
			
            //find index of <body>
//            int bodyIndex = line.indexOf("<body>");
            //add red class code after the <body>...i think
//            newHTML.insert(bodyIndex + 1, "\n\n" + redClassString + "\n\n");            
            
            if (line.contains("<body>")) {
                newHTML.append("\n\n").append(redClassString).append("\n\n");
            }
            
            //check print statement (prints lines multiple times?)
//            System.out.println("check: " + newHTMLstring);    
        }

        //this string will now be returned to the servlet, which will then return it to the web client
        String newHTMLstring = newHTML.toString();
        
        System.out.println("newHTMLstring: " + newHTMLstring);
        
        return newHTMLstring;
    }
    
    /** This method parses HTML from the client side of the data sheet app **/
    public static JSONObject parseDataSheetHTML(String HTMLstring) {
        return null;
    }
    
}
