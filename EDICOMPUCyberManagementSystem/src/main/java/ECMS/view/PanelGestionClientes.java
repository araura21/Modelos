package ECMS.view;

import ECMS.controller.GestorClientes;
import ECMS.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

public class PanelGestionClientes extends JPanel {
    private GestorClientes gestorClientes;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTable tablaBusqueda;
    private DefaultTableModel modeloBusqueda;
    private JTextField campoCedula, campoNombre, campoDireccion, campoTelefono, campoCorreo, campoBuscar;
    private JButton botonAgregar, botonActualizar, botonEliminar, botonLimpiar, botonBuscar;

    public PanelGestionClientes() {
        gestorClientes = new GestorClientes();
        setLayout(new BorderLayout());

        // Initialize components
        campoCedula = new JTextField();
        campoNombre = new JTextField();
        campoDireccion = new JTextField();
        campoTelefono = new JTextField();
        campoCorreo = new JTextField();
        campoBuscar = new JTextField(20);

        botonAgregar = new JButton("Agregar");
        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");
        botonLimpiar = new JButton("Limpiar");
        botonBuscar = new JButton("Buscar");

        // Setup main components
        setupFormPanel();
        setupTables();
        setupSearchPanel();
        setupButtonListeners();
        
        // Load initial data
        cargarClientes();
    }

    private void setupFormPanel() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Set uniform size for text fields
        Dimension tamañoCampo = new Dimension(300, 25);
        JTextField[] campos = {campoCedula, campoNombre, campoDireccion, campoTelefono, campoCorreo};
        String[] etiquetas = {"Cédula:", "Nombre:", "Dirección:", "Teléfono:", "Correo:"};

        for (JTextField campo : campos) {
            campo.setPreferredSize(tamañoCampo);
        }

        // Add input restrictions
        setSoloNumeros(campoCedula, 10);
        setSoloNumeros(campoTelefono, 10);

        // Add form components
        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panelFormulario.add(new JLabel(etiquetas[i], SwingConstants.RIGHT), gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            panelFormulario.add(campos[i], gbc);
        }

