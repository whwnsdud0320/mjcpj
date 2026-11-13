package model;

public class Staff {
    private int staffId;
    private String name;
    private String password;
    private String phone;
    private String role; // 'ADMIN', 'STAFF'
    private int hourlyWage;
    private int status; // 1: 재직, 0: 퇴사

    public Staff(int staffId, String name, String password, String phone, String role, int hourlyWage, int status) {
        this.staffId = staffId;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.hourlyWage = hourlyWage;
        this.status = status;
    }

    public int getStaffId() { return staffId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }
    public int getHourlyWage() { return hourlyWage; }
    public int getStatus() { return status; }
}