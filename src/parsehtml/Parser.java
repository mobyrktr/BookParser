/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsehtml;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.nodes.Document;



/**
 *
 * @author mobyr
 */
public abstract class Parser {
    public String book_name;
    public int book_id;
    public Document doc;
    private static ArrayList<ArrayList> books = new ArrayList();
    
    public Parser(String book_name)
    {
        this.book_name = book_name.toLowerCase();
    }
    
    
    public abstract String getBookURL() throws IOException;
    public abstract int getBookQty() throws IOException;
    //public abstract String saveImage() throws MalformedURLException, IOException;
    public abstract String getImageURL() throws IOException;
    public abstract String getBookTitle() throws IOException;
    public abstract float getPrice() throws IOException;
    public abstract float getShipmentPrice() throws IOException;

    public static ArrayList<ArrayList> getBooks() {
        return books;
    }
    
}
