package org.learnless.chap09;

/**
 * 可旋转接口
 * Created by learnless on 18.1.31.
 */
public interface Rotatable {
    void setRotationAngle(int degrees);
    int getRotationAngle();
    default void rotateBy(int angle) {
        setRotationAngle((getRotationAngle() + angle) % 360);
    }
}
