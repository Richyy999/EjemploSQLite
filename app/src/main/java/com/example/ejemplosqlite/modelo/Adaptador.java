package com.example.ejemplosqlite.modelo;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ejemplosqlite.R;
import com.example.ejemplosqlite.modelo.entidad.Tarea;
import com.example.ejemplosqlite.MainActivity;

import java.util.List;

/**
 * Adaptador del RecyclerView. Se encarga de llenar el RecyclerView.
 * Se debe crear un layout para la vista de las tarjetas
 *
 * @author Ricardo Bordería Pi
 */
public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder> {

    /**
     * Lista de elementos para cargar en el RecyclerView
     *
     * @see Tarea
     */
    private List<Tarea> tareas;

    /**
     * Interfaz personalizada para detectar el click sobre un elemento de la lista.
     * La funcionalidad se implementa en el activity que contiene el RecyclerView.
     *
     * @see Adaptador.OnClickCustom
     */
    private OnClickCustom onClickCustom;

    /**
     * ViewHolder del RecylerView. Se encarga de cargar el layout de cada tarjeta el RecyclerView. Contiene todos los elementos de cada tarjeta.
     * Implementa la interfaz View.OcClickListener para detectar la tarjeta escogida.
     * Tiene un atributo del mismo tipo de la interfaz creada para detectar el click en cada tarjeta.
     *
     * @see Adaptador.OnClickCustom
     * @see Adaptador
     */
    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * TextView de cada tarjeta
         */
        private TextView lblNombre, lblDescripcion, lblHecha;

        /**
         * Interfaz personalizada para detectar el click
         */
        private OnClickCustom onClickCustom;

        public MyHolder(@NonNull View v, OnClickCustom onClickCustom) {
            //Llamo al constructor del padre
            super(v);

            //Incializo los elementos de la ventana
            lblNombre = v.findViewById(R.id.lblNombre);
            lblDescripcion = v.findViewById(R.id.lblDescripcion);
            lblHecha = v.findViewById(R.id.lblHecho);

            //Añado el OnClickListener, NO el listener personalizado
            v.setOnClickListener(this);

            //Inicializo la interfaz personalizada
            this.onClickCustom = onClickCustom;
        }

        @Override
        public void onClick(View v) {
            //Le paso a la interfaz la posición actual de cada tarjeta. No se implementa el método aquí.
            onClickCustom.click(getAdapterPosition());
        }
    }

    /**
     * Constructor del adaptador en él le pasamos los objetos con los que se van a construir las tarjetas
     *
     * @param tareas lista de hospitales
     * @see Tarea
     */
    public Adaptador(List<Tarea> tareas, OnClickCustom onClickCustom) {
        this.tareas = tareas;
        this.onClickCustom = onClickCustom;
    }


    @NonNull
    @Override
    public Adaptador.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creo un View con el layout personalizado para cada tarjeta
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler, parent, false);

        /**
         * Devuelvo una instancia de la clase personalizada MyHolder con la vista y el atributo de
         * la interfaz personalzada
         *
         * @see Adaptador.OnClickCustom
         * @see Adaptador#onClickCustom
         */
        return new MyHolder(v, onClickCustom);
    }

    /**
     * Carga los datos en los elementos de cada tarjeta
     *
     * @param holder   objeto de la clase MyHolder en el que se inicializan los elementos de cada tarjeta
     * @param position posición del List que recorre en ese momento
     * @see Adaptador#tareas
     * @see MyHolder
     */
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //Cojo el objeto de la lista en la posición en la que se encuentre el adaptador
        Tarea t = tareas.get(position);

        //Le doy valores a los elementos de la tarjeta
        holder.lblNombre.setText(t.getNombre());
        holder.lblDescripcion.setText(t.getDescripcion());
        if (t.isHecho())
            holder.lblHecha.setText("Tarea realizada");
        else
            holder.lblHecha.setText("Tarea pendiente");
    }

    /**
     * Cantidad de items que se le pasan al adaptador
     *
     * @return size del List de hospitales
     * @see Adaptador#tareas
     */
    @Override
    public int getItemCount() {
        return tareas.size();
    }

    /**
     * Interfaz personalizada que contiene los métodos a implementar en el
     * activity que contiene el RecyclerView
     *
     * @see MainActivity
     */
    public interface OnClickCustom {

        /**
         * Método que se implementa en el activity que contiene el RecyclerView
         *
         * @param position posición en del elemento seleccionado
         * @see Adaptador#onClickCustom
         */
        void click(int position);
    }
}