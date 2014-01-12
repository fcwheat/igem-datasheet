package xmlparser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Pooja Shah
 */
public class poojHTML {

    public static void main() {
    //test functions here    
        
    }
    
    public static String[] parseHTML(String HTMLstring) {

        StringBuilder newHTML = new StringBuilder();
        String[] inputHTMLLines = HTMLstring.split("\n");

        for (String line : inputHTMLLines) {
            if (line.contains("http://")) {	//parse for links
		/*	pseudo code
		
                 //save links as images to directory...maybe?
                 File imgfile = new File(partImage + "." + ext);
                 BufferedImage partImage = toBufferedImage(file);
                 ImageIO.write(image, ext, imgfile);
		
                 newHTML.replace("http://...","C:\") //replace link with the directory path
                 */
            }
        }
        String[] newHTMLString = null;

        return newHTMLString;
    }
}


