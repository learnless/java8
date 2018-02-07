package org.learnless.chap09;

import java.util.List;

/**
 * 图像工具类
 * Created by learnless on 18.1.31.
 */
public class Utils {
    public static void paint(List<Resizable> list) {
        list.forEach(l -> {
            l.setAbsoluteSize(20, 20);
            l.draw();
        });
    }
}
