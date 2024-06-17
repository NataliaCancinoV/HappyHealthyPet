package uv.tc.happyhealthypet.poko

data class Mascota(
    val id: Int?,
    val correo: String,
    val nombreMascota: String,
    val edad: Int,
    val raza: String,
    val sexo: String,
    val fechaNacimiento: String
)
