package view

import adapter.AdAdapter
import adapter.ItemOnClickListener
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.divar.R
import kotlinx.android.synthetic.main.fragment_ad_list.*
import model.AdModel
import viewmodel.BannerViewModel

class AdListFragment : Fragment(), ItemOnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ad_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*====================================viewModel===========================================*/
        val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(Application())
            .create(BannerViewModel::class.java)

        val listMutableLiveData: MutableLiveData<ArrayList<AdModel>> =
            viewModel.getListMutableLiveData()

        listMutableLiveData.observe(this, object : Observer<ArrayList<AdModel>> {
            override fun onChanged(t: ArrayList<AdModel>?) {

                val adapter = AdAdapter(requireContext(), t!!, this@AdListFragment)
                val manager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                rec_ad.layoutManager = manager
                rec_ad.adapter = adapter
            }
        })
    }

    override fun onItemClick(item: AdModel) {
        val go = Intent(context, DetailAdActivity::class.java)
        go.putExtra("id", item.id)
        go.putExtra("title", item.title)
        go.putExtra("description", item.description)
        go.putExtra("price", item.price)
        go.putExtra("city", item.city)
        go.putExtra("tell", item.tell)
        go.putExtra("category", item.category)
        go.putExtra("date", item.date)
        go.putExtra("status", item.status)
        go.putExtra("img1", item.img1)
        go.putExtra("img2", item.img2)
        go.putExtra("img3", item.img3)
        startActivity(go)
       // Toast.makeText(requireContext(), item.city, Toast.LENGTH_SHORT).show()
    }
}