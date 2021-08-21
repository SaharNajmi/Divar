package ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import commom.EXTRA_KEY_DATA
import data.model.AdModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import ui.home.DetailAdActivity

class FavoriteFragment : Fragment(), FavoriteBannerClickListener {

    val favoriteViewModel: FavoriteViewModel by viewModel()

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
        favoriteViewModel.favoritebannerLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                rec_favorite.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                rec_favorite.adapter =
                    FavoriteAdapter(requireContext(), it as ArrayList<AdModel>, this)
                rec_favorite.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE

            } else {
                //اگر لیست عاقه مندی خالی بود لیوت خالی بودن را نشان دهد
                emptyLayout.visibility = View.VISIBLE
                emptyLayout.txtEmpty.text = getString(R.string.emptyFavorite)
            }
        }

    }

    override fun onClick(banner: AdModel) {
        startActivity(Intent(requireContext(), DetailAdActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, banner)
        })
    }

    override fun deleteItemClick(banner: AdModel) {
        favoriteViewModel.deleteFavorite(banner)
        Toast.makeText(requireContext(), "آیتم از لیست علاقمندی حذف شد!", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavorite()
    }
}