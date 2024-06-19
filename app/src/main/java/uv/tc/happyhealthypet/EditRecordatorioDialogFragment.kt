package uv.tc.happyhealthypet.fragmentos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import uv.tc.happyhealthypet.databinding.DialogEditRecordatorioBinding
import uv.tc.happyhealthypet.poko.Recordatorio
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditRecordatorioDialogFragment(
    private val recordatorio: Recordatorio,
    private val onRecordatorioUpdated: (Recordatorio) -> Unit
) : DialogFragment() {
    private var _binding: DialogEditRecordatorioBinding? = null
    private val binding get() = _binding!!

    private var newFecha: Long = recordatorio.fecha.toLongOrNull() ?: 0L
    private var newHora: Long = recordatorio.horario.toLongOrNull() ?: 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogEditRecordatorioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etNombreRecordatorio.setText(recordatorio.nombreRecordatorio)
        binding.etFechaRecordatorio.setText(recordatorio.fecha)
        binding.etHoraRecordatorio.setText(recordatorio.horario)

        binding.etFechaRecordatorio.setOnClickListener {
            showDatePicker()
        }

        binding.etHoraRecordatorio.setOnClickListener {
            showTimePicker()
        }

        binding.btnSave.setOnClickListener {
            val newName = binding.etNombreRecordatorio.text.toString()
            if (newName.isEmpty()) {
                Toast.makeText(requireContext(), "El nombre no puede estar vacÃo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val fecha = sdf.format(newFecha)
            val hora = timeFormat.format(newHora)

            val updatedRecordatorio = recordatorio.copy(
                nombreRecordatorio = newName,
                fecha = fecha,
                horario = hora
            )

            onRecordatorioUpdated(updatedRecordatorio)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->
            calendar.set(year, monthOfYear, dayOfMonth)
            newFecha = calendar.timeInMillis
            binding.etFechaRecordatorio.setText("$dayOfMonth/${monthOfYear + 1}/$year")
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            newHora = calendar.timeInMillis
            binding.etHoraRecordatorio.setText("$hourOfDay:$minute")
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}