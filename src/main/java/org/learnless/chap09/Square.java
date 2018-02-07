package org.learnless.chap09;

/**
 * 图像类，实现自定义图像接口
 * Created by learnless on 18.1.31.
 */
public class Square implements Resizable {
    private int width;
    private int height;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void setAbsoluteSize(int width, int height) {
        this.width = width;
        switch (this.height = height) {
        }
    }

    @Override
    public void draw() {

    }
}
