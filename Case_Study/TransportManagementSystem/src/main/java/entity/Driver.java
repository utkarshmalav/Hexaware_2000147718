package entity;

public class Driver {
    private int driverId;
    private String name;
    private String phone;
    private String status;

    public Driver() {}

    public Driver(int driverId, String name, String phone, String status) {
        this.driverId = driverId;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Driver [driverId=" + driverId + ", name=" + name + ", phone=" + phone + ", status=" + status + "]";
    }
}