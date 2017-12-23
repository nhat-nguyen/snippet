package com.penryn.snippet.glide

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import com.penryn.snippet.models.App

/**
 * Created by hoangnhat on 2017-12-21.
 */
class AppIconLoader(private val packageManager: PackageManager) : ModelLoader<App, Drawable> {
    override fun buildLoadData(model: App?, width: Int, height: Int, options: Options?): ModelLoader.LoadData<Drawable>? {
        if (model == null) {
            return null
        }

        return ModelLoader.LoadData(ObjectKey(model), AppDataFetcher(model, packageManager))
    }

    override fun handles(model: App?): Boolean {
        return model != null
    }

    class Factory(private val packageManager: PackageManager): ModelLoaderFactory<App, Drawable> {
        override fun teardown() {
        }

        override fun build(multiFactory: MultiModelLoaderFactory?): ModelLoader<App, Drawable> {
            return AppIconLoader(packageManager)
        }
    }

    private class AppDataFetcher(
        private val app: App,
        private val packageManager: PackageManager
    ) : DataFetcher<Drawable> {

        override fun getDataClass(): Class<Drawable> {
            return Drawable::class.java
        }

        override fun loadData(priority: Priority?, callback: DataFetcher.DataCallback<in Drawable>?) {
            if (callback == null) return

            try {
                callback.onDataReady(packageManager.getApplicationIcon(app.packageName))
            } catch (e: Exception) {
                callback.onLoadFailed(e)
            }
        }

        override fun getDataSource(): DataSource {
            return DataSource.LOCAL
        }

        override fun cleanup() {

        }

        override fun cancel() {

        }
    }
}
