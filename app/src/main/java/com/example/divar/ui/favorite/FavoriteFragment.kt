package com.example.divar.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import com.example.divar.common.Constants.EXTRA_KEY_DATA
import com.example.divar.common.MyFragment
import com.example.divar.data.db.dao.entities.Advertise
import com.example.divar.ui.home.DetailAdActivity
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : MyFragment(), FavoriteAdapter.FavoriteBannerClickListener {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //show favorite list
        favoriteViewModel.banners.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                rec_favorite.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                rec_favorite.adapter =
                    FavoriteAdapter(requireContext(), it as ArrayList<Advertise>, this)
                rec_favorite.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE

            } else {
                //show  empty layout
                emptyLayout.visibility = View.VISIBLE
                emptyLayout.txtEmpty.text = getString(R.string.emptyFavorite)
            }
        }

        //show or not show ProgressBar
        favoriteViewModel.progress.observe(viewLifecycleOwner) {
            setProgress(it)
        }
    }

    override fun onClick(banner: Advertise) {
        startActivity(Intent(requireContext(), DetailAdActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, banner)
        })
    }

    override fun deleteItemClick(banner: Advertise) {
        favoriteViewModel.deleteFavorite(banner)
        Toast.makeText(requireContext(), "آیتم از لیست علاقمندی حذف شد!", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavorite()
    }
}