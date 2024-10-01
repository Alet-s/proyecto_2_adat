package model;

/**@author Alejandro Serrano*/
import java.util.ArrayList;

/**
 * Representa la lista de archivos sobre los que se van a realizar operaciones
 * de serialización y compresión.
 */
public class ListaArchivos {
	ArrayList<Archivo> listaArchivos; // Lista de clase Archivo.

	/** Constructor sin parámetros: inicializa el ArrayList de archivos */
	public ListaArchivos() {
		listaArchivos = new ArrayList<Archivo>();
	}

	/**
	 * Getter de la lista de archivos
	 * 
	 * @return La lista de Archivos sobre la que realizar operaciones
	 */
	public ArrayList<Archivo> getListaArchivos() {
		return listaArchivos;
	}

	/** Setter de la lista de archivos */
	public void setListaArchivos(ArrayList<Archivo> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	/**
	 * Añade un archivo utilizando el constructor de Archivo.
	 * 
	 * @return true si el objeto ha sido añadido a la lista correctamente
	 */
	public boolean anyadirArchivo(int id, String nombre, String contenido) {
		return listaArchivos.add(new Archivo(id, nombre, contenido));
	}

	/** Muestra el contenido de cada Archivo dentro de la lista de Archivos */
	public void mostrarArchivos() {
		for (Archivo archivo : listaArchivos) {
			System.out.println("ID: " + archivo.getId() + ", Nombre: " + archivo.getNombre() + ", Contenido: "
					+ archivo.getContenido());
		}
	}
}
