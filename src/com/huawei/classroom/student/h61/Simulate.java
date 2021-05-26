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
    private final List<Citizen> latents = new ArrayList<>();
    private final List<Citizen> deaths = new ArrayList<>();
    private final List<Citizen> cured = new ArrayList<>();
    private final List<Citizen> patientsAtHome = new ArrayList<>();
    private final List<Citizen> patientsInHospital = new ArrayList<>();

    public SimResult sim(Param param, int days) {
        this.param = param;
        SimResult result = new SimResult();
        setCompanies();
        setCitizens();
        setVaccinations();
        for (int i = 0; i < days; i++) {
            oneDayPass();
        }
        result.setLatents(latents.size());
        result.setPatients(patientsAtHome.size() + patientsInHospital.size());
        result.setCured(cured.size());
        result.setDeaths(deaths.size());
        return result;
    }

    private void oneDayPass() {
        for (Citizen citizen: latents) {
            citizen.spendOneLatentDay();
            if (citizen.getStatus() == Status.patientAtHome) {
                latents.remove(citizen);
                patientsAtHome.add(citizen);
            } else {
                infectCompany(citizen);
                infectFamily(citizen);
            }
        }
        for (Citizen citizen: patientsAtHome) {
            citizen.spendOnePatientHomeDay();
            if (hospital.size() < param.getHospitalSize()) {
                hospitalAdmits(citizen);
            } else {
                wait4HospitalPatients.add(citizen);
            }
            if (citizen.getStatus() == Status.dead || citizen.getStatus() == Status.cured) {
                patientsAtHome.remove(citizen);
            }
            if (citizen.getStatus() == Status.cured) {
                cured.add(citizen);
            }
            if (citizen.getStatus() == Status.dead) {
                deaths.add(citizen);
            }
        }
        for (Citizen citizen: patientsInHospital) {
            citizen.spendOnePatientHospitalDay();
            if (citizen.getStatus() == Status.dead || citizen.getStatus() == Status.cured) {
                patientsInHospital.remove(citizen);
                hospital.remove(citizen);
            }
            if (citizen.getStatus() == Status.cured) {
                cured.add(citizen);
            }
            if (citizen.getStatus() == Status.dead) {
                deaths.add(citizen);
            }
        }
        for (Citizen citizen: cured) {
            if (hospital.contains(citizen)) {
                hospital.remove(citizen);
                Citizen new2Hospital = wait4HospitalPatients.poll();
                if (new2Hospital != null) {
                    hospitalAdmits(new2Hospital);
                }
            }
        }
    }

    private void setCitizens() {
        for (Integer id : param.getInitPatients()) {
            Citizen citizen = new Citizen(param, id);
            citizen.infected();
            if (vaccinations.contains(id)) {
                citizen.vaccinated();
            }
            latents.add(citizen);
        }
    }

    private void setCompanies() {
        for (int i = 0; i < param.getCityPopulation(); i++) {
//            int id = (int) (Math.random() * param.getCityPopulation());
//            if (companies.contains(id)) {
//                i--;
//            } else {
//                companies.add(id);
//            }
            companies.add(i);
        }
        Collections.shuffle(companies);
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

    private void infectFamily(Citizen citizen) {
        List<Integer> familyMembers = getFamilyMembers(citizen.getId());
        double rate = param.getSpreadRateFamily();
        if (citizen.isVaccinated()) {
            rate = param.getSpreadRateFamily() * (1 - param.getImmuEffect());
        }
        for (int k = 0; k < param.getFamilySize(); k++) {
            Citizen family = new Citizen(param, familyMembers.get(k));
            if (family.getStatus() == Status.healthy) {
                if (Math.random() <= rate) {
                    family.infected();
                }
            }
        }
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

    private void infectCompany(Citizen citizen) {
        List<Integer> companyMembers = getCompanyMembers(citizen.getId());
        double rate = param.getSpreadRateCompany();
        if (citizen.isVaccinated()) {
            rate = param.getSpreadRateCompany() * (1 - param.getImmuEffect());
        }
        for (int k = 0; k < param.getCompanySize(); k++) {
            Citizen coworker = new Citizen(param, companyMembers.get(k));
            if (coworker.getStatus() == Status.healthy) {
                if (Math.random() <= rate) {
                    coworker.infected();
                    // home at night
                    infectFamily(coworker);
                }
            }
        }
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
