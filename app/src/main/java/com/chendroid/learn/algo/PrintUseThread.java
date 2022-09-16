package com.chendroid.learn.algo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : zhaochen
 * @date : 2022/7/29
 * @description : 使用不同的线程打印    xxxx
 */
public class PrintUseThread {

    // 打印次数: A，B， C 每一个要打印 times 次
    private int times;

    // 本次要打印的次数； 如果为第 0 次，表示需要打印 A，如果为第 1 次，表示需要打印 B; 如果为第 2 次，表示需要打印 C;
    // 如果为第 3 次，表示需要打印 A; 如果为第 4 次，表示需要打印 B; 如果为第 5 次，表示需要打印 C ...
    private int currentPrintCount = 0;

    private final Lock lock = new ReentrantLock();

    private void reallyPrint(String targetString, int index) {
        // 次数的 for 循环不自动实现 i++， 而是在打印成功时，符合条件，才 i++； 真实循环的次数要远大于 times; 不停的在循环
        for (int i = 0; i < times; ) {
            // 进入到 for 循环时，进行加锁，让其他的线程进不来
            lock.lock();
            // 执行真正的打印操作；
            if (currentPrintCount % 3 == index) {
                // 打印的数量 state， 如果要打印 A 了，则打印次数对 3 取余需要 ==  0
                // 打印 B: state % 3 = 1; 打印 C: state % 3 = 2;
                System.out.print(targetString);
                i++;
                currentPrintCount++;
            }
            lock.unlock();
        }
    }

    /**
     * 三个线程分别打印 A，B，C ,总打印 times 次
     */
    public void printMain() {

        Thread aThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程 A 的运行逻辑，要打印 A 字母
                reallyPrint("A", 0);
            }
        });
        Thread bThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程 B 的运行逻辑，要打印 B 字母
                reallyPrint("B", 1);
            }
        });

        Thread cThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程 C 的运行逻辑，要打印 C 字母
                reallyPrint("C", 2);
            }
        });

        aThread.start();
        bThread.start();
        cThread.start();
    }


    /**
     * 方式二： 使用 wait() notify() 实现
     */

    // 线程的公共 monitor, 对它进行锁操作
    private final Object lockObject = new Object();

    public void printMain2() {

        Thread aThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程 A 的运行逻辑，要打印 A 字母
                // 这里正常进行循环，循环次数 times 次，在内部进行,加入一个唤醒队列中；
                for (int i = 0; i < times; i++) {
                    synchronized (lockObject) {
                        while (currentPrintCount % 3 != 0) {
                            // 如果此时，要打印的次数，不属于打印 A 的次数，则等等
                            try {
                                lockObject.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        currentPrintCount++;
                        System.out.println("A");
                        lockObject.notifyAll();
                    }
                }
            }
        });
        Thread bThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程 B 的运行逻辑，要打印 B 字母
                for (int i = 0; i < times; i++) {
                    synchronized (lockObject) {
                        while (currentPrintCount % 3 != 1) {
                            try {
                                lockObject.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        currentPrintCount++;
                        System.out.println("B");
                        lockObject.notifyAll();
                    }
                }
            }
        });

        Thread cThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程 C 的运行逻辑，要打印 C 字母
                for (int i = 0; i < times; i++) {
                    synchronized (lockObject) {
                        while (currentPrintCount % 3 != 2) {
                            try {
                                lockObject.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        currentPrintCount++;
                        System.out.println("C");
                        lockObject.notifyAll();
                    }
                }

            }
        });

        aThread.start();
        bThread.start();
        cThread.start();
    }

    private char c = 'A';

    private int number = 1;

    private final Object numberLock = new Object();

    /**
     * 用两个线程，一个输出字母，一个输出数字，交替输出 1A2B3C4D...26Z
     */
    public void printMain3() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 打印数字
                synchronized (numberLock) {
                    for (int i = 0; i < 26; i++) {
                        System.out.print(i + 1);
                        // 打印完之后，通知另外一个线程打印字母，并把自己处于 wait() 状态
                        numberLock.notifyAll();
                        try {
                            numberLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    numberLock.notifyAll();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 打印字母
                synchronized (numberLock) {
                    for (int i = 0; i < 26; i++) {
                        System.out.print(c + i);
                        // 打印完之后，通知另外一个线程打印数字，并把自己处于 wait() 状态
                        numberLock.notifyAll();
                        try {
                            numberLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    numberLock.notifyAll();
                }
            }
        }).start();
    }

    private final Lock lockDetail = new ReentrantLock();
    private final Condition c1 = lock.newCondition();
    private final Condition c2 = lock.newCondition();
    private final Condition c3 = lock.newCondition();

    /**
     * 实现更精细化的控制，哪个线程要挂起，哪个线程要开始运行； 精准唤醒某个线程；
     * <p> 线程 1 打印完 A 后，去唤醒线程 2 打印 B; 打印 B 后，去唤醒线程 3 打印 C; 打印好 C 后，去唤醒线程 1 打印字母 A
     */
    public void printMain4() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 打印字母 A
                // 这里不进行自动 i++， 只有在成功打印后，才 i++
                for (int i = 0; i < times; ) {
                    // 先进行上锁
                    lockDetail.lock();
                    try {
                        while (currentPrintCount % 3 != 0) {
                            // 如果取余不为 0 ， 表示此次不能打印 A；
                            // 把 c1 进行挂起 wait()等待处理
                            c1.await();
                        }
                        currentPrintCount++;
                        i++;
                        System.out.print("A");
                        // 此时打印 A 后，去唤醒 c2, 去打印字母 B
                        c2.signal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lockDetail.unlock();
                    }
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                // 打印字母 B
                // 这里不进行自动 i++， 只有在成功打印后，才 i++
                for (int i = 0; i < times; ) {
                    // 先进行上锁
                    lockDetail.lock();
                    try {
                        while (currentPrintCount % 3 != 1) {
                            // 如果取余不为 0 ， 表示此次不能打印 B；
                            // 把 c2 进行挂起 wait()等待处理
                            c2.await();
                        }
                        currentPrintCount++;
                        i++;
                        System.out.print("B");
                        // 此时打印 B 后，去唤醒 c3, 去打印字母 C
                        c3.signal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lockDetail.unlock();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 打印字母
                // 这里不进行自动 i++， 只有在成功打印后，才 i++
                for (int i = 0; i < times; ) {
                    // 先进行上锁
                    lockDetail.lock();
                    try {
                        while (currentPrintCount % 3 != 2) {
                            // 如果取余不为 0 ， 表示此次不能打印 C；
                            // 把 c3 进行挂起 wait()等待处理； 等待在线程 2 中去唤醒
                            c3.await();
                        }
                        currentPrintCount++;
                        i++;
                        System.out.print("C");
                        // 此时打印 C 后，去唤醒 c1, 去打印字母 A
                        c1.signal();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lockDetail.unlock();
                    }
                }
            }
        }).start();


    }

}
