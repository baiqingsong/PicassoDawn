# Picasso的使用

* [引用](#引用)
* [加载显示图片](#加载显示图片)
* [加载回调监听](#加载回调监听)
* [修改图片尺寸](#修改图片尺寸)
* [图片旋转](#图片旋转)
* [转换器Transformation](#转换器transformation)
* [请求优先级](#请求优先级)
* [获取图片](#获取图片)
* [缓存相关](#缓存相关)


## 引用
build.gradle里面引用：
```
dependencies {
    compile'com.squareup.picasso:picasso:2.5.2'
}
```


## 加载显示图片
简单应用：
```
Picasso.with(this).load("https://www.baidu.com/img/bd_logo1.png").into(mImageView);
```
显示资源文件下的图片：
```
Picasso.with(this).load(R.drawable.bd_logo).into(mImageView);
```
显示assets下的图片：
```
Picasso.with(this).load("file:///android_asset/bd_logo.png").into(mImageView);
```
显示sd卡下的图片：
```
Picasso.with(this).load("file:///sdcard/bd_logo.png").into(mImageView);
```
显示Uri的图片  
注：这个没有测试不过load方法可以自动加载uri  
Picasso.with(context).load(uri).into(imageView);

设置加载失败和加载中图片：
```
Picasso.with(this).load("https://www.baidu.com/img/bd_logo1.png")
    .placeholder(R.mipmap.ic_launcher)
    .error(R.mipmap.ic_launcher).into(mImageView);
```
如果加载发生错误会重复三次请求，三次都失败才会显示预设的错误图片。


## 加载回调监听
监听事件监听加载成功，错误
```
Picasso.with(this)
    .load("https://www.baidu.com/img/bd_logo1.png")
    .into(mImageView, new Callback() {
        @Override
        public void onSuccess() {
            Toast.makeText(ImageViewActivity.this, "load success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError() {
            Toast.makeText(ImageViewActivity.this, "load error", Toast.LENGTH_SHORT).show();
        }
    });
```

## 修改图片尺寸
加载图片，对图片进行剪切
```
Picasso.with(this)
    .load("https://www.baidu.com/img/bd_logo1.png")
    .resize(200,0)
    .into(mImageView);
```
注意：resize(200, 0)表示图片宽度200，高度等比拉伸

拉伸剪切类型：
* centerCrop()：和reszie（）配套使用，resize尺寸后，不拉伸截取中间部分显示
* centerInside()：和reszie（）配套使用，rresize尺寸后，等比拉神全部部分显示
* fit()：不能和reszie（）一起使用，非等比拉伸填满ImageView


## 图片旋转
```
Picasso.with(this)
    .load("https://www.baidu.com/img/bd_logo1.png")
    .rotate(180, 0 , 0)
    .into(mImageView);
```


## 转换器Transformation
例如:高斯模糊
```
public class BlurTransformation implements Transformation {

    RenderScript rs;

    public BlurTransformation(Context context) {
        super();
        rs = RenderScript.create(context);
    }

    @Override
    public Bitmap transform(Bitmap bitmap) {
        // Create another bitmap that will hold the results of the filter.
        Bitmap blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        // Allocate memory for Renderscript to work with
        Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);

        // Set the blur radius
        script.setRadius(25);

        // Start the ScriptIntrinisicBlur
        script.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        bitmap.recycle();

        return blurredBitmap;
    }

    @Override
    public String key() {
        return "blur";
    }
}
```
```
Picasso.with(this)
    .load("https://www.baidu.com/img/bd_logo1.png")
    .transform(new BlurTransformation(this))
    .into(mImageView);
```

需要设置build.gradle里面的最小sdk版本为17


例如做个灰处理操作：
```
public class GrayTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        int width, height;
        height = source.getHeight();
        width = source.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(source, 0, 0, paint);

        if(source!=null && source!=bmpGrayscale){
            source.recycle();
        }
        return bmpGrayscale;
    }

    @Override
    public String key() {
        return "gray";
    }
}
```
```
Picasso.with(this)
    .load("https://www.baidu.com/img/bd_logo1.png")
    .transform(new GrayTransformation())
    .into(mImageView);
```

如果需要加载多个Transform，有以下两种方式：
```
Picasso.with(this)
    .load("https://www.baidu.com/img/bd_logo1.png")
    .transform(new BlurTransformation(this))
    .transform(new GrayTransformation())
    .into(mImageView);
```
```
List<Transformation> transformations = new ArrayList<>();
transformations.add(new BlurTransformation(this));
transformations.add(new GrayTransformation());
Picasso.with(this)
        .load("https://www.baidu.com/img/bd_logo1.png")
        .transform(transformations)
        .into(mImageView);
```

一些Transformation的参考相关地址：

[https://github.com/wasabeef/picasso-transformations](https://github.com/wasabeef/picasso-transformations "Transformation参考地址")


## 请求优先级
```
public enum Priority {
    LOW,
    NORMAL,
    HIGH
}
```
```
Picasso.with(this)
    .load("https://www.baidu.com/img/bd_logo1.png")
    .priority(Picasso.Priority.HIGH)
    .into(mImageView);
```
默认优先级是normal


## 获取图片
Picasso获取图片有两种方式  
第一种：
```
new Thread(){
    @Override
    public void run() {
        super.run();
        try {
            final Bitmap bitmap = Picasso.with(ImageViewActivity.this).load("https://www.baidu.com/img/bd_logo1.png").get();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(bitmap != null){
                        mImageView.setImageBitmap(bitmap);
                    }else{
                        Toast.makeText(ImageViewActivity.this, "bitmap null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}.start();
```
需要注意的是get方法获取Bitmap类型图片必须再线程中执行
```
Picasso.with(this).load("https://www.baidu.com/img/bd_logo1.png").into(mImageView, new Callback() {
    @Override
    public void onSuccess() {
        Drawable drawable = mImageView.getDrawable();
    }

    @Override
    public void onError() {

    }
});
```


## 缓存相关
memoryPolicy 设置内存缓存策略  
MemoryPolicy是一个枚举，有两个值  
* NO_CACHE：表示处理请求的时候跳过检查内存缓存
* NO_STORE: 表示请求成功之后，不将最终的结果存到内存。
```
with(this).load(URL)
    .placeholder(R.drawable.default_bg)
    .error(R.drawable.error_iamge)
    .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE) //静止内存缓存
    .into(mBlurImage);
```

networkPolicy 设置磁盘缓存策略  
NetworkPolicy是一个枚举类型，有三个值：
* NO_CACHE: 表示处理请求的时候跳过处理磁盘缓存
* NO_STORE: 表示请求成功后，不将结果缓存到Disk,但是这个只对OkHttp有效。
* OFFLINE: 这个就跟 上面两个不一样了，如果networkPolicy方法用的是这个参数，那么Picasso会强制这次请求从缓存中获取结果，不会发起网络请求，不管缓存中能否获取到结果。
```
with(this).load(URL)
    .placeholder(R.drawable.default_bg)
    .error(R.drawable.error_iamge)
    .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)//跳过内存缓存
    .networkPolicy(NetworkPolicy.NO_CACHE)//跳过磁盘缓存
    .into(mBlurImage);
```

**注：缓存代码没有经过测试**




