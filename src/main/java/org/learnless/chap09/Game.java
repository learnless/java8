package org.learnless.chap09;

import java.util.Arrays;
import java.util.List;

/**
 * Created by learnless on 18.1.31.
 */
public class Game {
    public static void main(String[] args) {
        List<Resizable> shapes = Arrays.asList(new Ellipse(), new Rectangle(), new Square());
        Utils.paint(shapes);
    }
}
