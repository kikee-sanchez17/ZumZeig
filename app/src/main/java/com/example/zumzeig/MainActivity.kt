package com.example.zumzeig

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.zumzeig.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import fragments.BillboardFragment
import fragments.BistrotFragment
import fragments.BillboardFragment
import fragments.CalendarFragment
import fragments.HomeFragment
import fragments.InformacioStatic
import fragments.ParticipateFragment
import fragments.RentalFragment
import fragments.SavedFragment
import fragments.SchoolFragment
import fragments.UserFragment
import libraries.FunctionUtility

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        bottomNavigationView = findViewById(R.id.bottomNavView)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
                R.id.navHome -> loadFragment(HomeFragment(), false)
                R.id.navCalendar -> loadFragment(CalendarFragment(), false)
                R.id.navSaved -> loadFragment(SavedFragment(), false)
                else -> loadFragment(BillboardFragment(supportFragmentManager), false) // nav Profile
            }


        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navHome -> FunctionUtility().loadFragment(supportFragmentManager,HomeFragment(), true)
                R.id.navCalendar -> FunctionUtility().loadFragment(supportFragmentManager,BillboardFragment(supportFragmentManager), true)
                R.id.navSaved -> FunctionUtility().loadFragment(supportFragmentManager,SavedFragment(), true)
                else -> FunctionUtility().loadFragment(supportFragmentManager, UserFragment(supportFragmentManager),true) // nav Profile
            }
            true
        }

        FunctionUtility().loadFragment(supportFragmentManager,HomeFragment(), false)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> FunctionUtility().loadFragment(supportFragmentManager,HomeFragment(), true)
            R.id.nav_billboard -> FunctionUtility().loadFragment(supportFragmentManager,BillboardFragment(supportFragmentManager), true)
            R.id.nav_calendar -> FunctionUtility().loadFragment(supportFragmentManager,CalendarFragment(), true)
            R.id.nav_info -> FunctionUtility().loadFragment(supportFragmentManager,InformacioStatic(), true)
            R.id.nav_perfil -> FunctionUtility().loadFragment(supportFragmentManager,UserFragment(supportFragmentManager), true)
            R.id.nav_participate -> FunctionUtility().loadFragment(supportFragmentManager,ParticipateFragment(), true)
            R.id.nav_school -> FunctionUtility().loadFragment(supportFragmentManager,SchoolFragment(), true)
            R.id.nav_rental -> FunctionUtility().loadFragment(supportFragmentManager,RentalFragment(), true)
            R.id.nav_bistrot -> FunctionUtility().loadFragment(supportFragmentManager,BistrotFragment(), true)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
