package com.simonmdsn.bulletarena.common.enemy;

import com.badlogic.ashley.core.Component;

public class FormationComponent implements Component {

    private final int formationNumber;

    public FormationComponent(int formationNumber) {
        this.formationNumber = formationNumber;
    }

    public int formationNumber() {
        return formationNumber;
    }
}
