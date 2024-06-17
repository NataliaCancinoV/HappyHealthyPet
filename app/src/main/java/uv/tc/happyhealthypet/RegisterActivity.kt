package uv.tc.happyhealthypet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.happyhealthypet.databinding.ActivityRegisterBinding
import uv.tc.happyhealthypet.modelo.UsuariosDB
import uv.tc.happyhealthypet.poko.Usuario

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var usuariosDB: UsuariosDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        usuariosDB = UsuariosDB(this)
        usuariosDB.crearTabla()

        binding.imgBtnBack.setOnClickListener(){
            regresarLogin()
        }

        binding.btnRegistrarse.setOnClickListener{
            val nombre = binding.etNombre.text.toString()
            val correo = binding.etCorreo.text.toString()
            val contrasena = binding.etContrasena.text.toString()
            val contrasenaConfirmacion = binding.etContrasenaConfirmacion.text.toString()

            if (validarCampos(nombre,correo,contrasena)==true){
                if (contrasena == contrasenaConfirmacion){
                    val usuario = Usuario(nombre,correo,contrasena)
                    registrarUsuario(usuario)
                }else{
                    binding.etContrasenaConfirmacion.error = "Las contraseñas no coinciden"
                }

            }
        }
    }

    fun validarCampos(nombre: String, correo: String, contrasena: String): Boolean{
        var validar = false
        if ((nombre.isNotEmpty() && correo.isNotEmpty()) && (contrasena.isNotEmpty())){
            validar = true
        }
        else{
            if (nombre.isEmpty()){
                binding.etNombre.error = "Ingresa un nombre"
            }
            if (correo.isEmpty()){
                binding.etCorreo.error = "Ingresa un correo"
            }
            if (contrasena.isEmpty()){
                binding.etContrasena.error = "Ingresa una contrasena"
            }
        }
        return validar
    }

    fun registrarUsuario(usuario: Usuario){
        val valoresInsert = usuariosDB.agregarUsuario(usuario)
        var alerta = ""

        if (valoresInsert>0){
            alerta = "Registro Exitoso"
            Handler(Looper.getMainLooper()).postDelayed({
                regresarLogin()
            }, 3000)
            mostrarToast(alerta)
        }else{
            alerta = "Registro Fallido"
            mostrarToast(alerta)
        }
    }

    fun mostrarToast(mensaje: String){
        Toast.makeText(this@RegisterActivity, mensaje, Toast.LENGTH_LONG).show()
    }
    fun regresarLogin(){
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        intent.putExtra("login","")
        startActivity(intent)
        finish()
    }
}