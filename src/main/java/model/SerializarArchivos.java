package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class SerializarArchivos {
	Path serializedList; // Archivo serializado

	/**
	 * Serializa un ArrayList en un archivo dentro del directorio especificado.
	 *
	 * @param tempDir       Ruta que apunta al directorio temporal.
	 * @param listaArchivos El ArrayList que se va a serializar.
	 * @throws IOException Si ocurre un error de entrada/salida.
	 */
	public void serializeListToTempDir(Path tempDir, ArrayList<Archivo> listaArchivos) throws IOException {
		// Asegura que el directorio temporal existe
		if (!Files.exists(tempDir)) {
			throw new IOException("El directorio temporal no existe: " + tempDir.toAbsolutePath());
		}

		// Crear un archivo temporal dentro del directorio
		Path serializedList = Files.createTempFile(tempDir, "serializedList_", ".dat");
		setSerializedList(serializedList); // Asignar la ruta del archivo serializado

		// Serializar la lista en el archivo
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serializedList.toFile()))) {
			oos.writeObject(listaArchivos);
			System.out.println("Lista serializada en: " + serializedList.toAbsolutePath());
			listaArchivos.clear(); // Limpiar la lista en la memoria del programa tras la serialización.
		} catch (IOException e) {
			System.err.println("Error al serializar la lista.");
			throw e;
		}
	}

	/**
	 * Deserializa un ArrayList desde un archivo dentro del directorio especificado.
	 *
	 * @return El ArrayList deserializado, o null si la deserialización falla.
	 * @throws IOException            Si ocurre un error de entrada/salida.
	 * @throws ClassNotFoundException Si no se puede encontrar la clase de un objeto
	 *                                serializado.
	 */
	public ArrayList<Archivo> deserializeListFromTempDir() throws IOException, ClassNotFoundException {
		// Asegura que el archivo existe
		if (!Files.exists(serializedList)) {
			throw new IOException("El archivo serializado no existe: " + serializedList.toAbsolutePath());
		}

		// Deserializar la lista desde el archivo
		ArrayList<Archivo> deserializedList = null;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serializedList.toFile()))) {
			deserializedList = (ArrayList<Archivo>) ois.readObject();
			System.out.println("Lista deserializada desde: " + serializedList.toAbsolutePath());
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error al deserializar la lista.");
			throw e;
		}
		return deserializedList;
	}

	/**
	 * @return la ruta del archivo serializado (serializedList)
	 */
	public Path getSerializedList() {
		return serializedList;
	}

	/**
	 * @param serializedList la ruta del archivo serializado a asignar
	 */
	public void setSerializedList(Path serializedList) {
		this.serializedList = serializedList;
	}
}
