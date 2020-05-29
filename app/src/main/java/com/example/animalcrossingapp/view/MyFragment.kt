package com.example.animalcrossingapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import kotlinx.android.synthetic.main.activity_all_list.*
import kotlinx.android.synthetic.main.fragment_myfragment.*
import kotlinx.android.synthetic.main.fragment_myfragment.gridView2

class MyFragment() : Fragment() {
    companion object {
        private const val EXTRA_POSITION = "extra_position"




        fun newInstance(position: Int) : Fragment {
            val fragment = MyFragment()
            val args = Bundle()

            args.putInt(EXTRA_POSITION, position)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_myfragment, container, false)




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pdb = AnimalCrossingDB.getInstance(requireContext())!!

        val realTimeList = pdb.animalCrossingDao().selectCurrentAnimal("北半球", "8", "1月")
        var imgArr = Array(realTimeList.size, {0})
        var idx = 0
        realTimeList.forEach {
            var id = it.information_code
            imgArr[idx] = this.getResources().getIdentifier(id, "drawable", requireContext().getPackageName())
            idx++
        }
        val griviewAdapter = GridviewAdapter(requireContext(), imgArr)
        gridView2.adapter = griviewAdapter



        val position = arguments?.getInt(EXTRA_POSITION, -1)?: -1
        /*fr_myfragment_position.text = position.toString()*/


    }
}
