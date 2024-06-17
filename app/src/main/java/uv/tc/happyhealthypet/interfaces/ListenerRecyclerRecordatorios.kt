package uv.tc.happyhealthypet.interfaces

import uv.tc.happyhealthypet.poko.Recordatorio

interface ListenerRecyclerRecordatorios {
    fun clicEditarRecordatorio(position: Int,  nombreRecordatorio : String, fechaRecordatorio : String, horarioRecordatorio : String, nombreMascota : String)
    fun clicEliminarRecordatorio(idRecordatorio: Recordatorio, position: Int)
}