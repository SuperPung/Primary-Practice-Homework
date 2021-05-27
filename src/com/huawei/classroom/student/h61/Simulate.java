package com.huawei.classroom.student.h61;

import java.util.*;

/**
 * @author super
 */
public class Simulate {
    private Param param;
    private final List<Citizen> citizens = new ArrayList<>();
    private final List<Family> families = new ArrayList<>();
    private final List<Company> companies = new ArrayList<>();
    private Hospital hospital;
    private final SimResult simResult = new SimResult();

    public SimResult sim(Param param, int days) {
        this.param = param;
        hospital = new Hospital(param.getHospitalSize());
        init();

        for (int i = 0; i < days; i++) {
            oneDayPass();
        }

        count();
        return simResult;
    }

    private void oneDayPass() {
        for (Family family: families) {
            family.resetInfectionSource();
        }
        for (Company company: companies) {
            company.resetInfectionSource();
        }
        for (Citizen citizen: citizens) {
            citizen.spendOneDay(this);
        }
        hospital.refresh();
    }

    private void init() {
        initFamilies();
        initCitizens();
        initCompanies();
        initPatients();
    }

    private void initCitizens() {
        for (int i = 0; i < param.getCityPopulation(); i++) {
            Citizen citizen = new Citizen(param, i);
            if (Util.getOrNotWithProbability(param.getImmuRate())) {
                citizen.vaccinated();
            }
            citizens.add(citizen);
            families.get(i / param.getFamilySize()).addCitizen(citizen);
        }
    }

    private void initPatients() {
        List<Integer> initPatientsIndexes = param.getInitPatients();
        for (int index : initPatientsIndexes) {
            citizens.get(index).infected();
        }
    }

    private void initFamilies() {
        int familiesCount = param.getCityPopulation() / param.getFamilySize();
        for (int i = 0; i < familiesCount; i++) {
            families.add(new Family(i, param.getFamilySize(), param.getSpreadRateFamily()));
        }
    }

    private void initCompanies() {
        int companiesCount = param.getCityPopulation() / param.getCompanySize();
        for (int i = 0; i < companiesCount; i++) {
            companies.add(new Company(i, param.getCompanySize(), param.getSpreadRateCompany()));
        }
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < param.getCityPopulation(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);
        for (int i = 0; i < param.getCityPopulation(); i++) {
            int citizenIndex = indexes.get(i);
            int companyIndex = i / param.getCompanySize();
            companies.get(companyIndex).addCitizen(citizens.get(citizenIndex));
        }
    }

    private void count() {
        int deaths = 0;
        int cured = 0;
        int patientsAtHome = 0;
        int patientsInHospital = 0;
        int latents = 0;
        for (Citizen citizen: citizens) {
            switch (citizen.getStatus()) {
                case dead:deaths++;break;
                case cured:cured++;break;
                case latent:latents++;break;
                case patientAtHome:patientsAtHome++;break;
                case patientInHospital:patientsInHospital++;break;
                default:
            }
        }
        simResult.setCured(cured);
        simResult.setDeaths(deaths);
        simResult.setLatents(latents);
        simResult.setPatients(patientsAtHome + patientsInHospital);
    }

    public Hospital getHospital() {
        return hospital;
    }
}
