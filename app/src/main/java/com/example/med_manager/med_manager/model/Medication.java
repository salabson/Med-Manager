package com.example.med_manager.med_manager.model;

/**
 * Created by salabs on 04/04/2018.
 */

public class Medication {
    private int id;
    private String Name;
    private  String Description;
    private int Frequency;
    private String StartDate;
    private String EndDate;

    public Medication(String name, String description, int frequency, String startDate, String endDate) {
        this.id = id;
        Name = name;
        Description = description;
        Frequency = frequency;
        StartDate = startDate;
        EndDate = endDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public int getFrequency() {
        return Frequency;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }
}
