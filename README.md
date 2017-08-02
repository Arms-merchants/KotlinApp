# KotlinApp
一个测试项目
一些基本的kotlin代码，不过自定义view的部分还是采用的java来的写的
PaletteImageView 哈哈这个是抄的，不过正好作为练手写自定义view，虽说是抄写的不过省得以后会用到写点记录方便使用吧

```
<!--首先有几个自定义的属性 ，基本上都能通过代码设置，不过要设置图像的id，那么只能通过xml，不然就要传bitmap进去
随便了爱咋写咋写 图片的id，padding，阴影的x轴偏移量，阴影的Y轴偏移量，阴影的圆角尺寸-->
   <attr name="paletteRadius" format="dimension" />
       <attr name="paletteSrc" format="reference" />
       <attr name="palettePadding" format="dimension" />
       <attr name="paletteOffsetX" format="dimension" />
       <attr name="paletteOffsetY" format="dimension" />
       <attr name="paletteShadowRadius" format="dimension" />
   
       <declare-styleable name="PaletteImageView">
           <attr name="paletteRadius" />
           <attr name="paletteSrc" />
           <attr name="palettePadding" />
           <attr name="paletteOffsetX" />
           <attr name="paletteOffsetY" />
           <attr name="paletteShadowRadius" />
       </declare-styleable>
```

然后是view直接用就好，这个通过设置图片id
```xml
    <p.diqiugang.foriseinvest.com.kotlinapp.view.PaletteImageView
        android:id="@+id/palette_image_view"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_centerHorizontal="true"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="10dp"
        app:paletteOffsetX="10dp"
        app:paletteOffsetY="10dp"
        app:palettePadding="40dp"
        app:paletteSrc="@mipmap/ic_launcher" />

```

另外的一些方法
```
 setShadowColor(int color) 设置阴影的颜色。。不设置的话就是图片的主色调
 
 setPaletteRadiu(int raius) 图片的圆角半径，设大了就是圆形图片(●ˇ∀ˇ●)
  
 setPaletteShadowOffset(int offsetX, int offsetY) 设置阴影的最大偏移量，但是要在设置的padding范围内
 
 setPaletteShadowRadiu(int radiu)阴影的圆角半径
```
