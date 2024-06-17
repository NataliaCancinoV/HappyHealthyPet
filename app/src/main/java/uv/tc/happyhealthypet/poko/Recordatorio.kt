package uv.tc.happyhealthypet.poko

data class Recordatorio(
    val id: Int,
    val correo: String,
    val idMascota: Int,
    val tipoRecordatorio: String,
    val mascotaAsociada: String,
    val nombreRecordatorio: String,
    val horario: String,
    val fecha: String
)
