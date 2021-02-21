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
public final class KitapYurdu extends Parser{
    
    public KitapYurdu(String book_name) throws IOException
    {
        super(book_name);
        String url = "https://www.kitapyurdu.com/index.php?route=product/search&filter_name=".concat(this.book_name);
        this.doc = Jsoup.connect(url).get();
    }
    
    public KitapYurdu(String book_name, String book_url) throws IOException
    {
        super(book_name);
        this.doc = Jsoup.connect(book_url).get();
    }
    
    @Override
    public String getBookURL() throws IOException
    {
        Elements li = doc.getElementsByClass("box-content");
        
        String link = li.get(0).getElementsByClass("cover").get(this.book_id).getElementsByTag("a").attr("href");
        return link.split("&")[0];
    }
    

    @Override
    public int getBookQty() throws IOException
    {
        try
        {
        Elements li = doc.getElementsByClass("box-content");
        
        int book_qty = li.get(0).getElementsByClass("cover").size();
        return book_qty;
        }
        
        catch(IndexOutOfBoundsException e)
        {
            
        }
        return 0;
    }
    
    /*@Override
    public String saveImage() throws MalformedURLException, IOException
    {
        URL url1 = new URL(this.getImageURL());
        String new_path = "C:/Users/mobyr/Desktop/book_images/kitapyurdu/" + this.book_name.replaceAll(" ", "_");
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
    public String getImageURL() throws IOException
    {
        Elements li = doc.getElementsByClass("box-content").get(0).getElementsByClass("cover");
        
        String img_url = li.get(this.book_id).select("img[src]").attr("src").split("&")[0];
        
        return img_url;
    }
   
    @Override
    public String getBookTitle() throws IOException
    {
        Elements li = doc.getElementsByClass("box-content").get(0).getElementsByClass("cover");
        
        String title = li.get(this.book_id).select("img[src]").attr("alt");
        return title;
    }
    
    @Override
    public float getPrice() throws IOException {
        float total = Float.valueOf(doc.getElementsByClass("box-content").get(0).getElementsByClass("price-new").get(this.book_id).getElementsByClass("value").text().replaceAll(",", "."));
        return total;
    }
    
    @Override
    public float getShipmentPrice() throws IOException
    {
        float price = this.getPrice();
        
        if(price > 75)
        {
            return 0;
        }
        
        else if(price > 45)
        {
            return 2;
        }
        
        return 8.45f; // Fiyat 40'ın altındaysa site kontrol edilmeli.
    }
    
    @Override
    public String toString()
    {
        return "Kitap Yurdu";
    }
}
