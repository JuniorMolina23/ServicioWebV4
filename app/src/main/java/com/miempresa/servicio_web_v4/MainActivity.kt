package com.miempresa.servicio_web_v4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val policy =
             StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        btnLogear.setOnClickListener(){
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            var url = "http://192.168.23.226:3000/usuarios?"
            url = url + "usuario=" + usuario + "&clave=" + clave

            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        val valor = response.getJSONObject(0)
                        Toast.makeText(
                            applicationContext,
                            "validacion de usuario: " + valor.getString("usuario")+
                                    " con clave: " + valor.getString("clave")+" correcta",
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: JSONException){
                        Toast.makeText(
                            applicationContext,
                            "Error en las credenciales proporcionadas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Compruebe que tiene acceso a internet: ",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            queue.add(stringRequest)

        }
        btnRegistrar.setOnClickListener(){
            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.23.226:3000/usuarios"
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()

            val stringReq : StringRequest =
                object : StringRequest(
                    Method.POST, url,
                    Response.Listener { response ->
                        var strResp = response.toString()
                        Log.d("API",strResp)
                        Toast.makeText(
                            applicationContext,
                            "Usuario registrado de forma exitosa ",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    Response.ErrorListener{
                        Toast.makeText(
                            applicationContext,
                            "Compruebe que tiene acceso a internet: ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }){
                    override fun getParams(): MutableMap<kotlin.String, kotlin.String>? {
                        val params: HashMap<kotlin.String, kotlin.String> = hashMapOf()
                        params.put("usuario",usuario)
                        params.put("clave",clave)
                        params.put("estado","A")
                        return params
                    }
                }
            queue.add(stringReq)

        }
        btnSalir.setOnClickListener(){
            finish()
        }
    }
}