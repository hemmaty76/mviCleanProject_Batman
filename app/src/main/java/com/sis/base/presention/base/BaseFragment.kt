package com.sis.base.presention.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sis.base.presention.main.MainActivity

/**
 * BaseFragment class the parent of all fragments.
 *
 * @author mojtaba hemmati sis
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    var hostActivity: Activity? = null
    protected lateinit var binding: VB
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    abstract fun setupUi()
    abstract fun setupData()
    abstract fun isFetchData() : Boolean

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isFetchData()){
            setupData()
        }
        setupUi()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        hostActivity = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        hostActivity = null
    }
}
