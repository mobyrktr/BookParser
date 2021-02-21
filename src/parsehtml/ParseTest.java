/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsehtml;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author mobyr
 */
public class ParseTest {
    public static void main(String[] args) throws IOException {
        
        String url = ("https://www.bkmkitap.com/kurk-mantolu-madonna");
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc.getElementsByAttributeValue("itemprop", "price").attr("content"));
    }
}
