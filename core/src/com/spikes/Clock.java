package com.spikes;

public class Clock {

    private long start;

    public Clock() {
        start = System.currentTimeMillis();
    }

    public long getTime() {
        return System.currentTimeMillis() - start;
    }

    public double getTimeInSeconds() {
        return (double)getTime() / 1000.0;
    }

    public void restart() {
        start = System.currentTimeMillis();
    }

}
