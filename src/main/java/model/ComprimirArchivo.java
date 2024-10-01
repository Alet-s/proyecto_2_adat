package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ComprimirArchivo {
	File zipFile;

	/**
	 * @return el archivo zip (zipFile)
	 */
	public File getZipFile() {
		return zipFile;
	}

	/**
	 * @param zipFile el archivo zip a asignar
	 */
	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}

	/**
	 * Comprime un archivo dado en un archivo zip dentro de un directorio temporal.
	 *
	 * @param fileToZip El archivo que se va a comprimir.
	 * @param tempDir   La ruta del directorio temporal.
	 * @return El archivo zip comprimido almacenado en el directorio temporal.
	 * @throws IOException Si ocurre un error de entrada/salida.
	 */

	public File compressToZipInTempDir(File fileToZip, Path tempDir) throws IOException {
		// Asegura que el directorio temporal existe
		if (!Files.exists(tempDir)) {
			throw new FileNotFoundException("El directorio temporal no existe: " + tempDir.toAbsolutePath());
		}

		// Crear un archivo zip en el directorio temporal
		File zipFile = Files.createTempFile(tempDir, fileToZip.getName(), ".zip").toFile();

		// Comprime el archivo en el archivo zip
		try (FileOutputStream fos = new FileOutputStream(zipFile);
				ZipOutputStream zos = new ZipOutputStream(fos);
				FileInputStream fis = new FileInputStream(fileToZip)) {

			// Crear una entrada Zip para el archivo que se va a comprimir
			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zos.putNextEntry(zipEntry);

			// Buffer para leer el archivo
			byte[] buffer = new byte[1024];
			int length;

			// Escribir el archivo en el ZipOutputStream
			while ((length = fis.read(buffer)) >= 0) {
				zos.write(buffer, 0, length);
			}

			// Cerrar la entrada Zip
			zos.closeEntry();

			System.out.println("Archivo comprimido en: " + zipFile.getAbsolutePath());
			setZipFile(zipFile);
			return zipFile;
		} catch (IOException e) {
			System.err.println("Ocurrió un error al comprimir el archivo.");
			throw e;
		}

	}

	/**
	 * Descomprime el archivo zip previamente comprimido y lo almacena en el mismo
	 * directorio.
	 *
	 * @param tempDir La ruta del directorio temporal donde se descomprimirá el
	 *                archivo.
	 * @return El archivo descomprimido.
	 * @throws IOException Si ocurre un error de entrada/salida.
	 */
	public File decompressZipFile(Path tempDir) throws IOException {
		if (zipFile == null || !zipFile.exists()) {
			throw new FileNotFoundException(
					"El archivo zip no existe: " + (zipFile != null ? zipFile.getAbsolutePath() : "null"));
		}

		// Asegura que el directorio temporal existe
		if (!Files.exists(tempDir)) {
			throw new FileNotFoundException("El directorio temporal no existe: " + tempDir.toAbsolutePath());
		}

		// Crear un nuevo archivo para almacenar el contenido descomprimido en el mismo
		// directorio
		File decompressedFile = new File(tempDir.toFile(), "decompressed_" + zipFile.getName().replace(".zip", ""));

		try (FileInputStream fis = new FileInputStream(zipFile);
				ZipInputStream zis = new ZipInputStream(fis);
				FileOutputStream fos = new FileOutputStream(decompressedFile)) {

			ZipEntry zipEntry = zis.getNextEntry();
			if (zipEntry == null) {
				throw new IOException("No se encontraron entradas en el archivo zip.");
			}

			// Buffer para escribir el archivo descomprimido
			byte[] buffer = new byte[1024];
			int length;

			// Escribir los datos descomprimidos en el archivo
			while ((length = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}

			// Cerrar la entrada zip actual
			zis.closeEntry();
			System.out.println("Archivo descomprimido en: " + decompressedFile.getAbsolutePath());

			return decompressedFile;

		} catch (IOException e) {
			System.err.println("Ocurrió un error al descomprimir el archivo.");
			throw e;
		}
	}
}
