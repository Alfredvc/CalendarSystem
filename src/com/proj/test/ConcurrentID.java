package com.proj.test;

import java.sql.Time;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Alfredo
 * Date: 11.03.14
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
public class ConcurrentID {

    public static long nextID(){
        return System.currentTimeMillis();
    }

}
