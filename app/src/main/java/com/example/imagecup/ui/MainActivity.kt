package com.example.imagecup.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.ViewGroupBindingAdapter.setListener
import androidx.fragment.app.Fragment
import com.example.imagecup.R
import com.example.imagecup.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initView()
        setListener()
    }
    private fun initView(){
        replaceFragment(GalleryFragment())
    }

    private fun setListener(){
        binding.mainBottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.photo -> {
                    replaceFragment(GalleryFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.album -> {
                    replaceFragment(AlbumFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.ranking -> {
                    replaceFragment(RankingFragment())
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_FL, fragment)
        fragmentTransaction.commit()
    }

    fun fragmentToFragment(int :Int){
        when(int){
            1 -> replaceFragment(GalleryFragment())
        }
    }
        }