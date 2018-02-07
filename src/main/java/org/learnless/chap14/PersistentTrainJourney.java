package org.learnless.chap14;

import java.util.function.Consumer;

/**
 * 持久化数据结构
 * 实例1
 * Created by learnless on 18.2.6.
 */
public class PersistentTrainJourney {

    public static void main(String[] args) {
        TrainJourney t1 = new TrainJourney(40, new TrainJourney(30, null));
        TrainJourney t2 = new TrainJourney(20, new TrainJourney(50, null));

        //链接两个旅程
//        TrainJourney result = link(t1, t2);
        TrainJourney result2 = append(t1, t2);

        //打印整个旅程的价格
        visited(result2, trainJourney -> System.out.println("the price is " + trainJourney.getPrice()));

        System.out.println();
    }

    private static void visited(TrainJourney trainJourney, Consumer<TrainJourney> consumer) {
        if (trainJourney != null) {
            consumer.accept(trainJourney);
            visited(trainJourney.getOnward(), consumer);
        }
    }

    /**
     * 破环式链接
     * @param a
     * @param b
     * @return
     */
    public static TrainJourney link(TrainJourney a, TrainJourney b) {
        if (a == null)  return b;

        TrainJourney t = a;
        if (t.getOnward() != null) {
            t = t.getOnward();
        }
        t.setOnward(b); //已经破环函数式编程，传进来的a已经更改

        return a;
   }

    /**
     * 采用函数式编程
     * 并未创建整个序列，只创建a的副本
     * @param a
     * @param b
     * @return
     */
    public static TrainJourney append(TrainJourney a, TrainJourney b) {
        return a == null ? b : new TrainJourney(a.getPrice(), append(a.getOnward(), b));
    }


}

class TrainJourney {
    private int price;
    private TrainJourney onward;    //指向下一站

    public TrainJourney(int price, TrainJourney onward) {
        this.price = price;
        this.onward = onward;
    }

    public int getPrice() {
        return price;
    }

    public TrainJourney getOnward() {
        return onward;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOnward(TrainJourney onward) {
        this.onward = onward;
    }
}