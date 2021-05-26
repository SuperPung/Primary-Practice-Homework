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

    private Family family;
    private Company company;
    private int latentPeriodDays;
    private boolean vaccinated;
    private boolean immune;

    public Citizen(Param param, int id) {
        this.param = param;
        this.id = id;
        status = Status.healthy;
        vaccinated = false;
        immune = false;
        latentPeriodDays = -1;
    }

    public void vaccinated() {
        this.vaccinated = true;
    }

    public boolean isVaccinated() {
        return vaccinated;
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
        if (status == Status.healthy && !immune) {
            this.status = Status.latent;
            latentPeriodDays = 0;
        }
    }

    public void sick() {
        if (status == Status.latent) {
            status = Status.patientAtHome;
        }
    }

    public void cured() {
        if (status == Status.patientAtHome || status == Status.patientInHospital) {
            status = Status.cured;
            immune = true;
        }
    }

    public void dead() {
        if (status == Status.patientAtHome || status == Status.patientInHospital) {
            status = Status.dead;
        }
    }

    public void hospitalized() {
        if (status == Status.patientAtHome) {
            status = Status.patientInHospital;
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

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
