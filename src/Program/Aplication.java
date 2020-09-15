package Program;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.enities.Departament;
import model.enities.Seller;

import java.util.Date;
import java.util.List;

public class Aplication {

    public static void main(String[] args){

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller FindById ===");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("\n=== TEST 2: seller FindByDepartment ===");
        Departament departament = new Departament(2, null);
        List<Seller> list = sellerDao.findByDepartment(departament);
        for (Seller obj : list){
            System.out.println(obj);
        }
    }
}
