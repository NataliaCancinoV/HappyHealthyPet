package uv.tc.happyhealthypet.modelo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uv.tc.happyhealthypet.poko.Usuario

class UsuariosDB(contexto: Context) : SQLiteOpenHelper(contexto, NOMBRE_BD,null, VERSION_BD ) {
    companion object{
        private const val NOMBRE_BD = "HHPBD"
        private const val VERSION_BD = 1
        private const val NOMBRE_TABLA = "Usuarios"
        private const val COL_NOMBRE = "Nombre"
        private const val COL_CORREO = "Correo"
        private const val COL_PASSWORD = "Contrasena"
    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE_USUARIOS = ("CREATE TABLE $NOMBRE_TABLA ("+
                "$COL_CORREO TEXT PRIMARY KEY, " +
                "$COL_NOMBRE TEXT, " +
                "$COL_PASSWORD TEXT ") //La primera vez que se crea la base de datos al no estar creada ni instalada

        val values = ContentValues().apply {
            put(COL_CORREO, "nat@gmail.com")
            put(COL_NOMBRE, "Nat")
            put(COL_PASSWORD, "12345")
        }
        p0!!.execSQL(CREATE_TABLE_USUARIOS)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun agrergarUsuario(usuario: Usuario) : Long{
        val db = writableDatabase
        //Programacion Funcional para Kotlin
        val valoresInsert = ContentValues().apply {
            put(COL_CORREO, usuario.correo)
            put(COL_NOMBRE, usuario.nombre)
            put(COL_PASSWORD, usuario.password)
        }

        val filasAfectadas = db.insert(NOMBRE_TABLA, null, valoresInsert)
        db.close()

        return filasAfectadas
    }

    @SuppressLint("Range")
    fun seleccionarUsuarios() : List<Usuario> {
        val misUsuarios = mutableListOf<Usuario>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(NOMBRE_TABLA, null, null,
            null, null, null, null)
        if (resultadoConsulta != null){
            while (resultadoConsulta.moveToNext()){
                val correo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_CORREO))
                val nombre = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE))
                val password = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_PASSWORD))
                val usuario = Usuario(correo,nombre,password)
                misUsuarios.add(usuario)
            }
            resultadoConsulta.close()
        }
        db.close()
        return misUsuarios
    }

    @SuppressLint("Range")
    fun seleccionarMascotasPorUsuario(correo : String) : List<Usuario> {
        val misMascotas = mutableListOf<Usuario>()
        val db = readableDatabase
        val resultadoConsulta : Cursor = db.query(NOMBRE_TABLA, null, "$COL_CORREO = ?",
            arrayOf(correo), null, null, null)
        if (resultadoConsulta != null){
            while (resultadoConsulta.moveToNext()){
                val correo = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_CORREO))
                val nombre = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_NOMBRE))
                val password = resultadoConsulta.getString(resultadoConsulta.getColumnIndex(COL_PASSWORD))
                val usuario = Usuario(correo,nombre, password)
                misMascotas.add(usuario)
            }
            resultadoConsulta.close()
        }
        db.close()
        return misMascotas
    }

    fun actualizarUsuario(correo: String, nombre: String, contrasena: String, usuario: Usuario): Int{
        val db = writableDatabase
        val valoresUpdate = ContentValues().apply{
            put(COL_NOMBRE, usuario.nombre)
            put(COL_PASSWORD, usuario.password)
        }
        val filasAfectadas = db.update(NOMBRE_TABLA, valoresUpdate,"$COL_CORREO = ?",
            arrayOf(usuario.correo.toString()))
        db.close()
        return filasAfectadas
    }

    fun eliminarUsuario(correo: String): Int{
        val db = writableDatabase
        //No es necesario el ContentValue ya que solo se afecta un id
        val filasAfectadas = db.delete(NOMBRE_TABLA, "$COL_CORREO = ?", arrayOf(correo.toString()))
        db.close()
        return filasAfectadas
    }
}