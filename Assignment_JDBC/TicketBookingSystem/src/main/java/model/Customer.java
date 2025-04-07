package model;

public class Customer {
    private String customerName;
    private String email;
    private String phoneNumber;

    public Customer(String name, String email, String phone) {
        this.customerName = name;
        this.email = email;
        this.phoneNumber = phone;
    }

    public void displayCustomerDetails() {
        System.out.println("Customer: " + customerName);
    }

    public String getName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
