package com.huawei.classroom.student.h61;

import java.util.*;

/**
 * @author super
 */
public class Simulate {
    private Param param;
    private final List<Citizen> citizens = new ArrayList<>();
    private final Set<Integer> vaccinations = new HashSet<>();
    private final List<Integer> companies = new ArrayList<>();
    private final Set<Citizen> hospital = new HashSet<>();
    private final Queue<Citizen> wait4HospitalPatients = new LinkedList<>();
    private int latents = 0;
    private int deaths = 0;
    private int cured = 0;
    private int patients = 0;

    public SimResult sim(Param param, int days) {
        this.param = param;
        SimResult result = new SimResult();
        setCompanies();
        setCitizens();
        setVaccinations();
        for (int i = 0; i < days; i++) {
            // day
            for (int j = 0; j < param.getCityPopulation(); j++) {
                Citizen citizen = citizens.get(j);
                switch (citizen.getStatus()) {
                    case healthy:
                        break;
                    case latent:
                        citizen.spendOneLatentDay();
                        List<Integer> companyMembers = getCompanyMembers(j);
                        double rate = param.getSpreadRateCompany();
                        if (citizen.isVaccinated()) {
                            rate = param.getSpreadRateCompany() * (1 - param.getImmuEffect());
                        }
                        for (int k = 0; k < param.getCompanySize(); k++) {
                            Citizen coworker = citizens.get(companyMembers.get(k));
                            if (coworker.getStatus() == Status.healthy) {
                                if (Math.random() <= rate) {
                                    coworker.infected();
                                }
                            }
                        }
                        break;
                    case patient:
                        if (hospital.size() < param.getHospitalSize()) {
                            hospitalAdmits(citizen);
                        } else {
                            wait4HospitalPatients.add(citizen);
                            citizen.waitAtHome();
                        }
                        break;
                    case patientAtHome:
                        citizen.spendOnePatientHomeDay();
                        break;
                    case patientInHospital:
                        citizen.spendOnePatientHospitalDay();
                        break;
                    case cured:
                        if (hospital.contains(citizen)) {
                            hospital.remove(citizen);
                            Citizen new2Hospital = wait4HospitalPatients.poll();
                            if (new2Hospital != null) {
                                hospitalAdmits(new2Hospital);
                            }
                        }
                        break;
                    case dead:
                        break;
                    default:
                }
            }
            // night
            for (int j = 0; j < param.getCityPopulation(); j++) {
                Citizen citizen = citizens.get(j);
                if (citizen.getStatus() == Status.latent) {
                    List<Integer> familyMembers = getFamilyMembers(j);
                    double rate = param.getSpreadRateFamily();
                    if (citizen.isVaccinated()) {
                        rate = param.getSpreadRateFamily() * (1 - param.getImmuEffect());
                    }
                    for (int k = 0; k < param.getFamilySize(); k++) {
                        Citizen family = citizens.get(familyMembers.get(k));
                        if (family.getStatus() == Status.healthy) {
                            if (Math.random() <= rate) {
                                family.infected();
                            }
                        }
                    }
                }
            }
        }

        for (Citizen citizen : citizens) {
            switch (citizen.getStatus()) {
                case latent:
                    latents++;
                    break;
                case patient:
                case patientAtHome:
                case patientInHospital:
                    patients++;
                    break;
                case cured:
                    cured++;
                    break;
                case dead:
                    deaths++;
                    break;
                default:
            }
        }
        result.setLatents(latents);
        result.setPatients(patients);
        result.setCured(cured);
        result.setDeaths(deaths);
        return result;
    }

    private void setCitizens() {
        for (int i = 0; i < param.getCityPopulation(); i++) {
            Citizen citizen = new Citizen(param, i);
            if (param.getInitPatients().contains(i)) {
                citizen.infected();
            }
            if (vaccinations.contains(i)) {
                citizen.vaccinated();
            }
            citizens.add(citizen);
        }
    }

    private void setCompanies() {
        for (int i = 0; i < param.getCityPopulation(); i++) {
            int id = (int) (Math.random() * param.getCityPopulation());
            if (companies.contains(id)) {
                i--;
            } else {
                companies.add(id);
            }
        }
    }

    private List<Integer> getFamilyMembers(int id) {
        int size = param.getFamilySize();
        List<Integer> members = new ArrayList<>();
        int first = id / size * size;
        for (int i = 0; i < size; i++) {
            members.add(first + i);
        }
        return members;
    }

    private List<Integer> getCompanyMembers(int id) {
        int size = param.getCompanySize();
        List<Integer> members = new ArrayList<>();
        int num = companies.indexOf(id);
        int first = num / size * size;
        for (int i = 0; i < size; i++) {
            members.add(companies.get(first + i));
        }
        return members;
    }

    private void setVaccinations() {
        for (int i = 0; i < param.getCityPopulation() * param.getImmuRate(); i++) {
            int vaccinatedId = (int)(Math.random() % param.getCityPopulation());
            vaccinations.add(vaccinatedId);
        }
    }

    private void hospitalAdmits(Citizen citizen) {
        hospital.add(citizen);
        citizen.hospitalized();
    }
}
