package com.shawn.mvvm_cathybk.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.shawn.mvvm_cathybk.main.home.HomeFragment

object ActivityUtils {

    fun replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment, backStackTag: String?) {
        val transaction = fragmentManager.beginTransaction()
        if (!backStackTag.isNullOrEmpty()) {
            transaction.addToBackStack(backStackTag)
        }
        transaction.replace(containerId, fragment).commit()
    }

}