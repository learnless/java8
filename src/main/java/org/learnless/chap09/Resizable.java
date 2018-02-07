package org.learnless.chap09;

/**
 * 可调大小的图像接口
 * Created by learnless on 18.1.31.
 */
public interface Resizable extends Drawable {
    int getWidth();
    int getHeight();
    void setWidth(int width);
    void setHeight(int height);
    void setAbsoluteSize(int width, int height);
    /**
     * 发布后，新添加方法,所有的实现类都得继续更改，维护成本高
     * 可以在接口实现该方法
     */
    default void setRelativeSize(int wFactor, int hFactor) {
        setWidth(getWidth() * wFactor);
        setHeight(getHeight() * hFactor);
    }

}
