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
    /**
     * 所处家庭
     */
    private Family family;
    /**
     * 所处公司
     */
    private Company company;
    /**
     * 处于潜伏期的天数
     */
    private int latentPeriodDays;
    /**
     * 是否注射疫苗
     */
    private boolean vaccinated;
    /**
     * 是否免疫
     */
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
        vaccinated = true;
    }

    public void spendOneDay(Simulate simulate) {
        if (status == Status.healthy) {
            double effect = 0;
            if (vaccinated) {
                effect = param.getImmuEffect();
            }
            if (family.isContainsInfectionSource()) {
                checkInfected(param.getSpreadRateFamily() * (1 - effect));
            }
            if (company.isContainsInfectionSource()) {
                checkInfected(param.getSpreadRateCompany() * (1 - effect));
            }
        }
        if (status == Status.latent) {
            latentPeriodDays++;
            if (latentPeriodDays > param.getLatentPeriod()) {
                sick();
                simulate.getHospital().addPatient(this);
            }
        }
        if (status == Status.patientAtHome) {
            if (Util.getOrNotWithProbability(param.getDeathRateHome())) {
                dead();
            } else if (Util.getOrNotWithProbability(param.getHealingRateHome())) {
                cured();
            }
        }
         if (status == Status.patientInHospital) {
             if (Util.getOrNotWithProbability(param.getDeathRateHospital())) {
                 dead();
             } else if (Util.getOrNotWithProbability(param.getHealingRateHospital())) {
                 cured();
             }
        }
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

    /**
     * 在一定的传染率下，检查一个人是否被感染
     * @param spreadRate 传染率
     */
    public void checkInfected(double spreadRate) {
        if (status == Status.healthy) {
            if (Util.getOrNotWithProbability(spreadRate)) {
                infected();
            }
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

    public void setFamily(Family family) {
        this.family = family;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
