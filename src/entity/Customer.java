package entity;

public class Customer {
    String cusID;
    String cusName;
    String cusAddress;


    public Customer() {
    }

    public Customer(String cusID, String cusName, String cusAddress) {
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusAddress = cusAddress;

    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "cusID='" + cusID + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                '}';
    }
}
