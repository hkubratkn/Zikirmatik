package com.kapirti.zikirmatik.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kapirti.zikirmatik.databinding.FragmentLibraryBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kapirti.zikirmatik.adapter.ZikirRecyclerAdapter
import com.kapirti.zikirmatik.viewmodel.LibraryViewModel

class LibraryFragment : Fragment() {
    private lateinit var binding:FragmentLibraryBinding
    private lateinit var viewModel:LibraryViewModel
    private val recyclerZikirAdapter=ZikirRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLibraryBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(this).get(LibraryViewModel::class.java)
        viewModel.takeDataFromRoom()

        binding.recyclerView.layoutManager=LinearLayoutManager(context)
        binding.recyclerView.adapter=recyclerZikirAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            with(binding) {
                progressBar.visibility = View.VISIBLE
                errorEditText.visibility = View.GONE
                recyclerView.visibility = View.GONE
                viewModel.takeDataFromInternet()
                swipeRefreshLayout.isRefreshing = false
            }
        }
        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.zkdescription.observe(viewLifecycleOwner,Observer{zk->
            zk?.let {
                binding.recyclerView.visibility=View.VISIBLE
                recyclerZikirAdapter.loopZikir(zk)
            }
        })
        viewModel.error.observe(viewLifecycleOwner,Observer{error->
            error?.let {
                if (it) {
                    with(binding) {
                        errorEditText.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                } else {
                    binding.errorEditText.visibility = View.GONE
                }

            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer{loading->
            loading?.let{
                if(it){
                    with(binding){
                        recyclerView.visibility=View.GONE
                        errorEditText.visibility=View.GONE
                        progressBar.visibility=View.VISIBLE
                    }
                }else{
                    binding.progressBar.visibility=View.GONE
                }
            }
        })
    }
}