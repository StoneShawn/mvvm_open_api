package com.shawn.mvvm_cathybk.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.shawn.mvvm_cathybk.main.home.HomeFragment

object ActivityUtils {

    fun replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment, backStackTag: String?) {
        val transaction = fragmentManager.beginTransaction()
        if (!backStackTag.isNullOrEmpty()) {
            transaction.addToBackStack(backStackTag)
        }
        transaction.replace(containerId, fragment).commit()
    }

    fun addFragmentToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        containerId: Int,
        hideCurrent: Boolean
    ) {
        val transAction: FragmentTransaction = fragmentManager.beginTransaction()
        if (hideCurrent) {
            hideCurrentFragment(fragmentManager, containerId, transAction)
        }
        transAction.add(containerId, fragment)
        transAction.addToBackStack(null)
        transAction.commit()
    }

    fun hideCurrentFragment(
        fragmentManager: FragmentManager,
        containerId: Int,
        transaction: FragmentTransaction
    ) {
        val curFragment = fragmentManager.findFragmentById(containerId)
        if (curFragment != null) {
            transaction.hide(curFragment)
        }
    }
}