package repositry;

import lk.ijse.pos.servlet.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerRepositry {

    public ArrayList<Customer> viewAllCustomers(Connection connection) throws SQLException;
    public boolean addCustomer(Connection connection, Customer customer) throws SQLException;
    public boolean updateCustomr(Connection connection, Customer customer) throws SQLException;
    public boolean deleteCustomer(Connection connection, String id) throws SQLException;
}
