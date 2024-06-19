package uv.tc.happyhealthypet

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import uv.tc.happyhealthypet.databinding.ActivityPrincipalBinding
import uv.tc.happyhealthypet.fragmentos.*

class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityPrincipalBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        drawerLayout = binding.drawerLayout

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, MedicineFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_medicamentos)
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val medicinesFragment = MedicineFragment()
        val vaccineFragment = VaccineFragment()
        val medicalAppointmentFragment = MedicalAppointmentFragment()
        val recommendationsFragment = RecommendationsFragment()
        val croquetasFragment = CroquetasFragment()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_medicamentos -> {
                    setCurrentFragment(medicinesFragment)
                    true
                }
                R.id.nav_vacunas -> {
                    setCurrentFragment(vaccineFragment)
                    true
                }
                R.id.nav_consultas -> {
                    setCurrentFragment(medicalAppointmentFragment)
                    true
                }
                R.id.nav_recomendaciones -> {
                    setCurrentFragment(recommendationsFragment)
                    true
                }
                R.id.nav_croquetas -> {
                    setCurrentFragment(croquetasFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> setCurrentFragment(HomeFragment())
            R.id.nav_profile -> setCurrentFragment(ProfileFragment())
            //R.id.nav_logout -> Toast.makeText(this, R.string.toast_logout, Toast.LENGTH_LONG).show()
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
