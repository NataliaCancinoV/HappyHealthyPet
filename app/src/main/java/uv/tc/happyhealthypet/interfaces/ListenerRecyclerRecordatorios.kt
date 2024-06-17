package uv.tc.happyhealthypet.interfaces

import uv.tc.happyhealthypet.poko.Recordatorio

interface ListenerRecyclerRecordatorios {
    fun clicEditarRecordatorio(idRecordatorio: Recordatorio, position: Int)
    fun clicEliminarRecordatorio(idRecordatorio: Recordatorio, position: Int)
}