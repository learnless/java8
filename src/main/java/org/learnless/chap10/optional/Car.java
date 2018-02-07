package org.learnless.chap10.optional;

import java.util.Optional;

/**
 * Created by learnless on 18.1.31.
 */
public class Car {
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }
}
