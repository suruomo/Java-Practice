package 多线程.交互.多线程交互;

public class Hero {
    public String name;
    public float hp;

    public int damage;

    public synchronized void recover() {   //回血
        while (hp >= 1000) {        //hp>=1000时停止加血
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        hp = hp + 1;
        System.out.printf("%s 回血1点,增加血后，%s的血量是%.0f%n", name, name, hp);
        // 通知那些等待在this对象上的线程，可以醒过来了，如第20行，等待着的减血线程，苏醒过来
    }

    public synchronized void hurt() {   //掉血
        while (hp <= 1) {        //hp<=1时停止减血
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        hp = hp - 1;
        System.out.printf("%s 减血1点,减少血后，%s的血量是%.0f%n", name, name, hp);
        this.notify();
    }

    public void attackHero(Hero h) {    //攻击英雄
        try {
            //为了表示攻击需要时间，每次攻击暂停1000毫秒
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        h.hp -= damage;
        System.out.format("%s 正在攻击 %s, %s的血变成了 %.0f%n", name, h.name, h.name, h.hp);

        if (h.isDead())   //是否死亡
            System.out.println(h.name + "死了！");
    }

    public boolean isDead() {
        return 0 >= hp ? true : false;
    }
}

