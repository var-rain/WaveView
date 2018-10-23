package com.var.wave.view;

import android.animation.ValueAnimator;

/**
 * Created by: var_rain.
 * Created date: 2018/10/22.
 * Description: 波浪对象
 */
public class Wave {

    /* 初始半径 */
    private int radius;
    /* 透明度 */
    private int alpha;
    /* 变换半径 */
    private int value;
    /* 波浪配置 */
    private WaveConfig config;

    public Wave(WaveConfig config) {
        this.config = config;
        this.radius = config.getRadius();
        this.initRadiusValueAnim();
        this.initAlphaValueAnim();
    }

    /**
     * 初始化半径变换动画
     */
    private void initRadiusValueAnim() {
        ValueAnimator animator;
        if (config.isRevert()) {
            animator = ValueAnimator.ofInt(config.getWaveSize(), 0);
        } else {
            animator = ValueAnimator.ofInt(0, config.getWaveSize());
        }
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(-1);
        animator.setDuration(config.getSpeed());
        animator.addUpdateListener(animation -> value = (int) animation.getAnimatedValue());
        animator.setStartDelay(config.getDelay());
        animator.start();
    }

    /**
     * 初始化透明度变换动画
     */
    private void initAlphaValueAnim() {
        ValueAnimator animator;
        if (config.isRevert()) {
            animator = ValueAnimator.ofInt(0, 255);
        } else {
            animator = ValueAnimator.ofInt(255, 0);
        }
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(-1);
        animator.setDuration(config.getSpeed());
        animator.addUpdateListener(animation -> alpha = (int) animation.getAnimatedValue());
        animator.setStartDelay(config.getDelay());
        animator.start();
    }

    /**
     * 获取当前半径
     *
     * @return 返回变换半径值
     */
    public int getRadius() {
        return radius + value;
    }

    /**
     * 获取当前透明度
     *
     * @return 返回变换透明度
     */
    public int getAlpha() {
        return alpha;
    }
}