        // Add buttons panel
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(botonAgregar);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonLimpiar);
        panelBotones.add(botonEliminar);

        gbc.gridx = 0;
        gbc.gridy = etiquetas.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(panelBotones, gbc);

        // Add form panel to main panel
        add(panelFormulario, BorderLayout.NORTH);
    }

   private void setupTables() {
    String[] columnNames = {"N° Cédula", "Nombre", "Dirección", "Teléfono", "Correo"};
    
    // Configuración de la tabla principal
    modeloTabla = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tablaClientes = new JTable(modeloTabla);
    
    // Crear scroll pane para la tabla principal con tamaño preferido
    JScrollPane scrollPane = new JScrollPane(tablaClientes);
    scrollPane.setPreferredSize(new Dimension(600, 300)); // Ancho reducido
    
    // Configurar el panel central para contener la tabla principal
    JPanel panelCentral = new JPanel(new BorderLayout());
    panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    panelCentral.add(scrollPane, BorderLayout.CENTER);
    
    // Configuración de la tabla de búsqueda
    modeloBusqueda = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    tablaBusqueda = new JTable(modeloBusqueda);
    JScrollPane scrollBusqueda = new JScrollPane(tablaBusqueda);
    scrollBusqueda.setPreferredSize(new Dimension(300, 300));
    
    // Agregar las tablas al panel principal
    add(panelCentral, BorderLayout.CENTER);
    
    // Panel de búsqueda se configura en setupSearchPanel()
}
private void PanelSuperior(){
    
      ImageIcon icono = new ImageIcon("ruta/a/tu/imagen.png");
        Image imagenEscalada = icono.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(imagenEscalada));
        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelImagen.add(labelImagen);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelImagen, BorderLayout.WEST);

}
// Ajuste complementario del panel de búsqueda
private void setupSearchPanel() {
    JPanel panelBusquedaCompleto = new JPanel(new BorderLayout());
    panelBusquedaCompleto.setPreferredSize(new Dimension(520, 400)); // Ancho ajustado
    
    // Panel para la barra de búsqueda y botón
    JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    panelBusqueda.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    campoBuscar.setPreferredSize(new Dimension(200, 25));
    panelBusqueda.add(new JLabel("Buscar:"));
    panelBusqueda.add(campoBuscar);
    panelBusqueda.add(botonBuscar);
    
    // Configurar panel de resultados de búsqueda
    JScrollPane scrollBusqueda = new JScrollPane(tablaBusqueda);
    
    // Agregar componentes al panel de búsqueda
    panelBusquedaCompleto.add(panelBusqueda, BorderLayout.NORTH);
    panelBusquedaCompleto.add(scrollBusqueda, BorderLayout.CENTER);
    
    // Agregar el panel de búsqueda a la derecha
    add(panelBusquedaCompleto, BorderLayout.EAST);
}

    private void setupButtonListeners() {
        botonAgregar.addActionListener(e -> agregarCliente());
        botonActualizar.addActionListener(e -> actualizarCliente());
        botonEliminar.addActionListener(e -> eliminarCliente());
        botonLimpiar.addActionListener(e -> limpiarCampos());
        botonBuscar.addActionListener(e -> buscarClientes());

        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarClienteSeleccionado();
            }
        });
    }

    private void cargarClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada != -1) {
            campoCedula.setText((String) modeloTabla.getValueAt(filaSeleccionada, 0));
            campoNombre.setText((String) modeloTabla.getValueAt(filaSeleccionada, 1));
            campoDireccion.setText((String) modeloTabla.getValueAt(filaSeleccionada, 2));
            campoTelefono.setText((String) modeloTabla.getValueAt(filaSeleccionada, 3));
            campoCorreo.setText((String) modeloTabla.getValueAt(filaSeleccionada, 4));
        }
    }

    private void setSoloNumeros(JTextField campoTexto, int longitudMaxima) {
        ((AbstractDocument) campoTexto.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String nuevoTexto = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (regEx.matcher(text).matches() && nuevoTexto.length() <= longitudMaxima) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    private void agregarCliente() {
        if (!validarCampos()) return;

        Cliente cliente = crearClienteDesdeFormulario();
        gestorClientes.agregarCliente(cliente);
        limpiarCampos();
        cargarClientes();
        JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarCliente() {
        if (tablaClientes.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente para actualizar.", 
                "Error de selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarCampos()) return;

        Cliente cliente = crearClienteDesdeFormulario();
        gestorClientes.actualizarCliente(cliente);
        limpiarCampos();
        cargarClientes();
        JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.", "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente para eliminar.", 
                "Error de selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea eliminar este cliente?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            gestorClientes.eliminarCliente(id);
            cargarClientes();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.", "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validarCampos() {
        String id = campoCedula.getText();
        String nombre = campoNombre.getText();
        String telefono = campoTelefono.getText();
        String correo = campoCorreo.getText();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cédula, Nombre y Teléfono son campos obligatorios.", 
                "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!esCedulaValida(id)) {
            JOptionPane.showMessageDialog(this, "La cédula ingresada no es válida.", 
                "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!correo.isEmpty() && !esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una dirección de correo válida.", 
                "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private Cliente crearClienteDesdeFormulario() {
        return new Cliente(
            campoCedula.getText(),
            campoNombre.getText(),
            campoDireccion.getText(),
            campoTelefono.getText(),
            campoCorreo.getText()
        );
    }

    private void limpiarCampos() {
        campoCedula.setText("");
        campoNombre.setText("");
        campoDireccion.setText("");
        campoTelefono.setText("");
        campoCorreo.setText("");
        tablaClientes.clearSelection();
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        for (Cliente cliente : gestorClientes.obtenerClientes()) {
            modeloTabla.addRow(new Object[]{
                cliente.getId(),
                cliente.getNombre(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getCorreo()
            });
        }
    }
    
    private void buscarClientes() {
        String terminoBusqueda = campoBuscar.getText().toLowerCase();
        modeloBusqueda.setRowCount(0);

        for (Cliente cliente : gestorClientes.obtenerClientes()) {
            if (clienteCoincideConBusqueda(cliente, terminoBusqueda)) {
                modeloBusqueda.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getDireccion(),
                    cliente.getTelefono(),
                    cliente.getCorreo()
                });
            }
        }
    }

    private boolean clienteCoincideConBusqueda(Cliente cliente, String terminoBusqueda) {
        return cliente.getNombre().toLowerCase().contains(terminoBusqueda) ||
               cliente.getId().toLowerCase().contains(terminoBusqueda) ||
               cliente.getCorreo().toLowerCase().contains(terminoBusqueda);
    }

    private boolean esCorreoValido(String correo) {
        String regexCorreo = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regexCorreo).matcher(correo).matches();
    }

    private boolean esCedulaValida(String cedula) {
        if (cedula.length() != 10 || !cedula.matches("\\d+")) {
            return false;
        }

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || (provincia > 24 && provincia != 30)) {
            return false;
        }

        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito < 0 || tercerDigito > 6) {
            return false;
        }

        int suma = 0;
        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        for (int i = 0; i < 9; i++) {
            int valor = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
            suma += (valor >= 10) ? valor - 9 : valor;
        }

        int digitoVerificador = Character.getNumericValue(cedula.charAt(9));
        int modulo = suma % 10;
        int calculado = (modulo == 0) ? 0 : 10 - modulo;

        return calculado == digitoVerificador;
    }
}