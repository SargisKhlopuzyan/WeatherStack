package app.sargis.khlopuzyan.weatherstack.helper



import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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

//@BindingAdapter("setTrackListData")
//fun TextView.setTrackListData(tracks: List<Track>?) {
//
//    tracks?.let {
//        var tracksStringBuilder = StringBuilder()
//
//        for ((index, track) in tracks.withIndex()) {
//            if (index < tracks.size - 1)
//                tracksStringBuilder.append("${track.name}\n")
//            else {
//                tracksStringBuilder.append("${track.name}")
//            }
//        }
//        text = tracksStringBuilder.toString()
//    }
//}

@BindingAdapter("setImageResource")
fun ImageView.setImageResource(resource: String?) {

//    val placeholderId: Int =
//        if (id == R.id.imageViewArtist) R.drawable.ic_artist else R.drawable.ic_album
//
//    if (resource == null || resource.isBlank()) {
//        setImageResource(placeholderId)
//        return
//    }

    Glide.with(this.context)
        .load(resource)
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