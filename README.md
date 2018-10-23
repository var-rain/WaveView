#### Androd 波浪按钮

##### 扩散效果
![preview image 1](https://github.com/1934016928/WaveView/raw/master/capture/wave_with_head.gif)
##### 收缩效果
![preview image 2](https://github.com/1934016928/WaveView/raw/master/capture/wave_with_head_revert.gif)
##### 使用文字
![preview image 3](https://github.com/1934016928/WaveView/raw/master/capture/wave_with_text.gif)
##### 以及高频率
![preview image 4](https://github.com/1934016928/WaveView/raw/master/capture/wave_with_text_more.gif)

#### XML属性介绍
- 波浪的密度,值越大,波浪的频率越高
```
app:wv_density="3"
```
- 填充圆的颜色,在没有设置图片的情况下才生效,设置了图片的情况下是不生效的
```
app:wv_fill_color="@color/wave_color"
```
- 填充圆的半径
```
app:wv_radius="30"
```
- 是否反向播放动画,也就是上图中的扩散或收缩
```
app:wv_revert="false"
```
- 波浪的扩散速度,这个嘛....越小越快,可以是 **0.01** 也可以是 **10**
```
app:wv_speed="3"
```
- 填充的图片资源,设置过后填充颜色属性不生效
```
app:wv_src="@drawable/head"
```
- 填充描边颜色
```
app:wv_stroke_color="#ff5050"
```
- 填充描边宽度
```
app:wv_stroke_width="8"
```
- 波浪的颜色
```
app:wv_wave_color="@color/wave_color"
```
- 波浪的范围大小
```
app:wv_wave_size="60"
```
- 文字内容
```
app:wv_text="立即开启"
```
- 文字的颜色
```
app:wv_text_color="#ffffff"
```
- 文字的大小
```
app:wv_text_size="10"
```
#### 使用方式

```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.var.wave.view.WaveView
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:wv_density="3"
        app:wv_fill_color="@color/wave_color"
        app:wv_radius="30"
        app:wv_revert="false"
        app:wv_speed="3"
        app:wv_src="@drawable/head"
        app:wv_stroke_color="#ff5050"
        app:wv_stroke_width="8"
        app:wv_wave_color="@color/wave_color"
        app:wv_wave_size="60" />

</android.support.constraint.ConstraintLayout>
```