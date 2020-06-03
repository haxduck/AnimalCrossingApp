package com.example.animalcrossingapp.toolbar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.controller.TabLayoutAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.view.MainActivity
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.view.*
/**
 * A simple [Fragment] subclass.
 */
class SecondFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val context: Context = requireContext()
        val pageAdapter : PagerAdapter = TabLayoutAdapter(childFragmentManager)


        view.tabLayoutViewPager.adapter = pageAdapter
        view.tabLayout.setupWithViewPager(view.tabLayoutViewPager)

//        view.tabLayoutViewPager.adapter = TabLayoutAdapter(childFragmentManager)
//        view.tabLayout.setupWithViewPager(view.tabLayoutViewPager)

      /*  val intent = Intent (activity, SecondFragment::class.java)

        val db = AnimalCrossingDB.getInstance(context)!!
        var list = arrayListOf<Current>()*/

       /* try{
            list.addAll(arguments?.getParcelableArrayList("list")!!)
        }catch(e: KotlinNullPointerException){
            var clist = db.animalCrossingDao().selectAll()
            clist.forEach {
                list.add(it)
            }
        }*/
        /*if (list.addAll(arguments?.getParcelableArrayList("list")!!) == null ){
            var clist = db.animalCrossingDao().selectAll()
            clist.forEach {
                list.add(it)
            }
        } else {
            list.addAll(arguments?.getParcelableArrayList("list")!!)
        }*/

        /*view.recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CurrentAdapter(list) { animal ->
            }
        }*/

        return view
    }
    /*companion object {
        val TAG = SecondFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = SecondFragment()
    }*/


}
