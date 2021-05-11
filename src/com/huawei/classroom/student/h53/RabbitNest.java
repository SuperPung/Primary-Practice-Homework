package com.huawei.classroom.student.h53;

import java.util.ArrayList;
import java.util.List;

/**
 * @author super
 */
public class RabbitNest {
    private final List<Rabbit> rabbits = new ArrayList<>();

    public RabbitNest(int startCount) {
        for (int i = 0; i < startCount; i++) {
            Rabbit rabbit = new Rabbit();
            rabbits.add(rabbit);
        }
    }

    public void oneDayPass() {
        rabbitsGrow();
        rabbitsDead();
        rabbitsBearLittleRabbits();
    }

    public void rabbitsGrow() {
        for (Rabbit rabbit : rabbits) {
            rabbit.growOneDay();
        }
    }

    public void rabbitsDead() {
        rabbits.removeIf(Rabbit::isDead);
    }

    public void rabbitsBearLittleRabbits() {
        List<Rabbit> rabbitsBornOneDay = new ArrayList<>();
        for (Rabbit rabbit : rabbits) {
            int dayAge = rabbit.getDayAge();
            boolean isRabbitCanBear = dayAge == 180 || (dayAge > 180 && (dayAge - 180) % 90 == 0);
            if (isRabbitCanBear && !rabbit.isDead()) {
                Rabbit rabbit1 = new Rabbit();
                Rabbit rabbit2 = new Rabbit();
                rabbitsBornOneDay.add(rabbit1);
                rabbitsBornOneDay.add(rabbit2);
            }
        }
        rabbits.addAll(rabbitsBornOneDay);
    }

    public int getLivingRabbits() {
        return rabbits.size();
    }
}
