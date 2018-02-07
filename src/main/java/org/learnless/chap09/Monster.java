package org.learnless.chap09;

/**
 * 实现多个接口，接口有默认方法的实现，间接类似于多继承
 * Created by learnless on 18.1.31.
 */
public class Monster implements Rotatable, Moveable, Resizable {
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setY(int y) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void setWidth(int width) {

    }

    @Override
    public void setHeight(int height) {

    }

    @Override
    public void setAbsoluteSize(int width, int height) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void setRotationAngle(int degrees) {

    }

    @Override
    public int getRotationAngle() {
        return 0;
    }
}
