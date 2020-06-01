package com.example.ejemplosqlite.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.ejemplosqlite.modelo.entidad.Tarea;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que conecta con la Base de Datos. Almacena las tareas y las devuelve al usuario.
 * La PK es el nombre de la Tarea, de este modo no pueden haber más de una tarea con el mismo nombre.
 *
 * @author Ricardo Bordería Pi
 * @see Tarea
 */
public class SQLite {

    /**
     * Nombre de la tabla de objetos Tarea
     *
     * @see Tarea
     */
    private static final String TABLE_NAME = "tarea";

    /**
     * SQL con la creación de la base de datos
     */
    private static final String CREAR_BBDD = "CREATE TABLE " + TABLE_NAME + "(nombre TEXT, descripcion TEXT, hecho TEXT, CONSTRAINT " +
            "tarea_pk PRIMARY KEY (nombre))";

    /**
     * Nombre de la base de datos
     */
    private static final String BBDD_NAME = "lista";

    /**
     * Contexto necesario para la inicialización de la base de datos
     */
    private Context context;

    private SQLiteDatabase bbdd;

    private SQLiteOpenHelper helper;

    /**
     * Inicializa la conexión de la base de datos y la crea si no existe
     *
     * @param context contexto para crear la base de datos
     * @see SQLite#context
     */
    public SQLite(Context context) {
        this.context = context;

        helper = new SQLiteOpenHelper(context, BBDD_NAME, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREAR_BBDD);
                Log.d("CREAR BBDD", "ÉXITO");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        bbdd = helper.getWritableDatabase();
    }

    /**
     * Intenta insertar una tarea en la base de datos. Informará al usuario del resultado de
     * la ejecución. El atributo hecho se almacena como un String
     *
     * @param tarea Tarea para guardar en la base de datos
     * @return true si se ha insertado correctamente, false en caso contrario
     * @see Tarea
     * @see Tarea#hecho
     */
    public boolean insert(Tarea tarea) {
        try (SQLiteStatement stm = bbdd.compileStatement("INSERT INTO " + TABLE_NAME + " VALUES (?, ?, ?)");) {

            stm.bindString(1, tarea.getNombre());
            stm.bindString(2, tarea.getDescripcion());
            if (tarea.isHecho())
                stm.bindString(3, "true");
            else
                stm.bindString(3, "false");
            stm.execute();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta actualizar una tarea de la base de datos. Informa al usuario el resultado de la operación
     *
     * @param tarea tarea a actualizar
     * @return true si se actualiza correctamente, false en caso contrario
     * @see Tarea
     */
    public boolean update(Tarea tarea) {
        try (SQLiteStatement stm = bbdd.compileStatement("UPDATE " + TABLE_NAME + " SET nombre = ?, " +
                "descripcion = ?, hecho = ? WHERE nombre = ?");) {

            stm.bindString(1, tarea.getNombre());
            stm.bindString(2, tarea.getDescripcion());
            if (tarea.isHecho())
                stm.bindString(3, "true");
            else
                stm.bindString(3, "false");
            stm.bindString(4, tarea.getNombre());
            stm.execute();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Intenta eliminar una tarea de la base de datos. Informará al usuario el resultado de la operación
     *
     * @param tarea tarea a eliminar
     * @return true si la tarea se elimina con éxito, false en caso contrario
     * @see Tarea
     */
    public boolean delete(Tarea tarea) {
        try (SQLiteStatement stm = bbdd.compileStatement("DELETE FROM " + TABLE_NAME + " WHERE nombre = ?")) {

            stm.bindString(1, tarea.getNombre());
            stm.execute();
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Devuelve un List con las tareas almacenadas en la base de datos.
     *
     * @return List de Tarea con todas las tareas de la base de datos
     * @see SQLite#insert(Tarea)
     * @see Tarea
     * @see Tarea#hecho
     */
    public List<Tarea> getTareas() {
        List<Tarea> tareas = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cur = bbdd.rawQuery(sql, null);
        while (cur.moveToNext()) {
            tareas.add(new Tarea(cur.getString(0), cur.getString(1),
                    Boolean.parseBoolean(cur.getString(2))));
        }
        return tareas;
    }
}
