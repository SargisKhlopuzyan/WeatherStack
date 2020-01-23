package app.sargis.khlopuzyan.weatherstack.helper


import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.ui.common.BindableAdapter
import app.sargis.khlopuzyan.weatherstack.util.DataLoadingState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
@BindingAdapter("data")
fun <T> RecyclerView.setRecyclerViewData(items: T?) {
    if (adapter is BindableAdapter<*>) {
        @Suppress("UNCHECKED_CAST")
        (adapter as BindableAdapter<T>).setItems(items)
    }
}

@BindingAdapter("setDataLoadingState")
fun <T> RecyclerView.setDataLoadingState(dataLoadingState: DataLoadingState?) {
    if (adapter is BindableAdapter<*>) {
        @Suppress("UNCHECKED_CAST")
        (adapter as BindableAdapter<T>).setDataLoadingState(dataLoadingState)
    }
}

@BindingAdapter("setWeatherDescriptions")
fun TextView.setWeatherDescriptions(weatherDescriptions: List<String>?) {

    weatherDescriptions?.let {
        var tracksStringBuilder = StringBuilder()

        for ((index, weatherDescription) in weatherDescriptions.withIndex()) {
            if (index < weatherDescriptions.size - 1)
                tracksStringBuilder.append("${weatherDescription}\n")
            else {
                tracksStringBuilder.append("$weatherDescription")
            }
        }
        text = tracksStringBuilder.toString()
    }
}

//@BindingAdapter("setRefreshing")
//fun SwipeRefreshLayout.setRefreshing(isRefreshing: Boolean?) {
//    isRefreshing?.let {
//        this.isRefreshing = isRefreshing
//    }
//}

@BindingAdapter("setOnRefreshListener")
fun SwipeRefreshLayout.setOnRefreshListener(onRefreshListener: SwipeRefreshLayout.OnRefreshListener) {
    this.setOnRefreshListener(onRefreshListener)
}


@BindingAdapter("setImageResource")
fun ImageView.setImageResource(resource: List<String>?) {

    val placeholderId: Int = R.drawable.ic_cloud

    if (resource == null || resource.isEmpty() || resource[0].isBlank()) {
        setImageResource(placeholderId)
        return
    }

    Glide.with(this.context)
        .load(resource[0])
        .apply(RequestOptions().dontTransform())
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                if (context !is AppCompatActivity) return false
                val activity = context as AppCompatActivity
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    activity.startPostponedEnterTransition()
                }
                activity.supportStartPostponedEnterTransition()
                return false
            }

        })
        .fitCenter()
        .into(this)

}

@BindingAdapter("setOnQueryTextListener")
fun SearchView.bindSetOnQueryTextListener(onQueryTextListener: SearchView.OnQueryTextListener) {
    this.setOnQueryTextListener(onQueryTextListener)
}

//@BindingAdapter("setItemDatabaseState")
//fun LottieAnimationView.setItemDatabaseState(album: Album?) {
//
//    when (album?.cachedState) {
//
//        CachedState.Cached -> {
//            repeatCount = LottieDrawable.INFINITE
//            setImageResource(R.drawable.ic_favorite_checked)
//        }
//
//        CachedState.InProcess -> {
//            repeatCount = LottieDrawable.RESTART
//            setAnimation("loading.json")
//            playAnimation()
//        }
//
//        CachedState.NotCached -> {
//            repeatCount = LottieDrawable.INFINITE
//            setImageResource(R.drawable.ic_favorite_unchecked)
//        }
//    }
//}