package model.dao;

import model.dao.impl.SellarDaoJBDC;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        return new SellarDaoJBDC();
    }
}
