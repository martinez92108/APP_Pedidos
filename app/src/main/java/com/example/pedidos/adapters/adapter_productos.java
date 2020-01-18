package com.example.pedidos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidos.R;
import com.example.pedidos.pojo.productos;

import java.util.ArrayList;
import java.util.List;

public class adapter_productos  extends RecyclerView.Adapter<adapter_productos.ViewHolder> implements Filterable {

    private Context context;
    private List<productos>listProducto;
    private List<productos>listaPro;


    private int layout;

  //  private OnItemClickListener itemClickListener;


    public adapter_productos(Context context, List<productos> listProducto) {

        this.context = context;
        this.listProducto = listProducto;
        listaPro = new ArrayList<>(listProducto);
    }

    @NonNull
    @Override
    //PATRON DE DISEÑO ViewHolder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista_productos, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.bind(productos.get(position),itemClickListener);
        productos producto = listProducto.get(position);
        holder.codigo.setText(String.valueOf(producto.getIdproducto()));
        holder.nombre.setText(producto.getNombre());
        holder.descripcion.setText(producto.getDescripcion());
        holder.valor.setText(producto.getPrecio());


    }

    @Override
    public int getItemCount() {
        return listProducto.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView codigo;
        public TextView nombre;
        public TextView descripcion;
        public TextView valor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.codigo = itemView.findViewById(R.id.cod_producto);
            this.nombre = itemView.findViewById(R.id.nombre);
            this.descripcion = itemView.findViewById(R.id.descripcion);
            this.valor = itemView.findViewById(R.id.valor);
        }
    }



    /*
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listaPro = listProducto;
                } else {
                    List<productos> filteredList = new ArrayList<>();
                    for (productos row : listProducto) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNombre().toLowerCase().contains(charString.toLowerCase()) || row.getDescripcion().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    listaPro = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listaPro;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listaPro = (ArrayList<productos>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

     */


    @Override
    public Filter getFilter() {
        return filtardo;
    }


    private Filter filtardo = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<productos>producto_filtrado = new ArrayList<>();
           if(constraint == null || constraint.length()==0){
               producto_filtrado.addAll(listaPro);
               Toast.makeText(context,"inici",Toast.LENGTH_LONG).show();
           }else {
               String patronfiltro = constraint.toString().toLowerCase().trim();
               for (productos item :listaPro){
                   if(item.getNombre().toLowerCase().contains(patronfiltro)){
                       producto_filtrado.add(item);
                   }
               }
           }
            FilterResults resultado = new FilterResults();
           resultado.values = producto_filtrado;
           return resultado;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listProducto.clear();
            listProducto.addAll((List)results.values);
          //  Toast.makeText(context,"buscando",Toast.LENGTH_LONG).show();
            notifyDataSetChanged();
        }
    };


}
        /*
        public void bind(final String productos,final OnItemClickListener lisener){
            this.codigo.setText(productos);
            this.descripcion.setText(productos);
            this.valor.setText(productos);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lisener.onItemClick(productos,getAdapterPosition());
                }
            });

        }
    }
    public interface OnItemClickListener{
        void onItemClick(String nombre, int position);

    }

*/

