package uv.tc.happyhealthypet

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.happyhealthypet.databinding.ActivityLoginBinding
import uv.tc.happyhealthypet.modelo.MascotasDB
import uv.tc.happyhealthypet.modelo.UsuariosDB
import java.util.zip.Inflater

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var usuariosDB: UsuariosDB
    private lateinit var mascotasDB: MascotasDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        usuariosDB = UsuariosDB(this@LoginActivity)

        setContentView(view)
        mascotasDB= MascotasDB(this@LoginActivity)
        mascotasDB.crearTabla()
        cargarCredenciales()

        binding.btnIniciarSesion.setOnClickListener {
            val correo = binding.etCorreo.text.toString()
            val contrasena = binding.etContrasena.text.toString()

            if (verificarCredenciales(correo,contrasena)==true){
                if (usuariosDB.validarUsuario(correo,contrasena)==true){
                    if (binding.cbRecordarCuenta.isChecked)
                        guardarCredenciales(correo, contrasena, true)
                    else
                        guardarCredenciales("","",false)

                    Toast.makeText(this@LoginActivity, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                    irPantallaInicio(correo)
                }else{
                    Toast.makeText(this@LoginActivity, "Datos incorrectos", Toast.LENGTH_LONG).show()
                }

            }
        }

        binding.tvRegistrate.setOnClickListener {
            irPantallaRegistro()
        }


    }

    fun cargarCredenciales(){
        val archivoPreferenciasDefault = getSharedPreferences("datosLogin",Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo","")
        binding.etCorreo.setText(archivoPreferenciasDefault.getString("correo",""))
        Toast.makeText(this@LoginActivity, "CORREO SHARED : ${correoShared}", Toast.LENGTH_SHORT).show()
        binding.etContrasena.setText(archivoPreferenciasDefault.getString("password",""))
        if (archivoPreferenciasDefault.getBoolean("guardar",false))
            binding.cbRecordarCuenta.isChecked = true
    }

    fun guardarCredenciales(correo: String, password: String, guardado: Boolean){
        val archivoPreferenciasDefault = getSharedPreferences("datosLogin",Context.MODE_PRIVATE)
        with(archivoPreferenciasDefault.edit()){
            putString("correo", correo)
            putString("password", password)
            putBoolean("guardar",guardado)
            apply()
        }
        Toast.makeText(this@LoginActivity,"CREDENCIALES GUARDADAS",Toast.LENGTH_SHORT).show()

    }

    fun verificarCredenciales(correo: String, password: String):Boolean{
        var esValido = true
        if (correo.isEmpty()){
            binding.etCorreo.error= "Ingrese un correo"
            esValido = false
        }
        if (password.isEmpty()){
            binding.etContrasena.error = "Ingresa una contraseña"
            esValido = false
        }
        return esValido
    }

    fun irPantallaInicio(correo: String){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra("correo", binding.etCorreo.text.toString())
        startActivity(intent)
        finish()
    }

    fun irPantallaRegistro(){
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        intent.putExtra("registro", "")
        startActivity(intent)
    }
}