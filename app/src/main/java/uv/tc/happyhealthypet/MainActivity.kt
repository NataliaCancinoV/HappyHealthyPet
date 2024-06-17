package uv.tc.happyhealthypet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import uv.tc.happyhealthypet.databinding.ActivityMainBinding
import uv.tc.happyhealthypet.fragmentos.CroquetasFragment
import uv.tc.happyhealthypet.fragmentos.MedicalAppointmentFragment
import uv.tc.happyhealthypet.fragmentos.MedicineFragment
import uv.tc.happyhealthypet.fragmentos.RecommendationsFragment
import uv.tc.happyhealthypet.fragmentos.VaccineFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  var correo=""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //val archivoPreferenciasDefault = getPreferences(Context.MODE_PRIVATE)
        val archivoPreferenciasDefault = getSharedPreferences("datosLogin",Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo","")
        Toast.makeText(this, "CORREO SHARED MAIN : ${correoShared}", Toast.LENGTH_SHORT).show()

       // correo = intent.getStringExtra("correo")!!

       // Toast.makeText(this,"correo $correo", Toast.LENGTH_LONG).show()
        val medicinesFragment = MedicineFragment()
        val vaccineFragment = VaccineFragment()
        val medicalAppointmentFragment = MedicalAppointmentFragment()
        val recommendationsFragment = RecommendationsFragment()
        val croquetasFragment = CroquetasFragment()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_medicamentos -> {
                    setCurrentFragment(MedicineFragment())
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

        //navDrawer()

    }
    override fun onStart() {
        super.onStart()
        Log.i("Ciclo", "Dentro del metodo onStart de la actividad")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Ciclo", "Dentro del metodo onResume de la actividad")
    }


    fun navDrawer(){
        val intent = Intent(this@MainActivity, NavActivity::class.java)
        intent.putExtra("navDrawer", "nav")
        startActivity(intent)
        //finish()
    }

    private fun setCurrentFragment(fragment: Fragment){
        val bundle = Bundle()
        bundle.putString("correo", correo)
        fragment.arguments=bundle
        Toast.makeText(this@MainActivity, "BUNGLE CORREO:  ${correo}", Toast.LENGTH_SHORT).show()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment).addToBackStack(null).commit()
    }

}