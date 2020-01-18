package com.example.pedidos.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pedidos.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edtusuario,edtpassword;
    Button btnlogin;
    String usuario,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtusuario = findViewById(R.id.edtUsuario);
        edtpassword = findViewById(R.id.edtPassword);
        btnlogin = findViewById(R.id.btnLogin);


        btnlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                usuario= edtusuario.getText().toString();
                password = edtpassword.getText().toString();
                if(!usuario.isEmpty() && !password.isEmpty()){
                    validarUsuario("http://192.168.0.100/puyo/validar_usuario.php");
                }
                else{
                    Toast.makeText(MainActivity.this,"no se aceptan campos vacios",Toast.LENGTH_LONG).show();
                }

            }
        });
        recuperarDatos();

    }
    private void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            //se ejecuta cuando la peticion es exitosa
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    guardar_preferencias();
                    Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this,"Usuario o contraseña incorrecta",Toast.LENGTH_LONG).show();
                }

            }
            //reacciona si la peticion falla
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();


            }
            //para metros para el servicio
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros =  new HashMap<String,String>();
                parametros.put("usuario", usuario);
                parametros.put("password",password);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    //metodo guardar los datos de las sesion
    private  void guardar_preferencias(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("password",password);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    //metodo recuperar los datos almacenados en el sharen preferens
    private  void recuperarDatos(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias login", Context.MODE_PRIVATE);
        //esteblecer datos a los edit text
        edtusuario.setText(sharedPreferences.getString("usuario",""));
        edtpassword.setText(sharedPreferences.getString("password",""));

    }
}
