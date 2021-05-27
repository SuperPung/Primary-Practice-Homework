package com.huawei.classroom.student.h61;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author super
 */
public class Hospital {
    private final int size;
    private final Set<Citizen> patients = new HashSet<>();
    private final List<Citizen> waitingPatients = new LinkedList<>();

    public Hospital(int size) {
        this.size = size;
    }

    public void addPatient(Citizen citizen) {
        if (patients.size() < size) {
            patients.add(citizen);
            citizen.hospitalized();
        } else {
            waitingPatients.add(citizen);
        }
    }

    public void refresh() {
        patients.removeIf(citizen -> citizen.getStatus() != Status.patientInHospital);
        waitingPatients.removeIf(citizen -> citizen.getStatus() != Status.patientAtHome);
        while (patients.size() < size && waitingPatients.size() > 0) {
            Citizen citizen = waitingPatients.get(0);
            patients.add(citizen);
            citizen.hospitalized();
            waitingPatients.remove(0);
        }
    }
}
