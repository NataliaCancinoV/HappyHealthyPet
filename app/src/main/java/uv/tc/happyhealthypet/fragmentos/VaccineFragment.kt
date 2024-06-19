package uv.tc.happyhealthypet.fragmentos

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uv.tc.happyhealthypet.AlarmReceiver
import uv.tc.happyhealthypet.R
import uv.tc.happyhealthypet.adaptadores.RecycleRecordatorioAdapter
import uv.tc.happyhealthypet.interfaces.ListenerRecyclerRecordatorios
import uv.tc.happyhealthypet.modelo.RecordatoriosDB
import uv.tc.happyhealthypet.poko.Recordatorio
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class VaccineFragment : Fragment(), ListenerRecyclerRecordatorios {
    private lateinit var recordatoriosDB: RecordatoriosDB
    private lateinit var listaRecordatorios: ArrayList<Recordatorio>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleRecordatorioAdapter
    private var contador = 1

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Permisión concedida", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Permisión denegada", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestExactAlarmPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            Toast.makeText(requireContext(), "Permiso de alarma exacta concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Permiso de alarma exacta denegado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaccine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordatoriosDB = RecordatoriosDB(requireContext())
        val archivoPreferenciasDefault = requireContext().getSharedPreferences("datosLogin", Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo", "")

        recyclerView = view.findViewById(R.id.recycler_vacunas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        listaRecordatorios = arrayListOf()
        listaRecordatorios = recordatoriosDB.obtenerRecordatorios(correoShared.toString(), "Vacunas")
        val recordatorio = Recordatorio(null, correoShared.toString(), 1, "Vacunas",
            "Kia", "MedicinaDefault", "12:00", "10/07/2024")
        val btnAgregarVacuna: FloatingActionButton = view.findViewById(R.id.btn_agregar_recordatorio_vacuna)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                    // Permisión ya concedida
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Mostrar una explicación al usuario
                }
                else -> {
                    // Solicitar la permisión
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                requestExactAlarmPermissionLauncher.launch(intent)
            }
        }

        btnAgregarVacuna.setOnClickListener {
            showDateTimePicker()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter = RecycleRecordatorioAdapter(listaRecordatorios, false, this)
        recyclerView.adapter = adapter
    }

    private fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        val date = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            date.set(year, monthOfYear, dayOfMonth)
            TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                date.set(Calendar.MINUTE, minute)
                date.set(Calendar.SECOND, 0)
                setAlarm(date.timeInMillis)
                addRecordatorio(date.timeInMillis)
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show()
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show()
    }

    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

    private fun addRecordatorio(timeInMillis: Long) {
        val archivoPreferenciasDefault = requireContext().getSharedPreferences("datosLogin", Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo", "") ?: return

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val dateString = sdf.format(Date(timeInMillis))

        val recordatorio = Recordatorio(
            null,
            correoShared,
            1,
            "Vacunas",
            "Kia",
            "VacunaDefault",
            dateString.split(" ")[1], // Hora
            dateString.split(" ")[0]  // Fecha
        )

        listaRecordatorios.add(recordatorio)
        adapter.notifyItemInserted(listaRecordatorios.size - 1)

        val resultado = recordatoriosDB.agregarRecordatorio(recordatorio)
        if (resultado > 0) {
            Toast.makeText(requireContext(), "RECORDATORIO VACUNA AGREGADO", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "ERROR AL AGREGAR RECORDATORIO", Toast.LENGTH_SHORT).show()
        }
    }

    override fun clicEditarRecordatorio(
        position: Int,
        nombreRecordatorio: String,
        fechaRecordatorio: String,
        horarioRecordatorio: String,
        nombreMascota: String
    ) {
        adapter = RecycleRecordatorioAdapter(listaRecordatorios, true, this)
        recyclerView.adapter = adapter
        val recordatorio = listaRecordatorios[position]
        Toast.makeText(requireContext(), "ID DEL RECORDATORIO : ${recordatorio.id}", Toast.LENGTH_SHORT).show()
        val archivoPreferenciasDefault = requireContext().getSharedPreferences("datosLogin", Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo", "")
        if (contador % 2 == 0) {
            val nuevoRecodatorio = recordatoriosDB.actualizarRecordatorio(
                recordatorio.id!!,
                nombreRecordatorio,
                fechaRecordatorio,
                horarioRecordatorio,
                nombreMascota
            )
            if (nuevoRecodatorio > 0) {
                Toast.makeText(requireContext(), "DATOS EDITADOS CORRECTAMENTE", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "ERROR AL EDITAR DATOS", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "DATOS PARA EDITAR", Toast.LENGTH_SHORT).show()
        }
        contador++
    }

    override fun clicEliminarRecordatorio(idRecordatorio: Recordatorio, position: Int) {
        // Implementa la lógica para eliminar un recordatorio si es necesario
    }
}
