/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datasheet;

/**
 *
 * @author Pooja
 */
public class ParseHTML {
    
    public String parseHTML(String HTMLstring) {
        
        String newHTMLstring = new String();
        StringBuilder newHTML = new StringBuilder();
        
        //This string should be added to the StringBuilder in the html body
        String redClassString = "<style>.red{color:red}</style>";
        
        System.out.println("This is a print statement to your console - look at the GlassFish Server tab below");
        System.out.println("redClassString: " + redClassString);
        
        String[] inputHTMLLines = HTMLstring.split("\n");
        for (String line : inputHTMLLines) {
            
            // HERE IS WHERE YOU WILL PERFORM OPERATIONS AS YOU PARSE THE INPUT HTML LINE-BY-LINE AND ADD TO newHTML StringBuilder
            
        }
        
        return newHTMLstring;
    }
    
}
