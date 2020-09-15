package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.enities.Departament;
import model.enities.Seller;

import java.sql.*;
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
         PreparedStatement st = null;
         try {
             st = conn.prepareStatement(" INSERT INTO seller\n" +
                     "(Name, Email, BirthDate, BaseSalary, DepartmentId)\n" +
                     "VALUES\n" +
                     "(?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS);
             st.setString(1, obj.getName());
             st.setString(2, obj.getEmail());
             st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
             st.setDouble(4, obj.getBaseSalary());
             st.setInt(5, obj.getDepartament().getId());

             int rowsAffected = st.executeUpdate();
             if (rowsAffected  > 0){
                 ResultSet rs = st.getGeneratedKeys();
                 if (rs.next()){
                     int id = rs.getInt(1);
                     obj.setId(id);
                 }
             }else {
                 throw new DbException("Unexpected error! no rows affected!");
             }

         } catch (SQLException e) {
             throw new DbException(e.getMessage());
         }
         finally {
             DB.closeStatement(st);
         }


    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(" UPDATE seller\n" +
                            "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\n" +
                            "WHERE Id = ?");
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());
            st.setInt(6, obj.getId());

           st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

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
        PreparedStatement st =null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n" +
                    "FROM seller INNER JOIN department\n" +
                    "ON seller.DepartmentId = department.Id\n" +
                    "ORDER BY Name");
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();

            while (rs.next()){
               Departament departament = map.get(rs.getInt("DepartmentId"));
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
