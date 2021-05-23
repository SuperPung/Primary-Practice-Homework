package com.huawei.classroom.student.h61;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author super
 */
public class Citizen implements Serializable {
    private final int id;
    private Status status;
    private final Param param;
    private int latentPeriodDays;
    private boolean isVaccinated;

    public Citizen(Param param, int id) {
        this.param = param;
        this.id = id;
        status = Status.healthy;
        isVaccinated = false;
        latentPeriodDays = 0;
    }

    public void vaccinated() {
        this.isVaccinated = true;
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public void spendOneLatentDay() {
        if (status == Status.latent) {
            latentPeriodDays++;
            if (latentPeriodDays > param.getLatentPeriod()) {
                sick();
            }
        }
    }

    public void spendOnePatientHomeDay() {
        if (status == Status.patientAtHome) {
            if (Math.random() <= param.getHealingRateHome()) {
                cured();
            } else if (Math.random() <= param.getDeathRateHome()) {
                dead();
            }
        }
    }
    public void spendOnePatientHospitalDay() {
         if (status == Status.patientInHospital) {
            if (Math.random() <= param.getHealingRateHospital()) {
                cured();
            } else if (Math.random() <= param.getDeathRateHospital()) {
                dead();
            }
        }
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void infected() {
        if (status == Status.healthy) {
            this.status = Status.latent;
            latentPeriodDays = 0;
        }
    }

    public void sick() {
        if (status == Status.latent) {
            status = Status.patient;
        }
    }

    public void cured() {
        if (status == Status.patient || status == Status.patientAtHome || status == Status.patientInHospital) {
            status = Status.cured;
        }
    }

    public void dead() {
        if (status == Status.patient || status == Status.patientAtHome || status == Status.patientInHospital) {
            status = Status.dead;
        }
    }

    public void hospitalized() {
        if (status == Status.patient) {
            status = Status.patientInHospital;
        }
    }

    public void waitAtHome() {
        if (status == Status.patient) {
            status = Status.patientAtHome;
        }
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Citizen citizen = (Citizen) o;
        return id == citizen.id && Objects.equals(param, citizen.param);
    }
}
