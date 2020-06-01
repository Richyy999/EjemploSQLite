package com.example.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejemplosqlite.modelo.Adaptador;
import com.example.ejemplosqlite.modelo.SQLite;
import com.example.ejemplosqlite.modelo.entidad.Tarea;

import java.util.List;

/**
 * Clase principal de la aplicación. Desde ella se puede crear, editar y ver todas las tareas en la base de datos
 *
 * @author Ricardo Bordería Pi
 * @see Tarea
 */
public class MainActivity extends AppCompatActivity implements Adaptador.OnClickCustom {

    /**
     * Botón para insertar una nueva tarea en la base de datos
     *
     * @see SQLite
     */
    private Button btnInsert;
    /**
     * Botón para actalizar na tarea de la base de datos
     *
     * @see SQLite
     */
    private Button btnUpdate;
    /**
     * Botón para eliminar una tarea de la base de datos
     *
     * @see SQLite
     */
    private Button btnDelete;
    /**
     * Botón para listar todas las tareas de la base de datos
     *
     * @see SQLite
     */
    private Button btnListar;

    /**
     * Campo que contiene el nombre de la tarea
     *
     * @see Tarea
     */
    private EditText txtNombre;
    /**
     * Campo con la descripción de la tarea
     *
     * @see Tarea
     */
    private EditText txtDescripcion;

    /**
     * CheckBox que indica si la tarea está realizada
     *
     * @see Tarea
     */
    private CheckBox chHecho;

    /**
     * RecyclerView que contiene la lista de tareas
     *
     * @see Tarea
     */
    private RecyclerView rv;

    /**
     * Objeto de la clase SQLite para poder acceder a la base de datos
     *
     * @see SQLite
     */
    private SQLite sqlite;

    /**
     * Lista con las tareas de la base de datos
     *
     * @see SQLite
     * @see SQLite#getTareas()
     * @see Tarea
     */
    private List<Tarea> listaTareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlite = new SQLite(this);
        cargarVista();
        cargarListeners();
        listar();
    }

    /**
     * Limpia los campos despues de realizar cualquier operación
     */
    private void clear() {
        txtNombre.setText("");
        txtDescripcion.setText("");


        chHecho.setChecked(false);
    }

    /**
     * Llena los campos con la información de la tarea seleccionada
     *
     * @param tarea tarea seleccionada del RecyclerView
     * @see MainActivity#rv
     * @see MainActivity#click(int)
     * @see Adaptador.OnClickCustom
     * @see Tarea
     */
    private void cargarCampos(Tarea tarea) {
        txtNombre.setText(tarea.getNombre());
        txtDescripcion.setText(tarea.getDescripcion());

        chHecho.setChecked(tarea.isHecho());
    }

    /**
     * Vuelca las tareas de la base de datos en el RecyckerView
     *
     * @see MainActivity#rv
     * @see SQLite
     * @see SQLite#getTareas()
     * @see Tarea
     */
    private void listar() {
        listaTareas = sqlite.getTareas();
        RecyclerView.Adapter adapter = new Adaptador(listaTareas, this);
        rv.setAdapter(adapter);
    }

    /**
     * Elimina una tarea de la base de datos
     *
     * @see SQLite
     * @see SQLite#delete(Tarea)
     * @see Tarea
     */
    private void del() {
        if (sqlite.delete(getTarea()))
            showToast("Tarea eliminada");
        else
            showToast("Error al eliminar la tarea");
    }

    /**
     * Actualiza una tarea existente
     *
     * @see SQLite
     * @see SQLite#update(Tarea)
     * @see Tarea
     */
    private void actualizar() {
        if (sqlite.update(getTarea()))
            showToast("Tarea actualizada");
        else
            showToast("Error al actualizar la tarea");
    }

    /**
     * Crea una tarea nueva
     *
     * @see SQLite
     * @see SQLite#insert(Tarea)
     * @see Tarea
     */
    private void add() {
        if (sqlite.insert(getTarea()))
            showToast("Tarea añadida");
        else
            showToast("Error al añadir la tarea.\nCompruebe que el nombre de la tarea no exista\no actualize la existente");
    }

    /**
     * Crea una tarea con los datos de los campos
     *
     * @return Tarea con los datos de los campos
     */
    private Tarea getTarea() {
        String nombre = txtNombre.getText().toString().trim();
        String descripcion = txtDescripcion.getText().toString().trim();

        boolean hecho = chHecho.isChecked();

        return new Tarea(nombre, descripcion, hecho);
    }

    /**
     * Saca un Toast con un mensaje
     *
     * @param mensaje mensaje a mostrar
     */
    private void showToast(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    /**
     * Carga los listeners de los elementos de la vista
     */
    private void cargarListeners() {
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
                listar();
                clear();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
                listar();
                clear();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del();
                listar();
                clear();
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listar();
                clear();
            }
        });
    }

    /**
     * Carga los elementos de la ventana
     */
    private void cargarVista() {
        rv = findViewById(R.id.recyclerView);
        rv.setHasFixedSize(false);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);

        btnInsert = findViewById(R.id.btnGuardar);
        btnUpdate = findViewById(R.id.btnActualizar);
        btnDelete = findViewById(R.id.btnDelete);
        btnListar = findViewById(R.id.btnListar);

        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);

        chHecho = findViewById(R.id.chHecho);
    }

    /**
     * Carga los datos de la tarea seleccionadas en el RecyclerView en los campos
     *
     * @param position posición en del elemento seleccionado
     * @see MainActivity#rv
     * @see MainActivity#cargarCampos(Tarea)
     * @see Tarea
     */
    @Override
    public void click(int position) {
        cargarCampos(listaTareas.get(position));
    }
}