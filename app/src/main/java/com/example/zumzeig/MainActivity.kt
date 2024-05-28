package com.example.zumzeig
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import fragments.CalendarFragment
import fragments.HomeFragment
import fragments.SavedFragment
import fragments.UserFragment


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var frameLayout: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bottomNavigationView = findViewById(R.id.bottomNavView)
        frameLayout = findViewById(R.id.frameLayout)

        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            val itemId = item.itemId
            when (itemId) {
                R.id.navHome -> loadFragment(HomeFragment(), false)
                R.id.navCalendar -> loadFragment(CalendarFragment(), false)
                R.id.navSaved -> loadFragment(SavedFragment(), false)
                else -> loadFragment(UserFragment(), false) // nav Profile
            }

            true
        }
        loadFragment(HomeFragment(), true)
    }

    private fun loadFragment(fragment: Fragment, isAppInitialized: Boolean) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment)
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment)
        }

        fragmentTransaction.commit()
    }
}