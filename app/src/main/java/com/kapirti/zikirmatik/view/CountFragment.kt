package com.kapirti.zikirmatik.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kapirti.zikirmatik.R
import com.kapirti.zikirmatik.databinding.FragmentCountBinding
import com.kapirti.zikirmatik.databinding.FragmentLibraryBinding

class CountFragment : Fragment() {
    private lateinit var binding: FragmentCountBinding
    var count=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCountBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setCount()
        buttonHandler()
        binding.buttonDelete.setOnClickListener {
            count=0
            binding.textView.text=Integer.toString(count)
            val sharedPreferences=requireContext().getSharedPreferences("count", Context.MODE_PRIVATE)
            val preferences:SharedPreferences.Editor=sharedPreferences.edit()
            preferences.clear().commit()
        }

    }
    private fun setCount(){
        val sharedPreferences=requireContext().getSharedPreferences("count",Context.MODE_PRIVATE)
        count=sharedPreferences.getInt("count",0)
        if (count==0){
            binding.textView.text="0"
        }else{
            binding.textView.text=Integer.toString(count)
        }
    }
    fun buttonHandler() {
        val sharedPreferences = requireContext().getSharedPreferences("count", Context.MODE_PRIVATE)
        binding.buttonAdd.setOnClickListener {
            if (count == 0) {
                count = sharedPreferences.getInt("count", 0)
                count++
                binding.textView.text = Integer.toString(count)
            } else {
                count++
                binding.textView.text = Integer.toString(count)
            }
            sharedPreferences.edit().putInt("count", count).apply()
        }
    }
}