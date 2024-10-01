package console;

import java.io.IOException;

import model.ComprimirArchivo;
import model.DirectorioTemp;
import model.ListaArchivos;
import model.SerializarArchivos;

public class Consola {

	public static void main(String[] args) {
		DirectorioTemp dTemp = new DirectorioTemp("temp_dir_"); // Instancia de archivo temporal
		ListaArchivos listaArchivos = new ListaArchivos();// Lista de archivos

		// Añadiendo archivos a la lista
		listaArchivos.anyadirArchivo(1, "Alex", "Contenido de prueba 1");
		listaArchivos.anyadirArchivo(2, "Manu", "Contenido de prueba 2");
		
		// Mostrar el contenido de la lista
		listaArchivos.mostrarArchivos();
		
		SerializarArchivos sa = new SerializarArchivos();

		// Serialización de archivo
		try {
			sa.serializeListToTempDir(dTemp.getTmpdir(), listaArchivos.getListaArchivos());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Deserialización de archivo
		try {
			listaArchivos.setListaArchivos(sa.deserializeListFromTempDir());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Compresión
		ComprimirArchivo ca = new ComprimirArchivo();
		try {
			ca.compressToZipInTempDir(sa.getSerializedList().toFile(), dTemp.getTmpdir());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Descompresión
		try {
			ca.decompressZipFile(dTemp.getTmpdir());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
