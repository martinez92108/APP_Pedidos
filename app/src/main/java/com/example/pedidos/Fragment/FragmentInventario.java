package com.example.pedidos.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pedidos.R;
import com.example.pedidos.adapters.adapter_productos;
import com.example.pedidos.pojo.productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentInventario extends Fragment   {


    private static  final String url_producto = "http://192.168.0.100/puyo/productos.php";
    List<productos> listaProductos;
    private RecyclerView recyclerView;
    private productos adapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // What i have added is this
        setHasOptionsMenu(true);
    }


    public FragmentInventario() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_inventario,container,false);
        recyclerView= view.findViewById(R.id.rcviewproductos);
        listaProductos = new ArrayList<>();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos = new ArrayList<>();
        setHasOptionsMenu(true);
        loadproductos();
        return view;
    }

    private void loadproductos(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_producto, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject producto = array.getJSONObject(i);
                        listaProductos.add(new productos(
                                producto.getInt("idproducto"),
                                producto.getString("nombre"),
                                producto.getString("descripcion"),
                                producto.getString("precio")
                        ));
                    }
                    adapter_productos adapter = new adapter_productos(getContext(), listaProductos);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

                    }
                });
        Volley.newRequestQueue(getContext()).add(stringRequest);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.menu_buscador,menu);
        MenuItem searchItem = menu.findItem(R.id.buscoador);

        SearchView  buscador= (SearchView) searchItem.getActionView();
        buscador.setImeOptions(EditorInfo.IME_ACTION_DONE);

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter_productos adapter = new adapter_productos(getContext(), listaProductos);
                recyclerView.setAdapter(adapter);
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_productos adapter = new adapter_productos(getContext(), listaProductos);
                recyclerView.setAdapter(adapter);
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        buscador.setQueryHint("Buscar");
        super.onCreateOptionsMenu(menu, inflater);



        ///////



    }



}
