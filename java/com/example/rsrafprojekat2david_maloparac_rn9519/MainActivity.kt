package com.example.rsrafprojekat2david_maloparac_rn9519

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.example.rsrafprojekat2david_maloparac_rn9519.data.source.SavedDishDataBase
import com.example.rsrafprojekat2david_maloparac_rn9519.databinding.ActivityMainBinding
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.adapter.MainPagerAdapter
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment.ListFragment
import com.example.rsrafprojekat2david_maloparac_rn9519.presentation.view.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, MainFragment())
        transaction.commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount;

        if(count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}