package com.shawn.common.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

//TODO: 需搞懂
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: VB? = null
    private var activityHandler: Context? = null

    val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityHandler = context
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingCallback().invoke(inflater, container)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        activityHandler = null
    }

    fun <H> getActivityHandler(type: Class<H>?): H? {
        if (type != null && type.isInstance(activityHandler)) {
            return activityHandler as H
        }

        return null
    }


    abstract fun bindingCallback(): (LayoutInflater, ViewGroup?) -> VB


}