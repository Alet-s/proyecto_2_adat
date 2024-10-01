package model;

import java.io.Serializable;

/** @author Alejandro Serrano */

/**
 * Representa un archivo específico sobre el que se van a realizar las
 * operaciones de serialización, compresión y su volcado a un archivo de texto.
 */
public class Archivo implements Serializable {
	private static final long serialVersionUID = 1L;
	int id; // Identificador del archivo
	String nombre; // Nombre del archivo
	String contenido; // Contenido del archivo

	/**
	 * Constructor con parámetros
	 * 
	 * @param id        El id del archivo
	 * @param nombre    Nombre del autor del archivo
	 * @param contenido Contenido en texto del archivo
	 */
	public Archivo(int id, String nombre, String contenido) {
		this.id = id;
		this.nombre = nombre;
		this.contenido = contenido; 
	}

	// Getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
}
