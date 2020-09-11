package Program;

import model.enities.Departament;
import model.enities.Seller;

import java.util.Date;

public class Aplication {

    public static void main(String[] args){

        Departament obj = new Departament(1, "books");
        Seller seller = new Seller(21, "bob", "bob@gmail.com", new Date(), 30000.0, obj);
        System.out.println(seller);
    }
}
