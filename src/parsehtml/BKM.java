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
public final class BKM extends Parser {

    public BKM(String book_name) throws IOException
    {
        super(book_name);
        String url = "https://www.bkmkitap.com/arama?q=".concat(this.book_name);
        this.doc = Jsoup.connect(url).get();
    }
    
    public BKM(String book_name, String book_url) throws IOException
    {
        super(book_name);
        this.doc = Jsoup.connect(book_url).get();
    }

    @Override
    public String getBookURL() throws IOException {
        Elements e1 = doc.getElementsByClass("fl col-12 catalogWrapper").get(0).getElementsByClass("row drop-down-title").get(this.book_id).getElementsByClass("row").select("a[href]");
        return "https://www.bkmkitap.com" + e1.get(0).attr("href");
    }

    @Override
    public int getBookQty() throws IOException {
        int qty = doc.getElementsByClass("fl col-12 catalogWrapper").get(0).getElementsByClass("row drop-down-title").size();
        return qty;
    }

    /*@Override
    public String saveImage() throws MalformedURLException, IOException {
        URL url1 = new URL(this.getImageURL());
        String new_path = "C:/Users/mobyr/Desktop/book_images/bkm/" + this.book_name.replaceAll(" ", "_");
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
        Elements e1 = doc.getElementsByClass("fl col-12 catalogWrapper").get(0).getElementsByClass("row");
        return e1.get(this.book_id * 4).getElementsByTag("img").attr("src");
    }

    @Override
    public String getBookTitle() throws IOException {
        Elements e1 = doc.getElementsByClass("fl col-12 catalogWrapper").get(0).getElementsByClass("row drop-down-title").get(this.book_id).getElementsByClass("row").select("a[href]");
        return e1.get(0).text();
    }

    @Override
    public float getPrice() throws IOException {
        Elements e1 = doc.getElementsByClass("fl col-12 catalogWrapper").get(0).getElementsByClass("row drop-down-title");
        String total = e1.get(this.book_id).getElementsByClass("col col-12 currentPrice").text().replaceAll(",", ".").replaceAll(" TL", "");
        return Float.valueOf(total);
    }
    
    public float getPriceFromURL()
    {
        return Float.valueOf(doc.getElementsByAttributeValue("itemprop", "price").attr("content"));
    }
    
    @Override
    public float getShipmentPrice()
    {
        return 5.99f;
    }
    
    @Override
    public String toString()
    {
        return "BKM Kitap";
    }
}
