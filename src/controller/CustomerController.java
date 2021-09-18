package controller;

import db.DbConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController{

    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM Customer").executeQuery();
        List<String> ids = new ArrayList<>();

        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }



    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Customer VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(query);
        stm.setObject(1,c.getId());
        stm.setObject(2,c.getName());
        stm.setObject(3,c.getAddress());
        stm.setObject(4,c.getCity());
        stm.setObject(5,c.getProvince());
        stm.setObject(6,c.getContact());

        return stm.executeUpdate()>0;
    }


    public Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM Customer WHERE CustID=?");
        stm.setObject(1,id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()){
            return new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            );
        }else {
            return null;
        }
    }

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement(
                "DELETE FROM Customer WHERE CustID = '"+id+"'").executeUpdate()>0){
            return true;
        }else {
            return false;
        }
    }


    public boolean updateCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "UPDATE Customer SET CustName=?,CustAddress=?,City=?,Province=?,Contact=? WHERE CustID=?");
        stm.setObject(1,c.getName());
        stm.setObject(2,c.getAddress());
        stm.setObject(3,c.getCity());
        stm.setObject(4,c.getProvince());
        stm.setObject(5,c.getContact());
        stm.setObject(6,c.getId());

        return stm.executeUpdate()>0;
    }


    public ArrayList<Customer> getAllCustomers() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()){
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return customers;
    }
}
