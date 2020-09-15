package Program;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.enities.Departament;
import model.enities.Seller;

import java.util.Date;

public class Aplication {

    public static void main(String[] args){

        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
    }
}
