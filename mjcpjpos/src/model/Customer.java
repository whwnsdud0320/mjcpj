package model;

public class Customer {
    private int custId;
    private String phone1, phone2, phone3;
    private String name;
    private int stampCount;

    public Customer(int custId, String phone1, String phone2, String phone3, String name, int stampCount) {
        this.custId = custId;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.phone3 = phone3;
        this.name = name;
        this.stampCount = stampCount;
    }

    public int getCustId() { return custId; }
    public String getFullPhone() { return phone1 + "-" + phone2 + "-" + phone3; }
    public int getStampCount() { return stampCount; }
}