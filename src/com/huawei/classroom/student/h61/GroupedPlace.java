package com.huawei.classroom.student.h61;

import java.util.ArrayList;
import java.util.List;

/**
 * @author super
 */
public abstract class GroupedPlace {
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

    public boolean isContainsInfectionSource() {
        return containsInfectionSource;
    }

    public void resetInfectionSource() {
        containsInfectionSource = false;
        for (Citizen citizen : citizens) {
            if (checkInfection(citizen)) {
                containsInfectionSource = true;
                break;
            }
        }
    }

    /**
     * 检查一个人是否患病
     * @param citizen 市民
     * @return 是/否
     */
    public abstract boolean checkInfection(Citizen citizen);
}
