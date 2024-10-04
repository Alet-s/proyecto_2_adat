package console;

import model.Archivo;
import model.ComprimirArchivo;
import model.DirectorioTemp;
import model.ListaArchivos;
import model.SerializarArchivos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GestorArchivosSwing extends JFrame {
    private ListaArchivos listaArchivos;
    private SerializarArchivos serializarArchivos;
    private ComprimirArchivo comprimirArchivo;
    private DirectorioTemp directorioTemp;

    private JTable tablaArchivos;
    private DefaultTableModel modeloTabla;
    private JTextField idField, nombreField, contenidoField;

    public GestorArchivosSwing() {
        listaArchivos = new ListaArchivos();
        serializarArchivos = new SerializarArchivos();
        comprimirArchivo = new ComprimirArchivo();
        directorioTemp = new DirectorioTemp("temp_dir_");

        // Configuración de la ventana
        setTitle("Gestor de Archivos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout()); // Usamos GridBagLayout para mejor control de la disposición
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Margen entre los componentes

        // Panel superior para agregar archivos
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Agregar Archivo"));

        // Campo ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(new JLabel("ID:"), gbc);
        
        idField = new JTextField(10);
        gbc.gridx = 1;
        panelSuperior.add(idField, gbc);

        // Campo Nombre
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperior.add(new JLabel("Nombre:"), gbc);
        
        nombreField = new JTextField(10);
        gbc.gridx = 1;
        panelSuperior.add(nombreField, gbc);

        // Campo Contenido
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelSuperior.add(new JLabel("Contenido:"), gbc);
        
        contenidoField = new JTextField(10);
        gbc.gridx = 1;
        panelSuperior.add(contenidoField, gbc);

        // Botón Agregar
        JButton btnAgregar = new JButton("Agregar Archivo");
        btnAgregar.addActionListener(e -> agregarArchivo());
        gbc.gridx = 1;
        gbc.gridy = 3;
        panelSuperior.add(btnAgregar, gbc);

        // Añadimos el panel superior a la ventana principal.
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(panelSuperior, gbc);

        // Panel central con tabla para mostrar archivos
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Contenido"}, 0);
        tablaArchivos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaArchivos);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Panel inferior para botones de operaciones
        JPanel panelInferior = new JPanel(new GridBagLayout());
        gbc.weighty = 0.0;
        gbc.gridwidth = 1;

        JButton btnSerializar = new JButton("Serializar");
        btnSerializar.addActionListener(e -> serializarArchivos());
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInferior.add(btnSerializar, gbc);

        JButton btnDeserializar = new JButton("Deserializar");
        btnDeserializar.addActionListener(e -> deserializarArchivos());
        gbc.gridx = 1;
        panelInferior.add(btnDeserializar, gbc);

        JButton btnComprimir = new JButton("Comprimir");
        btnComprimir.addActionListener(e -> comprimirArchivo());
        gbc.gridx = 2;
        panelInferior.add(btnComprimir, gbc);

        JButton btnDescomprimir = new JButton("Descomprimir");
        btnDescomprimir.addActionListener(e -> descomprimirArchivo());
        gbc.gridx = 3;
        panelInferior.add(btnDescomprimir, gbc);

        // Añadimos el panel inferior a la ventana principal
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(panelInferior, gbc);

        setVisible(true);
    }

    private void agregarArchivo() {
        try {
            int id = Integer.parseInt(idField.getText());
            String nombre = nombreField.getText();
            String contenido = contenidoField.getText();

            Archivo archivo = new Archivo(id, nombre, contenido);
            listaArchivos.anyadirArchivo(id, nombre, contenido);
            modeloTabla.addRow(new Object[]{id, nombre, contenido});

            idField.setText("");
            nombreField.setText("");
            contenidoField.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID debe ser un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void serializarArchivos() {
        try {
            serializarArchivos.serializeListToTempDir(directorioTemp.getTmpdir(), listaArchivos.getListaArchivos());
            JOptionPane.showMessageDialog(this, "Archivos serializados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al serializar archivos", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void deserializarArchivos() {
        try {
            ArrayList<Archivo> archivosDeserializados = serializarArchivos.deserializeListFromTempDir();
            listaArchivos.setListaArchivos(archivosDeserializados);
            modeloTabla.setRowCount(0); // Limpiar tabla
            for (Archivo archivo : archivosDeserializados) {
                modeloTabla.addRow(new Object[]{archivo.getId(), archivo.getNombre(), archivo.getContenido()});
            }
            JOptionPane.showMessageDialog(this, "Archivos deserializados correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al deserializar archivos", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void comprimirArchivo() {
        try {
            if (serializarArchivos.getSerializedList() == null) {
                JOptionPane.showMessageDialog(this, "Primero debes serializar los archivos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            comprimirArchivo.compressToZipInTempDir(serializarArchivos.getSerializedList().toFile(), directorioTemp.getTmpdir());
            JOptionPane.showMessageDialog(this, "Archivos comprimidos correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al comprimir archivos", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void descomprimirArchivo() {
        try {
            comprimirArchivo.decompressZipFile(directorioTemp.getTmpdir());
            JOptionPane.showMessageDialog(this, "Archivos descomprimidos correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al descomprimir archivos", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GestorArchivosSwing::new);
    }
}
