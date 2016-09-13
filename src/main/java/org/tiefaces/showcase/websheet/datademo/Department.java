package org.tiefaces.showcase.websheet.datademo;


import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private Employee chief = new Employee();
    private List<Employee> staff = new ArrayList<Employee>();
    private String link;
    private byte[] image;


    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, Employee chief, List<Employee> staff) {
        this.name = name;
        this.chief = chief;
        this.staff = staff;
    }


    public void addEmployee(Employee employee) {
        staff.add(employee);
    }

    public int getHeadcount() {
        return staff.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Employee getChief() {
        return chief;
    }

    public void setChief(Employee chief) {
        this.chief = chief;
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List staff) {
        this.staff = staff;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", chief=" + chief +
                ", staff.size=" + staff.size() +
                '}';
    }
}
