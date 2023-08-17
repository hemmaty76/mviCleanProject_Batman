package com.sis.base.presention.main.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sis.base.databinding.DialogBottomSheetFragmentBinding

class DialogBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: DialogBottomSheetFragmentBinding
    lateinit var title: String
    lateinit var description: String

    private var positiveButtonText: String? = null
    private var positiveListener: View.OnClickListener? = null

    private var negativeButtonText: String? = null
    private var negativeListener: View.OnClickListener? = null

    companion object {
        fun getInstance(title: String, desc: String): DialogBottomSheet {
            return DialogBottomSheet().apply {
                this.title = title
                this.description = desc
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBottomSheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.title.text = title
            binding.description.text = description

        positiveButtonText?.let {
            binding.positive.setOnClickListener { v: View? ->
                if (positiveListener != null) positiveListener!!.onClick(v)
                dismiss()
            }
            binding.positive.text = positiveButtonText
            binding.positive.visibility = View.VISIBLE
        }
        negativeButtonText?.let {

            binding.negative.setOnClickListener { v: View? ->
                if (negativeListener != null) negativeListener!!.onClick(v)
                dismiss()
            }
            binding.negative.text = negativeButtonText
            binding.negative.visibility = View.VISIBLE
        }
    }


    fun setPositiveListener(
        title: String,
        listener: View.OnClickListener? = null
    ): DialogBottomSheet {
        positiveButtonText = title
        positiveListener = listener
        return this
    }

    fun setNegativeListener(
        title: String,
        listener: View.OnClickListener? = null
    ): DialogBottomSheet {
        negativeButtonText = title
        negativeListener =
            listener ?: View.OnClickListener { dismiss() }
        return this
    }

}