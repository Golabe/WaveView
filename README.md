# WaveView
## 水波浪

<div align="center"><image src="https://github.com/Golabe/WaveView/blob/master/gifs/a.gif?raw=true" with="300"/> </div>

### xml

```xml
  <com.github.golabe.waveview.library.WaveView
        android:id="@+id/waveView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:duration="1000"
        app:wave_after_alpha="100"
        app:wave_after_color="@color/colorPrimary"
        app:wave_before_color="@color/colorPrimary"
        app:wave_count="1"
        app:wave_height="10dp" />
```
### attrs

```xml
 <declare-styleable name="WaveView">
        <!--波高-->
        <attr name="wave_height" format="dimension" />
        <!--前一层波浪颜色-->
        <attr name="wave_before_color" format="color" />
        <!--后一层波浪颜色-->
        <attr name="wave_after_color" format="color" />
        <!--波的个数-->
        <attr name="wave_count" format="integer" />
        <!--持续时间-->
        <attr name="duration" format="integer" />
        <!--波浪高度-->
        <attr name="height" format="dimension" />
        <!--后面一层波浪 alpha值0-255-->
        <attr name="wave_after_alpha" format="integer"/>

    </declare-styleable>
```
