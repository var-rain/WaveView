package com.var.wave.view;

/**
 * Created by: var_rain.
 * Created date: 2018/10/23.
 * Description: 波浪配置
 */
public class WaveConfig {

    /* 初始半径 */
    private int radius;
    /* 波浪大小 */
    private int waveSize;
    /* 速度 */
    private int speed;
    /* 反向播放 */
    private boolean revert;
    /* 波浪延时 */
    private long delay;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getWaveSize() {
        return waveSize;
    }

    public void setWaveSize(int waveSize) {
        this.waveSize = waveSize;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isRevert() {
        return revert;
    }

    public void setRevert(boolean revert) {
        this.revert = revert;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
