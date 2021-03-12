package view

import RoomDatabase.*
import adapter.FavoriteAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import kotlinx.android.synthetic.main.fragment_favorite.*
import viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment(), RowClickListener {
    lateinit var viewModel: FavoriteViewModel
    lateinit var adapter: FavoriteAdapter
    private var listInformation: List<FavoriteEntity> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        //  viewModel = ViewModelProvider(this).get(FavoriteRoomDBViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        adapter = FavoriteAdapter(requireContext(), listInformation, this)
        rec_fav.layoutManager = manager
        rec_fav.adapter = adapter

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel.getAllFavorite(requireContext())
            ?.observe(viewLifecycleOwner, object : Observer<List<FavoriteEntity>> {
                override fun onChanged(t: List<FavoriteEntity>?) {
                    adapter.saveInformation(t!!)
                }
            })
    }

    override fun onItemClick(item: FavoriteEntity) {
        val go = Intent(context, DetailAdActivity::class.java)
        go.putExtra("id", item.id)
        go.putExtra("title", item.title)
        go.putExtra("description", item.description)
        go.putExtra("price", item.price)
        go.putExtra("city", item.city)
        go.putExtra("tell", item.userID)
        go.putExtra("category", item.category)
        go.putExtra("date", item.date)
        go.putExtra("img1", item.img1)
        go.putExtra("img2", item.img2)
        go.putExtra("img3", item.img3)
        startActivity(go)
    }

    override fun onDeleteClick(item: FavoriteEntity) {

    }
}