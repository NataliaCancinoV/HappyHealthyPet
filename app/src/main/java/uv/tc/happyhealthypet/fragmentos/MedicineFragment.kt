package uv.tc.happyhealthypet.fragmentos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uv.tc.happyhealthypet.NavActivity
import uv.tc.happyhealthypet.R
import uv.tc.happyhealthypet.adaptadores.RecycleRecordatorioAdapter
import uv.tc.happyhealthypet.interfaces.ListenerRecyclerRecordatorios
import uv.tc.happyhealthypet.modelo.RecordatoriosDB
import uv.tc.happyhealthypet.poko.Recordatorio

class MedicineFragment : Fragment(), ListenerRecyclerRecordatorios {
    private lateinit var recordatoriodDB: RecordatoriosDB
    private lateinit var listaRecordatorios: ArrayList<Recordatorio>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleRecordatorioAdapter
    private var correo = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //correo = arguments?.getString("email")!!
        Toast.makeText(requireContext(),"correo=$correo",Toast.LENGTH_LONG).show()
        recordatoriodDB= RecordatoriosDB(requireContext())

        val btnAgregar: FloatingActionButton = view.findViewById(R.id.btn_agregar_recordatorio)


        recyclerView=view.findViewById(R.id.recycler_medicamentos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        listaRecordatorios = arrayListOf<Recordatorio>()
        var recordatorio = Recordatorio(null,"",1,"Medicamentos",
            "Kia","MedicinaMareo","12:00","10/07/2024")

        btnAgregar.setOnClickListener{
            listaRecordatorios.add(recordatorio)
            adapter= RecycleRecordatorioAdapter(listaRecordatorios,false, this)
            recyclerView.adapter = adapter
        }

    }

    override fun onResume() {
        super.onResume()
        adapter= RecycleRecordatorioAdapter(listaRecordatorios,false, this)
        recyclerView.adapter = adapter
    }

    fun navDrawer(){
        val intent = Intent(requireContext(), NavActivity::class.java)
        intent.putExtra("navDrawer", "nav")
        startActivity(intent)
    }

    override fun clicEditarRecordatorio(position: Int) {
        adapter= RecycleRecordatorioAdapter(listaRecordatorios,true, this)
        recyclerView.adapter = adapter
    }

    override fun clicEliminarRecordatorio(idRecordatorio: Recordatorio, position: Int) {

    }

}