package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJBDC;

public class DaoFactory {

    public static SellerDaoJBDC createSellerDao(){
        return new SellerDaoJBDC(DB.getConnection());
    }
}
