package com.huawei.classroom.student.h59;

import java.util.Set;

/**
 * @author super
 */
public class Reaction {
    private Set<String> reactant;
    private Set<String> product;

    public Reaction(Set<String> reactant, Set<String> product) {
        this.reactant = reactant;
        this.product = product;
    }


    public Set<String> getReactant() {
        return reactant;
    }

    public Set<String> getProduct() {
        return product;
    }
}
