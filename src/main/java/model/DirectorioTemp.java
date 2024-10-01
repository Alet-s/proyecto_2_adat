package model;

/**@author Alejandro Serrano*/
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase para crear el directorio temporal y borrarlo cuando acabe la ejecución
 * del programa.
 */
public class DirectorioTemp {
	Path tmpdir; // Directorio temporal

	/**
	 * Constructor con parámetros.
	 * 
	 * @param prefix Prefijo utilizado para crear el directorio temporal.
	 */
	public DirectorioTemp(String prefix) {
		try {
			// Target: crear un directorio temporal dentro de la carpeta "target" del
			// proyecto Maven.
			Path tmpDir = Files.createTempDirectory(Paths.get("target"), prefix);
			setTmpdir(tmpDir);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Establece la ruta del directorio temporal.
	 *
	 * @param tmpdir la ruta del directorio temporal a asignar.
	 */
	public void setTmpdir(Path tmpdir) {
		this.tmpdir = tmpdir;
	}

	/**
	 * Devuelve la ruta del directorio temporal.
	 *
	 * @return la ruta del directorio temporal.
	 */
	public Path getTmpdir() {
		return tmpdir;
	}

}
