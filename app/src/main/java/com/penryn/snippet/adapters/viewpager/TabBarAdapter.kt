package com.penryn.snippet.adapters.viewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.penryn.snippet.R
import com.penryn.snippet.database.SnippetAppDatabase
import com.penryn.snippet.views.AppFilterableRecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TabBarAdapter(
    private val context: Context,
    private val appDatabase: SnippetAppDatabase
): PagerAdapterIconDrawable() {

    // TODO: Group icon, view, data loader together
    private val viewCache: Array<View?> = arrayOfNulls(4)
    private val disposables: Array<Disposable?> = arrayOfNulls(4)
    private val icons = arrayOf(
        R.drawable.ic_search_black_24dp, R.drawable.clipboard,
        R.drawable.play_store, R.drawable.ic_settings_black_24dp
    )

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        if (viewCache[position] != null) {
            return viewCache[position]!!
        }

        viewCache[position] = LayoutInflater.from(context).inflate(R.layout.tab_app_search, container, false)
        container.addView(viewCache[position])

        when (position) {
            0, 1, 2, 3 -> {
                // TODO: Dangling view. Race conditions. Probably need to use destroyItem to unsubscribe properly
                // TODO: Don't reload
                // TODO: position?

                if (disposables[position] == null || disposables[position]!!.isDisposed) {
                    val subscription = appDatabase.appDao().getAll()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .map { it.sortedWith(Comparator { o1, o2 -> o1.label.compareTo(o2.label) })}
                        .cache()

                    disposables[position] = subscription.subscribe({
                        (viewCache[position] as AppFilterableRecyclerView?)?.setDataset(it)
                    }, {})
                }
            }
        }

        return viewCache[position]!!
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return 4
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
        viewCache[position] = null
        disposables[position]?.dispose()
    }

    override fun getPageIcon(position: Int): Int {
        return icons[position]
    }
}
