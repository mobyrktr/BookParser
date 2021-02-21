/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsehtml;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

/**
 *
 * @author mobyr
 */
public final class Amazon extends Parser {
    
    public Amazon(String book_name) throws IOException
    {
        super(book_name);
        String url = "https://www.amazon.com.tr/s?k=".concat(this.book_name) + "&rh=n%3A12466380031&dc";
        this.doc = Jsoup.connect(url).get();
    }
    
    public Amazon(String book_name, String book_url) throws IOException
    {
        super(book_name);
        this.doc = Jsoup.connect(book_url).get();
    }
    
    @Override
    public String getBookURL() throws IOException {
        Elements el = doc.getElementsByAttribute("data-index").get(this.book_id).getElementsByTag("a");
        return "https://www.amazon.com.tr" + el.get(0).attr("href");
    }

    @Override
    public int getBookQty() throws IOException {
        int qty = doc.getElementsByAttribute("data-index").size();
        return qty;
    }

    @Override
    public String getImageURL() throws IOException {
        Elements e1 = doc.getElementsByAttribute("data-index").get(this.book_id).getElementsByTag("img");
        return e1.attr("src");
    }

    @Override
    public String getBookTitle() throws IOException {
        Elements e1 = doc.getElementsByAttribute("data-index").get(0).getElementsByTag("img");
        return e1.attr("alt");
    }

    @Override
    public float getPrice() throws IOException {
        Elements e1 = doc.getElementsByAttribute("data-index").get(this.book_id).getElementsByClass("a-offscreen");
        String s = e1.get(0).text().substring(1).replace(',', '.');
        return Float.valueOf(s);
    }
    
    public float getPriceFromURL()
    {
        return Float.valueOf(doc.getElementsByClass("offer-price").text().replace(',', '.').replaceFirst(" TL", ""));
    }
    
    @Override
    public float getShipmentPrice() throws IOException
    {
        float price = this.getPrice();
        if(price > 50)
            return 0;
        
        else
            return 4.90f;
    }
    
    @Override
    public String toString()
    {
        return "Amazon";
    }
}
