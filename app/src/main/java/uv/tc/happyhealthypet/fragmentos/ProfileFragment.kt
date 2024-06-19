package uv.tc.happyhealthypet.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import uv.tc.happyhealthypet.LoginActivity
import uv.tc.happyhealthypet.R
import uv.tc.happyhealthypet.databinding.ActivityProfileBinding
import uv.tc.happyhealthypet.modelo.UsuariosDB

class ProfileFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCerrarSesion: Button = view.findViewById(R.id.btn_cerrar_sesion)

        btnCerrarSesion.setOnClickListener{
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

}