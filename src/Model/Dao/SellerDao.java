package Model.Dao;

import model.enities.Departament;
import model.enities.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Integer id);
    Departament findById(Integer id);
    List<Seller> findAll();
}
