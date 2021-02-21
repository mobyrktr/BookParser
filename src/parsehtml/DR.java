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
public final class DR extends Parser {

    public DR(String book_name) throws IOException
    {
        super(book_name);
        String url = "https://www.dr.com.tr/search?q=".concat(this.book_name) + "&cat=0%2C10001&parentId=10001";
        this.doc = Jsoup.connect(url).get();
    }
    
    public DR(String book_name, String book_url) throws IOException
    {
        super(book_name);
        this.doc = Jsoup.connect(book_url).get();
    }
    
    @Override
    public String getBookURL() throws IOException {
        Elements e1 = doc.getElementsByTag("section").get(0).getElementsByClass("list-cell").get(this.book_id).getElementsByTag("a");
        String link = "https://www.dr.com.tr" + e1.attr("href");
        
        return link;
    }

    @Override
    public int getBookQty() throws IOException {
        Elements e1 = doc.getElementsByTag("section").get(0).getElementsByClass("list-cell");
        int qty = e1.size();
        
        return qty;
    }

    /*@Override
    public String saveImage() throws MalformedURLException, IOException {
        URL url1 = new URL(this.getImageURL());
        String new_path = "C:/Users/mobyr/Desktop/book_images/dr/" + this.book_name.replaceAll(" ", "_");
        String image_path = new_path +  "/image_" + String.valueOf(this.book_id) + ".jpg";
        try(InputStream in = url1.openStream())
        {
            File file = new File(new_path);
            file.mkdir();
            Files.copy(in, Paths.get(image_path));
        }
        return image_path;
    }*/

    @Override
    public String getImageURL() throws IOException {
        Elements e1 = doc.getElementsByTag("section").get(0).getElementsByClass("list-cell").get(this.book_id).getElementsByTag("a").select("img[src]");
        String link = e1.attr("src").replaceAll("136x136-0", "500x400-0");
        return link;
    }

    @Override
    public String getBookTitle() throws IOException {
        Elements e1 = doc.getElementsByTag("section").get(0).getElementsByClass("list-cell").get(this.book_id).getElementsByTag("a").select("h3");
        String title = e1.text();
        return title;
    }
    
    @Override
    public float getPrice() throws IOException {
        Elements e1 = doc.getElementsByTag("section").get(0).getElementsByClass("list-cell").get(this.book_id).getElementsByClass("prices").select("span");
        float total = Float.valueOf(e1.get(1).text().replaceAll(" TL", "").replaceAll(",", "."));
        
        return total;
    }
    
    public float getPriceFromURL()
    {
        Elements el = doc.getElementsByClass("product-price").get(0).getElementsByTag("p");
        return Float.valueOf(el.text().trim().substring(12).replaceAll(" ₺", "").replace(',', '.'));
    }
    
    @Override
    public float getShipmentPrice() throws IOException
    {
        float price = this.getPrice();
        
        if(price > 99)
            return 0;
        
        else if(price > 50 && price < 99)
            return 4.99f;
        
        else if(price < 50)
            return 7.99f;
        
        return -1;
    }
    
    @Override
    public String toString()
    {
        return "D&R";
    }
}
