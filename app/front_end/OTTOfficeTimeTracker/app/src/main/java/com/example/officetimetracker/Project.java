package com.example.officetimetracker;

public class Project {

    private int id;
    private String name;
    private String description;
    private int managerId;
    private String projectManagerName;

    Project(String id) {
        this.id = Integer.parseInt(id);
    }

    Project(String name, String description, String managerID) {
        this.name = name;
        this.description = description;
        this.managerId = Integer.parseInt(managerID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

}
