# Descripción General del Programa

Este programa está diseñado para gestionar archivos mediante operaciones de serialización, compresión y descompresión. Utiliza un directorio temporal para almacenar archivos de manera segura durante su ejecución y permite realizar varias operaciones en una lista de objetos `Archivo`, que representan archivos con atributos como ID, nombre y contenido.

## Clases y Funciones del Programa

### 1. Consola
- Clase principal que ejecuta el programa.
- Crea instancias de otras clases necesarias para llevar a cabo las operaciones.
- Realiza las siguientes acciones:
  - Crea un directorio temporal.
  - Añade archivos a una lista.
  - Serializa la lista de archivos.
  - Deserializa los archivos de la lista.
  - Comprime los archivos en un archivo ZIP.
  - Descomprime el archivo ZIP.

### 2. Archivo
- Representa un archivo específico.
- Contiene los siguientes atributos:
  - `id`: Identificador único del archivo.
  - `nombre`: Nombre del archivo.
  - `contenido`: Texto que contiene el archivo.
- Proporciona métodos para obtener y establecer estos atributos.

### 3. ComprimirArchivo
- Encargada de realizar la compresión y descompresión de archivos.
- **Métodos**:
  - `compressToZipInTempDir(File fileToZip, Path tempDir)`:
    - Toma un archivo y lo comprime en un archivo ZIP dentro del directorio temporal.
    - Verifica que el directorio temporal existe y crea un nuevo archivo ZIP.
    - Escribe el contenido del archivo original en el archivo ZIP.
  - `decompressZipFile(Path tempDir)`:
    - Descomprime un archivo ZIP existente en el directorio temporal.
    - Crea un nuevo archivo para almacenar el contenido descomprimido.

### 4. DirectorioTemp
- Crea un directorio temporal para almacenar archivos de manera segura durante la ejecución del programa.
- Se asegura de que el directorio se elimine o se gestione adecuadamente al finalizar el programa.
- Proporciona métodos para obtener y establecer la ruta del directorio temporal.

### 5. ListaArchivos
- Maneja una lista de objetos `Archivo`.
- Permite añadir nuevos archivos a la lista y mostrar la información de los archivos existentes.
- **Métodos**:
  - `anyadirArchivo(int id, String nombre, String contenido)`: Añade un nuevo objeto `Archivo` a la lista.
  - `mostrarArchivos()`: Muestra la información de todos los archivos en la lista.

### 6. SerializarArchivos
- Realiza operaciones de serialización y deserialización sobre la lista de archivos.
- **Métodos**:
  - `serializeListToTempDir(Path tempDir, ArrayList<Archivo> listaArchivos)`:
    - Serializa la lista de archivos y la guarda en un archivo dentro del directorio temporal.
    - Utiliza un `ObjectOutputStream` para escribir la lista de archivos.
  - `deserializeListFromTempDir()`:
    - Deserializa la lista de archivos desde el archivo previamente serializado.
    - Utiliza un `ObjectInputStream` para recuperar la lista.

## Flujo de Ejecución del Programa

1. **Instanciación de Clases**:
   - Se crea un objeto `DirectorioTemp`, que representa el directorio temporal.
   - Se crea un objeto `ListaArchivos`, que contendrá la lista de archivos.
   
2. **Añadir Archivos**:
   - Se añaden varios archivos a la lista utilizando el método `anyadirArchivo()`, que crea nuevos objetos `Archivo`.
   
3. **Mostrar Archivos**:
   - Se muestran los archivos de la lista utilizando el método `mostrarArchivos()`.

4. **Serialización**:
   - La lista de archivos se serializa en un archivo dentro del directorio temporal mediante el método `serializeListToTempDir()`.
   - Este método escribe la lista de archivos en un archivo con una extensión `.dat`.

5. **Deserialización**:
   - Se deserializa la lista desde el archivo previamente creado usando `deserializeListFromTempDir()` y se asigna a la lista de archivos.

6. **Compresión**:
   - Se comprime la lista de archivos en un archivo ZIP utilizando el método `compressToZipInTempDir()`.
   - Este método crea un archivo ZIP en el directorio temporal que contiene los archivos serializados.

7. **Descompresión**:
   - Se descomprime el archivo ZIP previamente creado utilizando el método `decompressZipFile()`, que crea un nuevo archivo con el contenido descomprimido en el directorio temporal.

## Manejo de Errores

El programa incluye manejo de excepciones en varias partes críticas:

- **IOException**: Se maneja en caso de errores de entrada/salida al acceder a archivos.
- **ClassNotFoundException**: Se maneja durante la deserialización para asegurar que la clase `Archivo` esté disponible.
