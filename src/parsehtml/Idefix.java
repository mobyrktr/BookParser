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
public final class Idefix extends Parser{

    public Idefix(String book_name) throws IOException
    {
        super(book_name);
        String url = "https://www.idefix.com/search/?ActiveCategoryId=11693&Q=".concat(this.book_name);
        this.doc = Jsoup.connect(url).get();
    }
    
    public Idefix(String book_name, String book_url) throws IOException
    {
        super(book_name);
        this.doc = Jsoup.connect(book_url).get();
    }

    @Override
    public String getBookURL() throws IOException {
        Elements e1 = doc.getElementsByClass("shelf").get(0).getElementsByClass("product-info").get(this.book_id).getElementsByClass("box-title").select("a[href]");
        String link = "https://www.idefix.com" + e1.attr("href");
        
        return link;
    }

    @Override
    public int getBookQty() throws IOException {
        try
        {
            int size = doc.getElementsByClass("shelf").get(0).getElementsByClass("product-info").size();
            return size;
        }
        
        catch(IndexOutOfBoundsException e)
        {
            
        }
        
        return 0;
    }

    /*@Override
    public String saveImage() throws MalformedURLException, IOException {
        URL url1 = new URL(this.getImageURL());
        String new_path = "C:/Users/mobyr/Desktop/book_images/idefix/" + this.book_name.replaceAll(" ", "_");
        String image_path = new_path +  "/image_" + String.valueOf(book_id) + ".jpg";
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
        String link = doc.getElementsByClass("shelf").get(0).select("img").get(this.book_id).attr("data-src").replaceAll("150x242-0", "500x400-0");
        
        return link;
    }

    @Override
    public String getBookTitle() throws IOException {
        String title = doc.getElementsByClass("shelf").get(0).getElementsByClass("product-info").get(this.book_id).getElementsByClass("box-title").select("a").attr("title");
        
        return title;
    }

    @Override
    public float getPrice() throws IOException {
        String str = doc.getElementsByClass("shelf").get(0).getElementsByClass("product-info").get(this.book_id).getElementsByClass("box-line-4").select("span").attr("data-price").replaceAll(",", ".");
        
        return Float.valueOf(str);
    }
    
    public Float getPriceFromURL()
    {
        return Float.valueOf(doc.getElementById("salePrice").text().replace(',', '.').replaceFirst(" TL", ""));
    }
    
    @Override
    public float getShipmentPrice() throws IOException
    {
        return 8.0f;
    }

    public String getBook_name() {
        return book_name;
    }
    
    @Override
    public String toString()
    {
        return "Idefix";
    }
}
