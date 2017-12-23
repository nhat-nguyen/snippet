package com.penryn.snippet.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.penryn.snippet.models.App;

/**
 * Created by hoangnhat on 2017-12-20.
 */

@GlideModule
public class SnippetGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.prepend(App.class, Drawable.class, new AppIconLoader.Factory(context.getPackageManager()));
    }

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
