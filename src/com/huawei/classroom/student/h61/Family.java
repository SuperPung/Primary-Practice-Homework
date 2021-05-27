package com.huawei.classroom.student.h61;

/**
 * @author super
 */
public class Family extends GroupedPlace {
    public Family(int id, int size, double spreadRate) {
        super(id, size, spreadRate);
    }

    @Override
    public void addCitizen(Citizen citizen) {
        super.addCitizen(citizen);
        citizen.setFamily(this);
    }

    /**
     * 检查一个人是否患病
     *
     * @param citizen 市民
     * @return 是/否
     */
    @Override
    public boolean checkInfection(Citizen citizen) {
        return citizen.getStatus() == Status.patientAtHome || citizen.getStatus() == Status.latent;
    }


}
