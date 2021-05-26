package com.huawei.classroom.student.h61;

import java.util.ArrayList;
import java.util.List;

/**
 * @author super
 */
public class GroupedPlace {
    private int id;
    private int size;
    private double spreadRate;
    private List<Citizen> citizens;
    private boolean containsInfectionSource = false;

    public GroupedPlace(int id, int size, double spreadRate) {
        this.id = id;
        this.size = size;
        this.spreadRate = spreadRate;
        citizens = new ArrayList<>();
    }

    public void addCitizen(Citizen citizen) {
        citizens.add(citizen);
    }
}
