package org.learnless.chap14;

/**
 * 持久化数据结构
 * 实例2 二叉查找树
 * Created by learnless on 18.2.6.
 */
public class PersistentTree {

    public static void main(String[] args) {
        Tree t = new Tree("Mary", 22,
                new Tree("Emily", 20,
                        new Tree("Alan", 50, null, null),
                        new Tree("Georgie", 23, null, null)
                ),
                new Tree("Tian", 29,
                        new Tree("Raoul", 23, null, null),
                        null
                )
        );

        int val = lookup("Tian", 25, t);
//        Tree tree = update("Tom", 25, t);
        Tree tree = fupdate("Tom", 25, t);

        System.out.println(val);
    }

    /**
     * 树的查找，传入的参数没有更改，符合函数式编程
     * 新建节点的个数与数的深度有关，一般数的深度不会很大
     * @param k
     * @param defaultVal
     * @param tree
     * @return
     */
    static int lookup(String k, int defaultVal, Tree tree) {
        if (k == null)  return defaultVal;
        int comp = k.compareTo(tree.getKey());
        return comp == 0 ? tree.getVal() :
                (comp < 0 ? lookup(k, defaultVal, tree.getLeft()) : lookup(k, defaultVal, tree.getRight()));
    }

    /**
     * 数的更新以及添加,原先树的结构以更改,破环式
     * @param k
     * @param v
     * @param t
     */
    static Tree update(String k, int v, Tree t) {
        //当前节点为空，应该新建节点
        if (t == null) {
            return new Tree(k, v, null, null);
        }
        int comp = k.compareTo(t.getKey());
        if (comp == 0) {
            t.setVal(v);    //有副作用
        } else if(comp < 0) {
            t.setLeft(update(k, v, t.getLeft()));
        } else {
            t.setRight(update(k, v, t.getRight()));
        }
        return t;
    }

    /**
     * 函数时更新树结构
     * @param k
     * @param v
     * @param t
     * @return
     */
    static Tree fupdate(String k, int v, Tree t) {
        if (t == null) {
            return new Tree(k, v, null, null);
        }

        int comp = k.compareTo(t.getKey());
        Tree nt = null;
        if (comp == 0) {
            nt = new Tree(k, v, t.getLeft(), t.getRight());
        } else if(comp < 0) {
            nt = new Tree(t.getKey(), t.getVal(), fupdate(k, v, t.getLeft()), t.getRight());
        } else {
            nt = new Tree(t.getKey(), t.getVal(), t.getLeft(), fupdate(k, v, t.getRight()));
        }

        return nt;
    }



}

class Tree {
    private String key;
    private int val;
    private Tree left, right;

    public Tree(String key, int val, Tree left, Tree right) {
        this.key = key;
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public String getKey() {
        return key;
    }

    public int getVal() {
        return val;
    }

    public Tree getLeft() {
        return left;
    }

    public Tree getRight() {
        return right;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public void setLeft(Tree left) {
        this.left = left;
    }

    public void setRight(Tree right) {
        this.right = right;
    }
}
