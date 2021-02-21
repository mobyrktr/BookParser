/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mailer.SendMails;
import parsehtml.Amazon;
import parsehtml.BKM;
import parsehtml.DR;
import parsehtml.Idefix;
import parsehtml.TestSitesi;

/**
 *
 * @author mobyr
 */
public class SQLiteDB {
    private Connection conn = null;
    Statement stmt;
    private final String dburl = "jdbc:sqlite:parseHTML.db";

    public SQLiteDB() throws SQLException
    {
      try
      {
          conn = DriverManager.getConnection(dburl);
          System.out.println("Bağlantı Başarılı.");
      }

      catch(SQLException e)
      {

          System.out.println(e.toString());
          System.out.println("Bağlantı sağlanamadı.");
      }
    }
    
    public String getFullNameByMail(String mail) throws SQLException
    {
        String name = "";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("select * from users where mail = '%s'", mail));
        if(rs.next())
        {
            return rs.getString(2) + " " + rs.getString(3);
        }
        
        return null;
    }
    
    public void addUser(String f_name, String l_name, String mail, String password, String phone) throws SQLException
    {
        stmt = conn.createStatement();
        stmt.executeUpdate(String.format("insert into users " + "(f_name, l_name, mail, password, phone) " + "values('%s', '%s', '%s', '%s', '%s')", f_name, l_name, mail, password, phone));
    }
    
    public boolean addFavorite(String mail, String book_name, float last_price, float shipment_and_price, String seller, String url) throws SQLException
    {
        int book_id = this.getBookID(url);
        
        if(book_id == -1) // kitap veritabanında yoksa
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(String.format("insert into favorites (book_name, last_price, shipment_and_price, seller, url) values('%s', '%s', '%s', '%s', '%s')", book_name, String.valueOf(last_price), String.valueOf(shipment_and_price), seller, url));            
            book_id = this.getBookID(url);
            
        }
        
        String favorites = this.getFavorites(mail) == null ? "" : this.getFavorites(mail);
        
        
        if(!favorites.contains(String.valueOf(book_id)))
        {
            favorites = favorites.concat(String.valueOf(book_id) + ":");
        }

        else
        {
            return false; //kitap zaten favorilerde var.
        }
        

        stmt = conn.createStatement();
        stmt.executeUpdate(String.format("update users set favorites = '%s' where mail = '%s'", favorites, mail)); // kulanıcının favorilerine ekler.
        
        return true; // başarıyla eklendi.
    }
    
    public void deleteFavorite(String mail, int book_id) throws SQLException
    {
        String favorites = this.getFavorites(mail);
        favorites = favorites.replaceAll(String.valueOf(book_id) + ":", "");
        stmt = conn.createStatement();
        stmt.executeUpdate(String.format("update users set favorites = '%s' where mail = '%s'", favorites, mail));
    }
    
    public int getFavoriteQty(String mail) throws SQLException
    {
        String favorites = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("select favorites from users where id = '%s'", mail));
        if(rs.next())
        {
            favorites = rs.getString(1);
        }
        
        if(favorites == null || favorites.equals(""))
        {
            return 0;
        }
        
        return favorites.split(":").length - 1;
    }
    
    public int getBookID(String book_url) throws SQLException
    {
        int id = 0;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("select id from favorites where url = '%s'", book_url));
        if(rs.next())
        {
            return rs.getInt(1);
        }
        
        return -1; // kitap bulunamadıysa
    }
    
    public ArrayList<ArrayList<String>> getFavoriteInfos(String mail) throws SQLException
    {
        String favorites = "";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("select favorites from users where mail = '%s'", mail));
        if(rs.next())
        {
            favorites = rs.getString(1);
        }
        
        if(favorites == null || favorites.equals(""))
            return null;
        
        ArrayList<ArrayList<String>> favorite_list = new ArrayList<>();
        try
        {
            for(String s: favorites.split(":"))
            {
                if(s != null)
                {
                    ArrayList<String> tmp = new ArrayList<>();
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(String.format("select book_name, last_price, shipment_and_price, seller, url from favorites where id = %d", Integer.valueOf(s)));
                    if(rs.next())
                    {
                        tmp.add(rs.getString(1));
                        tmp.add(rs.getString(2));
                        tmp.add(rs.getString(3));
                        tmp.add(rs.getString(4));
                        tmp.add(rs.getString(5));
                        tmp.add(s);
                        favorite_list.add(tmp);
                    }
                }
            }
        }

        catch(NullPointerException e)
        {
            return null;
        }
    
        return favorite_list;
    }
    
    public String getFavorites(String mail) throws SQLException
    {
        String favorites = "";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("select favorites from users where mail = '%s'", mail));
        if(rs.next())
        {
            favorites = rs.getString(1);
        }
        
        return favorites;
    }
    
    public boolean isRegistered(String mail, String password) throws SQLException
    {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("select mail, password from users where mail = '%s' and password = '%s'", mail, password));
        return rs.next();
    }
    
    public String getName(String mail) throws SQLException
    {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("select f_name from users where mail = '%s'", mail));
        if(rs.next())
        {
            return rs.getString(1);
        }
        
        return null;
    }
    
    public boolean isUniqueMail(String mail) throws SQLException
    {
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(String.format("select mail from users where mail = '%s'", mail));
        return !rs.next();
    }
    
    public void setMail(String old_mail, String new_mail) throws SQLException
    {
        stmt = conn.createStatement();
        stmt.executeUpdate(String.format("update users set mail = '%s' where mail = '%s'", new_mail, old_mail));
    }
    
    public void checkDiscount(String mail) throws SQLException, IOException
    {
        float[] toReturn = new float[2];
        float old_price, price = 0;
        String seller, url, book_name;
        int book_id;
        ArrayList<ArrayList<String>> infos = this.getFavoriteInfos(mail);
        for(ArrayList<String> book: infos)
        {
            book_name = book.get(0);
            seller = book.get(3);
            url = book.get(4);
            book_id = Integer.valueOf(book.get(5));
        
            System.out.println(url);
            switch(seller)
            {
                case "Amazon":
                    price = new Amazon("", url).getPriceFromURL();
                    break;

                case "BKM Kitap":
                    price = new BKM("", url).getPriceFromURL();
                    break;

                case "D&R":
                    price = new DR("", url).getPriceFromURL();
                    break;

                case "Idefix":
                    price = new Idefix("", url).getPriceFromURL();
                    break;

                case "Kitap Yurdu":
                    price = new BKM("", url).getPriceFromURL();
                    break;
                 
                case "TestSitesi":
                    price = new TestSitesi("").getPrice();
                    break;
            }

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("select last_price from favorites where id = %d", book_id));
            if(rs.next())
            {
                old_price = rs.getFloat(1);
                if(old_price != price)
                {
                    this.setPrice(book_id, price);
                    System.out.println("Güncellendi.");
                    if(price < old_price)
                    {
                        String name = getFullNameByMail(mail);
                        new SendMails(mail, book_name, String.valueOf(old_price), String.valueOf(price), name, url);
                    }
                }
                
                else
                {
                    System.out.println("Değişiklik yok.");
                }
            }
        }
    }
    
    public void setPrice(int book_id, Float price) throws SQLException
    {
        stmt = conn.createStatement();
        stmt.executeUpdate(String.format("update favorites set last_price = '%s' where id = %d", String.valueOf(price), book_id));
    }
}
