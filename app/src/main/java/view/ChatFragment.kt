package view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.divar.R
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.toolbar.*

class ChatFragment : Fragment(){

    private val cityArray = listOf("کردستان", "تهران", "اردبیل")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_two.setOnClickListener {
            Toast.makeText(requireContext(), "aaaa", Toast.LENGTH_SHORT).show()

        }

        /*==================================spinner city======================================*/
        val adapterCity = ArrayAdapter<String>(requireContext(),
            R.layout.dropdown_menu, cityArray)
        spinner_city.adapter = adapterCity

        spinner_city.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedPosition: Int = spinner_city.selectedItemPosition
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        })
    }
}