package com.simonmdsn.bulletarena.common.enemy;

import java.util.ArrayList;
import java.util.List;

public class FormationNumberGenerator {

    private static FormationNumberGenerator instance;

    public static FormationNumberGenerator getInstance() {
        if(instance == null) {
            instance = new FormationNumberGenerator();
        }
        return instance;
    }

    private FormationNumberGenerator() {
    }

    private final List<Integer> formationNumbers = new ArrayList<>();

    public int size() {
        return formationNumbers.size();
    }

    public int indexOf(int formationNumber) {
        return formationNumbers.indexOf(formationNumber);
    }

    public void remove(int formationNumber) {
        formationNumbers.remove(Integer.valueOf(formationNumber));
    }

    public int generate() {
        if(formationNumbers.isEmpty()) {
            formationNumbers.add(0);
            return formationNumbers.get(0);
        }
        Integer integer = formationNumbers.get(formationNumbers.size() - 1);
        int newFormationNumber = integer + 1;
        formationNumbers.add(newFormationNumber);
        return newFormationNumber;
    }
}
