package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.enities.Departament;
import model.enities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJBDC implements SellerDao {

    private Connection conn;

     public SellerDaoJBDC(Connection conn){
        this.conn = conn;
    }

    public SellerDaoJBDC() {

    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st =null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){ 
                Departament departament = instantialDepartment(rs);
                Seller obj = instantialSeller(rs, departament);
                return obj;
            }
            return null;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantialSeller(ResultSet rs, Departament departament) throws SQLException {
        Seller obj  = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartament(departament);
        return obj;
    }

    private Departament instantialDepartment(ResultSet rs) throws SQLException {
       Departament departament = new Departament();
        departament.setId(rs.getInt("DepartmentId"));
        departament.setName(rs.getString("DepName"));
        return departament;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    @Override
    public List<Seller> findByDepartment(Departament departament) {
        PreparedStatement st =null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "WHERE DepartmentId = ?\n" +
                    "ORDER BY Name");
            st.setInt(1, departament.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();

            while (rs.next()){
                departament = map.get(rs.getInt("DepartmentId"));
                if (departament == null){
                    departament = instantialDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), departament);
                }
                Seller obj = instantialSeller(rs, departament);
                list.add(obj);
            }
            return list;
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
