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
public final class Hepsiburada extends Parser{
    
    public Hepsiburada(String book_name) throws IOException
    {
        super(book_name);
        String url = "https://www.hepsiburada.com/ara?q=".concat(this.book_name) + "&kategori=60001501";
        this.doc = Jsoup.connect(url).get();
    }


    @Override
    public String getBookURL() throws IOException {
        Elements e1 = doc.getElementsByClass("with-hover big-box").get(0).getElementsByTag("li").get(this.book_id).getElementsByClass("box product  hb-placeholder").select("a[href]");
        
        return "https://www.hepsiburada.com" + e1.attr("href");
    }

    @Override
    public int getBookQty() throws IOException {
        int qty = doc.getElementsByClass("with-hover big-box").get(0).getElementsByTag("h3").size();
        
        if(qty == 1)
        {
            return 0;
        }
        
        return qty;
    }

    /*@Override
    public String saveImage() throws MalformedURLException, IOException {
        URL url1 = new URL(this.getImageURL());
        String new_path = "C:/Users/mobyr/Desktop/book_images/hepsiburada/" + this.book_name.replaceAll(" ", "_");
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
        Elements e1 = doc.getElementsByClass("with-hover big-box").get(0).getElementsByTag("li").get(this.book_id).getElementsByClass("box product  hb-placeholder").select("a[href]");
        String link = e1.get(0).getElementsByClass("carousel-lazy-item").select("img").attr("data-src");
        return link;
    }

    @Override
    public String getBookTitle() throws IOException {
        String title = "";
        try
        {
            Elements e1 = doc.getElementsByClass("with-hover big-box").get(0).getElementsByTag("li").get(this.book_id).getElementsByClass("box product  hb-placeholder").select("a[href]");
            title = e1.get(0).getElementsByClass("product-detail").select("h3").attr("title");
        }
        catch(Exception e)
        {
            
        }
        return title;
    }

    @Override
    public float getPrice() throws IOException {
        Elements e1 = doc.getElementsByClass("with-hover big-box").get(0).getElementsByTag("li").get(this.book_id).getElementsByClass("box product  hb-placeholder").select("a[href]");
        String s = "";
        float total = 0;
        
        try
        {
            s = e1.get(0).getElementsByClass("product-detail").get(0).getElementsByClass("price product-price").text();
            if(s.isEmpty())
            {
                throw new Exception("manyak çocuk");
            }
        }
        catch(Exception e)
        {
            s = e1.get(0).getElementsByClass("product-detail").get(0).getElementsByClass("price-value").text();
        }
        
        finally
        {
           total = Float.valueOf(s.replaceAll(" TL", "").replaceAll(",", "."));
        }
        return total;
    }
    
    @Override
    public float getShipmentPrice() throws IOException
    {
        float price = this.getPrice();
        if(price > 74)
        {
            return 0;
        }
        
        else
        {
            return 8.99f;
        }
    }
    
    @Override
    public String toString()
    {
        return "Hepsiburada";
    }
}
