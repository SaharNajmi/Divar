package com.example.divar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.divar.fragment.AdListFragment
import com.example.divar.fragment.FavoriteFragment
import com.example.divar.fragment.NewAdFragment

class PageAdapter(fm:FragmentManager) :FragmentPagerAdapter(fm){
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AdListFragment()
            1 -> FavoriteFragment()
            else ->  NewAdFragment()
        }
    }
}