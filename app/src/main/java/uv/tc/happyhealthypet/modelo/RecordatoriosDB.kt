package uv.tc.happyhealthypet.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uv.tc.happyhealthypet.poko.Recordatorio

class RecordatoriosDB(context: Context):SQLiteOpenHelper(context, NOMBRE_BD,null, VERSION_BD) {
    companion object{
        private const val NOMBRE_BD = "HHPBD"
        private const val VERSION_BD = 1
        private const val NOMBRE_TABLA = "Recordatorios"

        private const val COL_ID_RECORDATORIO = "IdRecordatorio"
        private const val COL_CORREO_USUARIO = "CorreoUsuario"
        private const val COL_ID_MASCOTA = "IdMascota"
        private const val COL_TIPO_RECORDATORIO = "TipoRecordatorio"
        private const val COL_NOMBRE_MASCOTA = "NombreMascota"
        private const val COL_NOMBRE_RECORDATORIO = "NombreRecordatorio"
        private const val COL_HORARIO = "Horario"
        private const val COL_FECHA = "Fecha"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        var CREATE_TABLE_MASCOTAS = "CREATE TABLE $NOMBRE_TABLA(" +
                "$COL_ID_RECORDATORIO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_CORREO_USUARIO TEXT, "+
                "$COL_ID_MASCOTA TEXT, " +
                "$COL_TIPO_RECORDATORIO TEXT, " +
                "$COL_NOMBRE_MASCOTA TEXT, " +
                "$COL_NOMBRE_RECORDATORIO TEXT, " +
                "$COL_HORARIO TEXT, " +
                "$COL_FECHA TEXT, " +
                "foreign key ($COL_CORREO_USUARIO) references Usuarios(Correo), " +
                "foreign key ($COL_ID_MASCOTA) references Mascotas(IdMascota))" //La primera vez que se crea la base de datos al no estar creada ni instalada

        p0!!.execSQL(CREATE_TABLE_MASCOTAS)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun crearTabla(){
        val db = writableDatabase
        var valor = db.execSQL("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA(" +
                "$COL_ID_RECORDATORIO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_CORREO_USUARIO TEXT, "+
                "$COL_ID_MASCOTA TEXT, " +
                "$COL_TIPO_RECORDATORIO TEXT, " +
                "$COL_NOMBRE_MASCOTA TEXT, " +
                "$COL_NOMBRE_RECORDATORIO TEXT, " +
                "$COL_HORARIO TEXT, " +
                "$COL_FECHA TEXT, " +
                "foreign key ($COL_CORREO_USUARIO) references Usuarios(Correo), " +
                "foreign key ($COL_ID_MASCOTA) references Mascotas(IdMascota))")
        return valor
    }

    fun agregarRecordatorio(recordatorio: Recordatorio) : Long{
        val db = writableDatabase
        //Programacion Funcional para Kotlin
        val valoresInsert = ContentValues().apply {
            put(COL_ID_RECORDATORIO, recordatorio.id )
            put(COL_CORREO_USUARIO, recordatorio.correo)
            put(COL_ID_MASCOTA, recordatorio.idMascota)
            put(COL_TIPO_RECORDATORIO, recordatorio.tipoRecordatorio)
            put(COL_NOMBRE_MASCOTA, recordatorio.mascotaAsociada)
            put(COL_NOMBRE_RECORDATORIO, recordatorio.nombreRecordatorio)
            put(COL_HORARIO, recordatorio.horario)
            put(COL_FECHA, recordatorio.fecha)
        }

        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()

        return filasAfectadas
    }

    @SuppressLint("Range")
    fun obtenerRecordatorios(correo: String) : List<Recordatorio> {
        val misMascotas = mutableListOf<Recordatorio>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(NOMBRE_TABLA, null, "$COL_CORREO_USUARIO=?", arrayOf(correo), null, null, null)
        if (resultadoConsulta != null){
            while (resultadoConsulta.moveToNext()){
                val idRecordatorio = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_RECORDATORIO))
                val correoUsuario = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_CORREO_USUARIO))
                val idMascota = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_MASCOTA))
                val tipoRecordatorio = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_TIPO_RECORDATORIO))
                val nombreMascota = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE_MASCOTA))
                val nombreRecordatorio = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE_RECORDATORIO))
                val horario = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_HORARIO))
                val fecha = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_FECHA))
                val recordatorio = Recordatorio(idRecordatorio,correoUsuario,idMascota,tipoRecordatorio,nombreMascota,nombreRecordatorio,horario,fecha)
                misMascotas.add(recordatorio)
            }
            resultadoConsulta.close()
        }
        db.close()
        return misMascotas
    }
    /*
    @SuppressLint("Range")
    fun editarRecordatorio(correo : String) : List<Recordatorio> {
        val misMascotas = mutableListOf<Recordatorio>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(NOMBRE_TABLA, null, "$COL_USUARIO_CORREO = ?",
            arrayOf(correo), null, null, null)
        if (resultadoConsulta != null){
            while (resultadoConsulta.moveToNext()){
                val idMascota = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_ID_MASCOTA))
                val correo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_USUARIO_CORREO))
                val nombre = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE))
                val edad = resultadoConsulta.getInt(resultadoConsulta.getColumnIndex(COL_EDAD))
                val raza = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_RAZA))
                val sexo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_SEXO))
                val fechaNacimiento = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_FECHA_NACIMIENTO))
                val mascota = Mascota(idMascota,correo,nombre,edad,raza,sexo,fechaNacimiento)
                misMascotas.add(mascota)
            }
            resultadoConsulta.close()
        }
        db.close()
        return misMascotas
    }*/
}