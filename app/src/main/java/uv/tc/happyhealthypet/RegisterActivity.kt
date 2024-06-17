package uv.tc.happyhealthypet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.happyhealthypet.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = getString(R.string.tv_registro)

        binding.imgBtnBack.setOnClickListener(){
            regresarLogin()
        }
    }

    fun regresarLogin(){
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        intent.putExtra("login","")
        startActivity(intent)
        finish()
    }
}