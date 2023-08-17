package com.sis.base.utils

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.sis.base.R
import java.text.SimpleDateFormat


@BindingAdapter("bind:src", "bind:radius")
fun ImageView.setImage(src: Any?, radius: Float) {
    Glide.with(this).load(src).placeholder(R.drawable.bg_round)
        .transform(MultiTransformation(CenterCrop(), RoundedCorners(radius.toInt()))).into(this)
}


var inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
var outputFormat = SimpleDateFormat("yyyy-MM-dd  hh:mm a")

@BindingAdapter("bind:date", "bind:prefix")
fun TextView.setDateAndPrefix(worldDate: String?, prefix: String?) {
    if (!worldDate.isNullOrBlank()) {
        text = outputFormat.format(inputFormat.parse(worldDate)!!)
        if (!prefix.isNullOrBlank()) {
            append(" | ")
            append(prefix)
        }
    } else if (!prefix.isNullOrBlank()) {
        text = prefix
    }
}


@BindingAdapter("bind:messageUiState")
fun TextView.setMessageUiState(messageUiState: UiState?) {
    when (messageUiState) {
        is UiState.Error -> {
            visibility = View.VISIBLE
            messageUiState.ex?.message?.let {
                text = it
            } ?: run {
                messageUiState.message?.let {
                    text = it
                } ?: run {
                    setText(R.string.errorMessage)
                }
            }
        }

        is UiState.Loading -> {
            messageUiState.message?.let {
                visibility = View.VISIBLE
                text = it
            } ?: run {
                visibility = View.GONE
            }
        }

        is UiState.Empty -> {
            visibility = View.VISIBLE
            messageUiState.message?.let {
                text = it
            } ?: run {
                setText(R.string.empty)
            }
        }

        else -> {
            visibility = View.GONE
        }
    }
}

@BindingAdapter("bind:goneWhenNothing")
fun View.goneWhenNothing(goneWhenNothing: UiState) {
    visibility = if (goneWhenNothing is UiState.Nothing) View.GONE else View.VISIBLE
}

@BindingAdapter("bind:visibleWhenNothing")
fun View.visibleWhenNothing(visibleWhenNothing: UiState) {
    visibility = if (visibleWhenNothing is UiState.Nothing) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:visibleIsLoading")
fun View.visibleIsLoading(visibleIsLoading: UiState) {
    visibility = if (visibleIsLoading is UiState.Loading) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:visibleIsError")
fun View.visibleIsError(visibleIsError: UiState) {
    visibility =
        if (visibleIsError is UiState.Error && visibleIsError.ex !is NoActionException) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:visibleIsEmptyOrError")
fun View.visibleIsEmptyOrError(visibleIsEmptyOrError: UiState) {
    visibility =
        if (visibleIsEmptyOrError is UiState.Error || visibleIsEmptyOrError is UiState.Empty) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:likeState")
fun ImageView.likeState(isLike: Boolean?) {
    if (isLike == true) {
        setImageResource(R.drawable.ic_heart_full)
        setColorFilter(Color.RED)
    } else {
        setImageResource(R.drawable.ic_heart)
        setColorFilter(Color.BLACK)
    }

}



