package ECMS.view;

import ECMS.controller.GestorCyber;
import ECMS.model.Historial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PanelPagos extends JPanel {
    private GestorCyber gestorCyber;
    private JTable tablaInforme;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> filtroComputadora;
    private JSpinner spinnerFechaInicio, spinnerFechaFin;
    private JButton botonGenerarInforme, botonExportarCSV;

    public PanelPagos(GestorCyber gestorCyber) {
        this.gestorCyber = gestorCyber;
        setLayout(new BorderLayout());

        JPanel panelControl = new JPanel(new FlowLayout());
        JPanel panelControl2 = new JPanel(new FlowLayout());
        filtroComputadora = new JComboBox<>(obtenerListaComputadoras());

        // Inicializa JSpinner con SpinnerDateModel
        Calendar calendario = Calendar.getInstance();
        Date fechaInicial = calendario.getTime();
        calendario.add(Calendar.YEAR, -100);
        Date fechaMasAntigua = calendario.getTime();
        calendario.add(Calendar.YEAR, 200);
        Date fechaMasReciente = calendario.getTime();

        SpinnerDateModel modeloInicio = new SpinnerDateModel(fechaInicial, fechaMasAntigua, fechaMasReciente, Calendar.YEAR);
        SpinnerDateModel modeloFin = new SpinnerDateModel(fechaInicial, fechaMasAntigua, fechaMasReciente, Calendar.YEAR);

        spinnerFechaInicio = new JSpinner(modeloInicio);
        spinnerFechaFin = new JSpinner(modeloFin);

        JSpinner.DateEditor editorInicio = new JSpinner.DateEditor(spinnerFechaInicio, "yyyy-MM-dd");
        JSpinner.DateEditor editorFin = new JSpinner.DateEditor(spinnerFechaFin, "yyyy-MM-dd");
        spinnerFechaInicio.setEditor(editorInicio);
        spinnerFechaFin.setEditor(editorFin);

        botonGenerarInforme = new JButton("Actualizar");
        botonExportarCSV = new JButton("Exportar a CSV");

        panelControl.add(botonGenerarInforme);
        panelControl.add(new JLabel("Computadora:"));
        panelControl.add(filtroComputadora);
        panelControl.add(new JLabel("Fecha de Inicio:"));
        panelControl.add(spinnerFechaInicio);
        panelControl.add(new JLabel("Fecha de Fin:"));
        panelControl.add(spinnerFechaFin);
        

        add(panelControl, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID Computadora", "Hora de Inicio", "Hora de Fin", "Duración", "Costo", "Cedula cliente"}, 0);
        tablaInforme = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaInforme);
        add(scrollPane, BorderLayout.CENTER);

        botonGenerarInforme.addActionListener(e -> generarInforme());
        

        // Registrar un listener para actualizar el informe en tiempo real
        gestorCyber.agregarEscuchadorActualizacionHistorial(this::generarInforme);
        
        panelControl2.add(botonExportarCSV);
        add(panelControl2, BorderLayout.SOUTH);
        botonExportarCSV.addActionListener(e -> exportarACSV());
    }

    private String[] obtenerListaComputadoras() {
        String[] computadoras = new String[11];
        computadoras[0] = "Todas las Computadoras";
        for (int i = 1; i <= 10; i++) {
            computadoras[i] = "Computadora " + i;
        }
        return computadoras;
    }

    private void generarInforme() {
        modeloTabla.setRowCount(0);
        DecimalFormat df = new DecimalFormat("#.##");
        double ingresosTotales = 0;

        List<Historial> historialFiltrado = gestorCyber.obtenerHistorial().stream()
                .filter(this::coincideConFiltros)
                .collect(Collectors.toList());
        
        // Verifica si el historial filtrado está vacío
        if (historialFiltrado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros para la computadora seleccionada en el rango de fechas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        System.out.println("Tamaño del historial filtrado: " + historialFiltrado.size()); // Línea de depuración

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        for (Historial entrada : historialFiltrado) {
            System.out.println("Entrada: " + entrada); // Línea de depuración
            modeloTabla.addRow(new Object[]{
                    entrada.getIdComputadora(),
                    formatter.format(entrada.getHoraInicio()),
                    formatter.format(entrada.getHoraFin()),
                    formatearDuracion(entrada.getHoraInicio(), entrada.getHoraFin()),
                    "$" + df.format(entrada.getCosto()),
                    entrada.getIdCliente()
            });
            ingresosTotales += entrada.getCosto();
        }

        modeloTabla.addRow(new Object[]{"Total", "", "", "", "$" + df.format(ingresosTotales),""});
    }

    private boolean coincideConFiltros(Historial entrada) {
        int computadoraSeleccionada = filtroComputadora.getSelectedIndex();
        Date fechaInicio = (Date) spinnerFechaInicio.getValue();
        Date fechaFin = (Date) spinnerFechaFin.getValue();

        Instant inicioInstant = fechaInicio.toInstant();
        Instant finInstant = fechaFin.toInstant().plusSeconds(86399); // Incluir todo el día

        boolean coincideConComputadora = computadoraSeleccionada == 0 || entrada.getIdComputadora() == computadoraSeleccionada;
        boolean coincideConFechaInicio = fechaInicio == null || !entrada.getHoraInicio().isBefore(inicioInstant);
        boolean coincideConFechaFin = fechaFin == null || !entrada.getHoraFin().isAfter(finInstant);

        System.out.println("coincideConComputadora: " + coincideConComputadora); // Línea de depuración
        System.out.println("coincideConFechaInicio: " + coincideConFechaInicio); // Línea de depuración
        System.out.println("coincideConFechaFin: " + coincideConFechaFin); // Línea de depuración

        return coincideConComputadora && coincideConFechaInicio && coincideConFechaFin;
    }

    private String formatearDuracion(Instant inicio, Instant fin) {
        Duration duracion = Duration.between(inicio, fin);
        long horas = duracion.toHours();
        long minutos = duracion.toMinutesPart();
        long segundos = duracion.toSecondsPart();
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    private void exportarACSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Especifica un archivo para guardar");
        int seleccionUsuario = fileChooser.showSaveDialog(this);

        if (seleccionUsuario == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
                    writer.append(modeloTabla.getColumnName(i));
                    if (i < modeloTabla.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");

                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    for (int j = 0; j < modeloTabla.getColumnCount(); j++) {
                        writer.append(modeloTabla.getValueAt(i, j).toString());
                        if (j < modeloTabla.getColumnCount() - 1) {
                            writer.append(",");
                        }
                    }
                    writer.append("\n");
                }

                writer.flush();
                JOptionPane.showMessageDialog(this, "Informe exportado exitosamente a " + filePath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exportando el informe: " + e.getMessage(), "Error de Exportación", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
