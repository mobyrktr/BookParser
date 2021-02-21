/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsehtml;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;

/**
 *
 * @author mobyr
 */
public final class TestSitesi extends Parser{

    public TestSitesi(String book_name) throws IOException {
        super(book_name);
        File input = new File("C:/Users/mobyr/Desktop/index.html");
        this.doc = Jsoup.parse(input, "UTF-8", "");
    }

    @Override
    public String getBookURL() throws IOException {
        return "file://C:/Users/mobyr/Desktop/index.html";
    }

    @Override
    public int getBookQty() throws IOException {
        return 1;
    }

    @Override
    public String getImageURL() throws IOException {
        return doc.getElementsByClass("book_img").attr("src");
    }

    @Override
    public String getBookTitle() throws IOException {
        return doc.getElementsByClass("title").text();
    }

    @Override
    public float getPrice() throws IOException {
        return Float.valueOf(doc.getElementsByClass("price").text());
    }

    @Override
    public float getShipmentPrice() throws IOException {
        return 5f;
    }
    
    @Override
    public String toString()
    {
        return "TestSitesi";
    }
    
}
