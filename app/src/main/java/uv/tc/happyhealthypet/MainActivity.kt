package uv.tc.happyhealthypet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.happyhealthypet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //navDrawer()
        irLoginActividad()
    }
    override fun onStart() {
        super.onStart()
        Log.i("Ciclo", "Dentro del metodo onStart de la actividad")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Ciclo", "Dentro del metodo onResume de la actividad")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Ciclo", "Dentro del metodo onPause de la actividad")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Ciclo","Dentro del metodo onStop de la actividad")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Ciclo","Dentro del metodo onDestroy de la actividad")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Ciclo", "Dentro del metodo onRestart de la actividad")
    }

    fun navDrawer(){
        val intent = Intent(this@MainActivity, NavActivity::class.java)
        intent.putExtra("navDrawer", "nav")
        startActivity(intent)
        //finish()
    }

    fun irLoginActividad(){
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        intent.putExtra("login", "log")
        startActivity(intent)
        finish()
    }
}