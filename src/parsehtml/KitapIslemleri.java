/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsehtml;

import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author mobyr
 */
public final class KitapIslemleri {
    public String seller_name;
    public String book_name;
    public String book_title;
    public String book_url;
    public String book_img_path;
    public String book_img_url;
    public float book_price;
    public float shipping;
    public int book_id;
    
    public void addBooks(Parser[] sellers) throws IOException
    {
        for(Parser p: sellers)
        {
            if(p.getBookQty() < 5)
            {
                if(p.getBookQty() != 0)
                {
                    for (int i = 0; i < p.getBookQty(); i++) 
                    {
                        ArrayList book = new ArrayList();
                        p.book_id = i;
                        this.book_name = p.book_name;
                        this.book_title = p.getBookTitle();
                        if(this.book_title.toLowerCase().contains(this.book_name))
                        {
                            this.book_url = p.getBookURL();
                            this.book_img_url = p.getImageURL();
                            //this.book_img_path = p.saveImage();
                            this.book_price = p.getPrice();
                            this.book_id = p.book_id;
                            this.seller_name = p.toString();
                            this.shipping = p.getShipmentPrice();
                            book.add(book_title);
                            book.add(book_url);
                            book.add(book_img_url);
                            book.add(book_img_path);
                            book.add(book_price);
                            book.add(seller_name);
                            book.add(shipping);
                            Parser.getBooks().add(book);
                        }
                    }
                }
            }

            else
            {
                for (int i = 0; i < 5; i++) 
                {
                    ArrayList book = new ArrayList();
                    p.book_id = i;
                    this.book_name = p.book_name;
                    this.book_title = p.getBookTitle();
                    
                    if(this.book_title.toLowerCase().contains(this.book_name))
                    {
                        this.book_url = p.getBookURL();
                        this.book_img_url = p.getImageURL();
                        //this.book_img_path = p.saveImage();
                        try
                        {
                            this.book_price = p.getPrice();
                        }

                        catch(Exception e)
                        {
                            continue;
                        }
                        this.book_id = p.book_id;
                        this.seller_name = p.toString();
                        this.shipping = p.getShipmentPrice();
                        book.add(book_title);
                        book.add(book_url);
                        book.add(book_img_url);
                        book.add(book_img_path);
                        book.add(book_price);
                        book.add(seller_name);
                        book.add(shipping);
                        Parser.getBooks().add(book);
                    }
                }
            }   
        }
    }
}
