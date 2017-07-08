package com.dawn.picassodawn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by 90449 on 2017/7/7.
 */

public class ImageViewActivity extends AppCompatActivity {
    private ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        mImageView = (ImageView) findViewById(R.id.image_view);
//        final ImageView mImageView02 = (ImageView) findViewById(R.id.image_view_02);
//        Picasso.with(this).load("https://www.baidu.com/img/bd_logo1.png").into(mImageView, new Callback() {
//            @Override
//            public void onSuccess() {
//                Drawable drawable = mImageView.getDrawable();
//                mImageView02.setImageDrawable(drawable);
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    final Bitmap bitmap = Picasso.with(ImageViewActivity.this).load("https://www.baidu.com/img/bd_logo1.png").get();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(bitmap != null){
//                                mImageView.setImageBitmap(bitmap);
//                            }else{
//                                Toast.makeText(ImageViewActivity.this, "bitmap null", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        Picasso.with(this)
//                .load("https://www.baidu.com/img/bd_logo1.png")
//                .priority(Picasso.Priority.HIGH)
//                .into(mImageView);
//        List<Transformation> transformations = new ArrayList<>();
//        transformations.add(new BlurTransformation(this));
//        transformations.add(new GrayTransformation());
//        Picasso.with(this)
//                .load("https://www.baidu.com/img/bd_logo1.png")
//                .transform(transformations)
//                .into(mImageView);
//        Picasso.with(this)
//                .load("https://www.baidu.com/img/bd_logo1.png")
//                .transform(new BlurTransformation(this))
////                .transform(new GrayTransformation())
//                .into(mImageView);
//        Picasso.with(this)
//                .load("https://www.baidu.com/img/bd_logo1.png")
//                .rotate(180, 0 , 0)
//                .into(mImageView);
//        Picasso.with(this)
//                .load("https://www.baidu.com/img/bd_logo1.png")
//                .resize(200,0)
//                .into(mImageView);
//        Picasso.with(this)
//                .load("https://www.baidu.com/img/bd_logo1.png")
//                .into(mImageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(ImageViewActivity.this, "load success", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError() {
//                        Toast.makeText(ImageViewActivity.this, "load error", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        Picasso.with(this).load(R.drawable.bd_logo).into(mImageView);
//        Picasso.with(this).load("file:///android_asset/bd_logo.png").into(mImageView);
//        Picasso.with(this).load("file:///sdcard/bd_logo.png").into(mImageView);
//        Picasso.with(this).load("https://www.baidu.com/img/bd_logo1.png").placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(mImageView);
    }
}
