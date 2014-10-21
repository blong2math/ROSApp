package app.ros.com.rosapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;

/**
 * Created by Huxley on 2014/10/20.
 */
public class AppApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        onInitialize(null);
    }

    protected void onInitialize(Bundle savedInstance){
        initImageLoader();
    }

    protected Context getContext(){
        return getApplicationContext();
    }

    private void initImageLoader(){
        UnlimitedDiscCache unlimitedDiscCache = new UnlimitedDiscCache(new File(getExternalCacheDir()
                + File.separator + "image"), new File(getCacheDir() + File.separator + "image"));
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().
                resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(true).
                bitmapConfig(Bitmap.Config.RGB_565).displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getContext()).
                diskCache(unlimitedDiscCache).defaultDisplayImageOptions(displayImageOptions).build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }
}
