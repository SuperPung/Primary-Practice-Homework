package com.huawei.classroom.student.h53;

/**
 * @author super
 */
public class Rabbit {
    private int dayAge;
    private boolean dead;
    private final int lifeDayTime = 700;

    public Rabbit() {
        dayAge = 0;
        dead = false;
    }

    public int getDayAge() {
        return dayAge;
    }

    public void growOneDay() {
        if (isDead()) {
            return;
        }
        dayAge++;
        if (dayAge > lifeDayTime) {
            dead = true;
        }
    }

    public boolean isDead() {
        return dead;
    }
}
