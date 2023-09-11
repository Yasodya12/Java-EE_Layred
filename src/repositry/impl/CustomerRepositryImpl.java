package repositry.impl;

import entity.Customer;

import repositry.CustomerRepositry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerRepositryImpl implements CustomerRepositry {


    @Override
    public ArrayList<Customer> viewAllCustomers(Connection connection) throws SQLException {
        System.out.println("Inside Repositry");

        ArrayList allCustomers = new ArrayList();
        ResultSet rst = execute("SELECT * from Customer", connection);
        while (rst.next()){
            Customer customer = new Customer();
            customer.setCusID( rst.getString(1)) ;
            customer.setCusName(rst.getString(2));
             customer.setCusAddress(rst.getString(3));
            allCustomers.add(customer);

        }
        System.out.println(allCustomers);
        return allCustomers;
    }

    public boolean addCustomer(Connection connection, Customer customer) throws SQLException {
        return execute("insert into Customer values(?,?,?,?)", connection, customer.getCusID(), customer.getCusName(),customer.getCusAddress(),"200");

    }

    public boolean updateCustomr(Connection connection, Customer customer) throws SQLException {
        return execute("update Customer set cusName=?,cusAddress=?,cusContact=? where cusID=?",connection, customer.getCusID(), customer.getCusName(),customer.getCusAddress(),"200");
    }
    public boolean deleteCustomer(Connection connection, String id) throws SQLException {
        return execute("delete from Customer where cusID=?", connection, id);
    }
    public static <T>T execute(String sql, Connection connection, Object... args) throws SQLException{

        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i+1),args[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T) pstm.executeQuery();
        }else{
            return (T) (Boolean) (pstm.executeUpdate()>0);
        }
    }
}
