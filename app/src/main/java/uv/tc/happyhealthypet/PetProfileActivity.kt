
package uv.tc.happyhealthypet

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uv.tc.happyhealthypet.databinding.ActivityPetProfileBinding
import uv.tc.happyhealthypet.modelo.MascotasDB
import uv.tc.happyhealthypet.poko.Mascota

class PetProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPetProfileBinding
    private lateinit var mascotas : ArrayList<Mascota>
    private lateinit var mascotasDB: MascotasDB
    private var correo = ""
    private  var edadMascota =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityPetProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mascotas= arrayListOf<Mascota>()
        mascotasDB= MascotasDB(this@PetProfileActivity)

        mascotas = mascotasDB.obtenerMascotas()

        val archivoPreferenciasDefault =getSharedPreferences("datosLogin", Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo","")

        //val mascota = Mascota(null, correoShared.toString(),nombre, edadMascota, raza, sexo, fechaNacimiento)
        binding.btnAgregarMascotaBd.setOnClickListener{
            val nombre = binding.etNombre.text.toString()
            val edad = binding.etEdad.text.toString()
            val raza = binding.etContrasena.text.toString() // Corregido el campo a etRaza
            val sexo = binding.etSexo.text.toString()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString()

            if (edad.isNotEmpty()) {
                edadMascota = edad.toInt()
            } else {
                Toast.makeText(this, "Por favor, ingrese la edad de la mascota", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mascota = Mascota(null, correoShared.toString(), nombre, edadMascota, raza, sexo, fechaNacimiento)
            agregarMascota(mascota)
            Toast.makeText(this@PetProfileActivity, "Mascota agregada: $nombre", Toast.LENGTH_SHORT).show()
        }
        binding.btnEditarMascota.setOnClickListener {
        //Toast.makeText(this@PetProfileActivity, "ID: ${mascotas[mascotas.size-1].id} NOMBRE: ${mascotas[mascotas.size-1].fechaNacimiento}", Toast.LENGTH_SHORT).show()
        }

    }
private fun agregarMascota ( mascota : Mascota){
    val resultado = mascotasDB.agregarMascotas(mascota)
    if(resultado>0){
        Toast.makeText(this@PetProfileActivity, "MASCOTA AGREGADA", Toast.LENGTH_SHORT).show()
    }else{
        Toast.makeText(this@PetProfileActivity, "ERROR AL AGREGAR MASCOTA", Toast.LENGTH_SHORT).show()

    }

}
}