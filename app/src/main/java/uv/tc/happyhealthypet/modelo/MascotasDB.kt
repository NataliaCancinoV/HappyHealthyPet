package uv.tc.happyhealthypet.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uv.tc.happyhealthypet.poko.Mascota

class MascotasDB(context: Context):SQLiteOpenHelper(context, NOMBRE_BD,null, VERSION_BD) {
    companion object{
        private const val NOMBRE_BD = "HHPBD"
        private const val VERSION_BD = 1
        private const val NOMBRE_TABLA = "Mascotas"
        private const val COL_ID_MASCOTA = "IdMascota"
        private const val COL_NOMBRE = "NombreMascota"
        private const val COL_EDAD = "Edad"
        private const val COL_RAZA = "Raza"
        private const val COL_SEXO = "Sexo"
        private const val COL_FECHA_NACIMIENTO = "FechaNacimiento"
        private const val COL_USUARIO_CORREO = "CorreoUsuario"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        var CREATE_TABLE_MASCOTAS = "CREATE TABLE $NOMBRE_TABLA(" +
                "$COL_ID_MASCOTA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_USUARIO_CORREO TEXT, "+
                "$COL_NOMBRE TEXT, " +
                "$COL_EDAD INTEGER, " +
                "$COL_RAZA TEXT, " +
                "$COL_SEXO TEXT, " +
                "$COL_FECHA_NACIMIENTO TEXT, " +
                "foreign key ($COL_USUARIO_CORREO) references Usuarios(Correo)) "//La primera vez que se crea la base de datos al no estar creada ni instalada

        p0!!.execSQL(CREATE_TABLE_MASCOTAS)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun crearTabla(){
        val db = writableDatabase
        var valor = db.execSQL("CREATE TABLE IF NOT EXISTS $NOMBRE_TABLA(" +
                "$COL_ID_MASCOTA INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_USUARIO_CORREO TEXT, "+
                "$COL_NOMBRE TEXT, " +
                "$COL_EDAD INTEGER, " +
                "$COL_RAZA TEXT, " +
                "$COL_SEXO TEXT, " +
                "$COL_FECHA_NACIMIENTO TEXT, " +
                "foreign key ($COL_USUARIO_CORREO) references Usuarios(Correo))" )
        return valor
    }

    fun agregarMascotas(mascota: Mascota) : Long{
        val db = writableDatabase
        //Programacion Funcional para Kotlin
        val valoresInsert = ContentValues().apply {
            put(COL_ID_MASCOTA, mascota.id )
            put(COL_USUARIO_CORREO, mascota.correo)
            put(COL_NOMBRE, mascota.nombreMascota)
            put(COL_EDAD, mascota.edad)
            put(COL_RAZA, mascota.raza)
            put(COL_SEXO, mascota.sexo)
            put(COL_FECHA_NACIMIENTO, mascota.fechaNacimiento)
        }

        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()

        return filasAfectadas
    }

    @SuppressLint("Range")
    fun obtenerMascotas() : List<Mascota> {
        val misMascotas = mutableListOf<Mascota>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(NOMBRE_TABLA, null, null, null, null, null, null)
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
    }

    @SuppressLint("Range")
    fun seleccionarMascotasUsuario(correo : String) : List<Mascota> {
        val misMascotas = mutableListOf<Mascota>()
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
    }

}