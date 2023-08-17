package com.sis.base.presention.base.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.HashSet

abstract class BaseViewHolder<T : ViewDataBinding, H>(var binding: T) : RecyclerView.ViewHolder(binding.root) {


}