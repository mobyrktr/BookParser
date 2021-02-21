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
public class Test {
    
    public static void main(String[] args) throws IOException {
        KitapIslemleri k = new KitapIslemleri();
        String book_name = "şeker portakalı";
        Parser[] sellers = {new DR(book_name), new KitapYurdu(book_name), new Idefix(book_name), new Hepsiburada(book_name), new BKM(book_name), new Amazon(book_name)};
        k.addBooks(sellers);
        for(ArrayList book: Parser.getBooks())
        {
            System.out.println("Satıcı: " + book.get(5));
        }
    }
}