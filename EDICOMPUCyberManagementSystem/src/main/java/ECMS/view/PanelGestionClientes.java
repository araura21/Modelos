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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

public class PanelGestionClientes extends JPanel {
    private GestorClientes gestorClientes;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JTextField campoId, campoNombre, campoDireccion, campoTelefono, campoCorreo, campoBuscar;
    private JButton botonAgregar, botonActualizar, botonEliminar, botonLimpiar, botonBuscar;

    public PanelGestionClientes() {
        gestorClientes = new GestorClientes();
        setLayout(new BorderLayout());

        // Crear panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelFormulario.add(new JLabel("ID:"));
        campoId = new JTextField(10);
        setSoloNumeros(campoId, 10);
        panelFormulario.add(campoId);

        panelFormulario.add(new JLabel("Nombre:"));
        campoNombre = new JTextField(20);
        panelFormulario.add(campoNombre);

        panelFormulario.add(new JLabel("Dirección:"));
        campoDireccion = new JTextField(30);
        panelFormulario.add(campoDireccion);

        panelFormulario.add(new JLabel("Teléfono:"));
        campoTelefono = new JTextField(10);
        setSoloNumeros(campoTelefono, 10);
        panelFormulario.add(campoTelefono);

        panelFormulario.add(new JLabel("Correo:"));
        campoCorreo = new JTextField(20);
        panelFormulario.add(campoCorreo);

        // Crear panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botonAgregar = new JButton("Agregar");
        botonActualizar = new JButton("Actualizar");
        botonEliminar = new JButton("Eliminar");
        botonLimpiar = new JButton("Limpiar");

        panelBotones.add(botonAgregar);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonLimpiar);

        // Crear panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        campoBuscar = new JTextField(20);
        botonBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(campoBuscar);
        panelBusqueda.add(botonBuscar);

        // Crear tabla
        String[] nombresColumnas = {"ID", "Nombre", "Dirección", "Teléfono", "Correo"};
        modeloTabla = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaClientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaClientes);

        // Agregar componentes al panel
        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelBusqueda, BorderLayout.WEST);

        // Agregar listeners a los botones
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCliente();
            }
        });

        botonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCliente();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCliente();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });

        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaClientes.getSelectedRow();
                if (filaSeleccionada != -1) {
                    campoId.setText((String) modeloTabla.getValueAt(filaSeleccionada, 0));
                    campoNombre.setText((String) modeloTabla.getValueAt(filaSeleccionada, 1));
                    campoDireccion.setText((String) modeloTabla.getValueAt(filaSeleccionada, 2));
                    campoTelefono.setText((String) modeloTabla.getValueAt(filaSeleccionada, 3));
                    campoCorreo.setText((String) modeloTabla.getValueAt(filaSeleccionada, 4));
                }
            }
        });

        // Cargar los datos iniciales
        cargarClientes();
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
        String id = campoId.getText();
        String nombre = campoNombre.getText();
        String direccion = campoDireccion.getText();
        String telefono = campoTelefono.getText();
        String correo = campoCorreo.getText();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID, Nombre y Teléfono son campos obligatorios.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una dirección de correo válida.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(id, nombre, direccion, telefono, correo);
        gestorClientes.agregarCliente(cliente);
        limpiarCampos();
        cargarClientes();
        JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente para actualizar.", "Error de selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = campoId.getText();
        String nombre = campoNombre.getText();
        String direccion = campoDireccion.getText();
        String telefono = campoTelefono.getText();
        String correo = campoCorreo.getText();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID, Nombre y Teléfono son campos obligatorios.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una dirección de correo válida.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(id, nombre, direccion, telefono, correo);
        gestorClientes.actualizarCliente(cliente);
        limpiarCampos();
        cargarClientes();
        JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente para eliminar.", "Error de selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            gestorClientes.eliminarCliente(id);
            cargarClientes();
            JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarCampos() {
        campoId.setText("");
        campoNombre.setText("");
        campoDireccion.setText("");
        campoTelefono.setText("");
        campoCorreo.setText("");
    }

    private void cargarClientes() {
        modeloTabla.setRowCount(0);
        for (Cliente cliente : gestorClientes.obtenerClientes()) {
            Object[] fila = {cliente.getId(), cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono(), cliente.getCorreo()};
            modeloTabla.addRow(fila);
        }
    }

    private void buscarClientes() {
        String terminoBusqueda = campoBuscar.getText().toLowerCase();
        modeloTabla.setRowCount(0);
        for (Cliente cliente : gestorClientes.obtenerClientes()) {
            if (cliente.getNombre().toLowerCase().contains(terminoBusqueda) ||
                cliente.getId().toLowerCase().contains(terminoBusqueda) ||
                cliente.getCorreo().toLowerCase().contains(terminoBusqueda)) {
                Object[] fila = {cliente.getId(), cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono(), cliente.getCorreo()};
                modeloTabla.addRow(fila);
            }
        }
    }

    private boolean esCorreoValido(String correo) {
        String regexCorreo = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regexCorreo);
        return pattern.matcher(correo).matches();
    }
}
