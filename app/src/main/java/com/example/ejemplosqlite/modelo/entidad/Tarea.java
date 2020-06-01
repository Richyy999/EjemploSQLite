package com.example.ejemplosqlite.modelo.entidad;

/**
 * Almacena la información de las tares
 *
 * @author Ricardo Bordería Pi
 */
public class Tarea {

    /**
     * Nombre de la tarea
     */
    private String nombre;
    /**
     * Descripción de la tarea
     */
    private String descripcion;

    /**
     * Indica si la tarea está realizada
     */
    public boolean hecho;

    public Tarea(String nombre, String descripcion, boolean hecho) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hecho = hecho;
    }

    /**
     * Devuelve el nombre de la tarea
     *
     * @return nombre de la tarea
     * @see Tarea#nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Almacena el nombre de la tarea
     *
     * @param nombre nombre de la tarea
     * @see Tarea#nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la descripción de la tarea
     *
     * @return descripción de la tarea
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Almacena la descripción de la tarea
     *
     * @param descripcion descripción de la tarea
     * @see Tarea#descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve si la tarea está hecha
     *
     * @return true si la tares está hecha, false en caso contrario
     * @see Tarea#hecho
     */
    public boolean isHecho() {
        return hecho;
    }

    /**
     * Almacena si la tarea está hecha o no
     *
     * @param hecho true si la tarea está hecha, false en caso contrario
     * @see Tarea#hecho
     */
    public void setHecho(boolean hecho) {
        this.hecho = hecho;
    }
}
