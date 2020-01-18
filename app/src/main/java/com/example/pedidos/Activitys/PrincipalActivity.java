package com.example.pedidos.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.pedidos.Fragment.FragmentClientes;
import com.example.pedidos.Fragment.FragmentInventario;
import com.example.pedidos.Fragment.FragmentPedidos;
import com.example.pedidos.R;
import com.google.android.material.navigation.NavigationView;


public class PrincipalActivity extends AppCompatActivity  {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
        Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout) ;
        navigationView = (NavigationView)findViewById(R.id.navigation);
        setToolbar();
        setFragmentbydefault();
        //transaccion fragment
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                boolean fragmenttransaction = false;
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.menupedido:
                        fragment = new FragmentPedidos();
                        fragmenttransaction = true;
                        break;

                    case R.id.menuInventario:
                        fragment = new FragmentInventario();
                        fragmenttransaction = true;
                        break;
                    case R.id.menuClientes:
                        fragment = new FragmentClientes();
                        fragmenttransaction = true;

                        break;
                    case R.id.menucerrar:
                        cerrarSesion();
                        break;
                }

                if(fragmenttransaction){
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
                    //chequea la opcion que esta activa
                    menuItem.setCheckable(true);
                    getSupportActionBar().setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();


                }


                return true;
            }
        });


    }
private void setToolbar(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //habilita icono a lado de titulo
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //abrir el menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    private void setFragmentbydefault(){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,new FragmentInventario()).commit();
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        menuItem.setCheckable(true);
        getSupportActionBar().setTitle(menuItem.getTitle());
    }
    public  void cerrarSesion(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferencias login", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();

    }



}
 /*cerrar sesion
        button= findViewById(R.id.btncerrar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("preferencias login", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

         */