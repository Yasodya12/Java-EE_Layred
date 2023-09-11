package service.impl;


import lk.ijse.pos.servlet.entity.Customer;
import lk.ijse.pos.servlet.repositry.CustomerRepositry;
import lk.ijse.pos.servlet.repositry.impl.CustomerRepositryImpl;
import lk.ijse.pos.servlet.util.ResponseUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/xxx"})
public class CustomerServiceImpl extends HttpServlet {
    CustomerRepositry customerRepositry =new CustomerRepositryImpl();
    public CustomerServiceImpl() {
        System.out.println("Contructor");


    }
    public Connection getConnection() throws SQLException {

        ServletContext servletContext = getServletContext();
        BasicDataSource pool = (BasicDataSource) servletContext.getAttribute("dbcp");
        return pool.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Inside Serverlet");



        try {

            ArrayList<Customer> customers = customerRepositry.viewAllCustomers(getConnection());
            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            int index = 0;
            while (index < customers.size()) {
                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                Customer customer = customers.get(index);
                customerObject.add("id",customer.getCusID() );
                customerObject.add("name", customer.getCusName());
                customerObject.add("address", customer.getCusAddress());

                allCustomers.add(customerObject.build());
                index++;

            }

            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allCustomers.build()));
        } catch (SQLException e) {

            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            Customer customer = new Customer();
            System.out.println(req.getParameter("cusID")+req.getParameter("cusName")+req.getParameter("cusAddress"));
            customer.setCusID(req.getParameter("cusID"));
            customer.setCusName(req.getParameter("cusName"));
            customer.setCusAddress(req.getParameter("cusAddress"));

             customerRepositry.addCustomer(getConnection(), customer);
             if ( customerRepositry.addCustomer(getConnection(), customer)){
                 resp.getWriter().print(ResponseUtil.genJson("Success", "Successfully Added.!"));
             }

        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();
            Customer customer = new Customer();
            customer.setCusID(jsonObject.getString("id"));
            customer.setCusName(jsonObject.getString("name"));
            customer.setCusAddress( jsonObject.getString("address"));
            customerRepositry.updateCustomr(getConnection(), customer);
            if (customerRepositry.updateCustomr(getConnection(), customer)){
                resp.getWriter().print(ResponseUtil.genJson("Success", "Customer Updated..!"));
            }else {
                resp.getWriter().print(ResponseUtil.genJson("Failed", "Customer Updated Failed..!"));
            }
        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            boolean rrrrrr = customerRepositry.deleteCustomer(getConnection(), req.getParameter("cusID"));
            System.out.println(req.getParameter("cusID")+rrrrrr);
            if (rrrrrr){
                resp.getWriter().print(ResponseUtil.genJson("Success", "Customer Deleted..!"));
            }else {
                resp.getWriter().print(ResponseUtil.genJson("Failed", "Customer Delete Failed..!"));
            }
        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));

        }
    }
}
