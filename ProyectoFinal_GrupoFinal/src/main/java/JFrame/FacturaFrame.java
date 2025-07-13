package JFrame;

import modelo.Factura;
import repositorio.FacturaRepositorio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FacturaFrame extends JFrame {
    private JTextField idVentasField, impuestoField, subtotalField, totalField;
    private JTable tablaFacturas;
    private DefaultTableModel modeloTabla;
    private JSpinner fechaSpinner;
    private JRadioButton canceladoBtn, pendienteBtn, creditoBtn;
    private ButtonGroup estadoGroup;
    private FacturaRepositorio repositorio;

    public FacturaFrame() {
        setTitle("Gestión de Facturas");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        repositorio = new FacturaRepositorio();

        JLabel idVentasLabel = new JLabel("ID Venta:");
        idVentasLabel.setBounds(20, 20, 100, 25);
        add(idVentasLabel);

        idVentasField = new JTextField();
        idVentasField.setBounds(120, 20, 150, 25);
        add(idVentasField);

        JLabel fechaLabel = new JLabel("Fecha:");
        fechaLabel.setBounds(20, 60, 100, 25);
        add(fechaLabel);

        fechaSpinner = new JSpinner(new SpinnerDateModel());
        fechaSpinner.setBounds(120, 60, 150, 25);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechaSpinner, "yyyy-MM-dd");
        fechaSpinner.setEditor(dateEditor);
        add(fechaSpinner);

        JLabel impuestoLabel = new JLabel("Impuesto:");
        impuestoLabel.setBounds(20, 100, 100, 25);
        add(impuestoLabel);

        impuestoField = new JTextField();
        impuestoField.setBounds(120, 100, 150, 25);
        add(impuestoField);

        JLabel subtotalLabel = new JLabel("Subtotal:");
        subtotalLabel.setBounds(20, 140, 100, 25);
        add(subtotalLabel);

        subtotalField = new JTextField();
        subtotalField.setBounds(120, 140, 150, 25);
        add(subtotalField);

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setBounds(20, 180, 100, 25);
        add(totalLabel);

        totalField = new JTextField();
        totalField.setBounds(120, 180, 150, 25);
        totalField.setEditable(false);
        add(totalField);

        JLabel estadoLabel = new JLabel("Estado:");
        estadoLabel.setBounds(20, 220, 100, 25);
        add(estadoLabel);

        canceladoBtn = new JRadioButton("Cancelado");
        pendienteBtn = new JRadioButton("Pendiente");
        creditoBtn = new JRadioButton("Crédito");

        canceladoBtn.setBounds(120, 220, 100, 25);
        pendienteBtn.setBounds(220, 220, 100, 25);
        creditoBtn.setBounds(320, 220, 100, 25);

        estadoGroup = new ButtonGroup();
        estadoGroup.add(canceladoBtn);
        estadoGroup.add(pendienteBtn);
        estadoGroup.add(creditoBtn);

        add(canceladoBtn);
        add(pendienteBtn);
        add(creditoBtn);

        JButton agregarBtn = new JButton("Agregar");
        agregarBtn.setBounds(500, 20, 100, 25);
        add(agregarBtn);

        JButton editarBtn = new JButton("Editar");
        editarBtn.setBounds(500, 60, 100, 25);
        add(editarBtn);

        JButton eliminarBtn = new JButton("Eliminar");
        eliminarBtn.setBounds(500, 100, 100, 25);
        add(eliminarBtn);

        JButton listarBtn = new JButton("Listar");
        listarBtn.setBounds(500, 140, 100, 25);
        add(listarBtn);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Venta", "Fecha", "Impuesto", "Subtotal", "Total", "Estado"}, 0);
        tablaFacturas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
        scrollPane.setBounds(20, 280, 790, 300);
        add(scrollPane);
        
        tablaFacturas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaFacturas.getSelectedRow() != -1) {
                int fila = tablaFacturas.getSelectedRow();
                idVentasField.setText(modeloTabla.getValueAt(fila, 1).toString());
                try {
                    Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(modeloTabla.getValueAt(fila, 2).toString());
                    fechaSpinner.setValue(fecha);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                impuestoField.setText(modeloTabla.getValueAt(fila, 3).toString());
                subtotalField.setText(modeloTabla.getValueAt(fila, 4).toString());
                totalField.setText(modeloTabla.getValueAt(fila, 5).toString());

                String estado = modeloTabla.getValueAt(fila, 6).toString();
                switch (estado) {
                    case "Cancelado" ->
                        canceladoBtn.setSelected(true);
                    case "Pendiente" ->
                        pendienteBtn.setSelected(true);
                    case "Crédito" ->
                        creditoBtn.setSelected(true);
                }
            }
        });

       

        agregarBtn.addActionListener(e -> agregarFactura());
        editarBtn.addActionListener(e -> editarFactura());
        eliminarBtn.addActionListener(e -> eliminarFactura());
        listarBtn.addActionListener(e -> cargarFacturas());

        // Calcular total automáticamente
        KeyAdapter calcularTotalListener = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    double subtotal = Double.parseDouble(subtotalField.getText());
                    double impuesto = Double.parseDouble(impuestoField.getText());
                    double total = subtotal + impuesto;
                    totalField.setText(String.format("%.2f", total));
                } catch (NumberFormatException ex) {
                    totalField.setText("");
                }
            }
        };

        subtotalField.addKeyListener(calcularTotalListener);
        impuestoField.addKeyListener(calcularTotalListener);
    }

    private void cargarFacturas() {
        modeloTabla.setRowCount(0);
        List<Factura> lista = repositorio.listarFacturas();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Factura f : lista) {
            modeloTabla.addRow(new Object[]{
                f.getIdFactura(),
                f.getIdVentas(),
                sdf.format(f.getFecha()),
                f.getImpuesto(),
                f.getSubtotal(),
                f.getSubtotal() + f.getImpuesto(),
                estadoTexto(f.getIdEstado())
            });
        }
    }

    private void agregarFactura() {
        try {
            int idVentas = Integer.parseInt(idVentasField.getText().trim());
            Date fecha = (Date) fechaSpinner.getValue();
            double impuesto = Double.parseDouble(impuestoField.getText().trim());
            double subtotal = Double.parseDouble(subtotalField.getText().trim());
            int idEstado = obtenerEstadoSeleccionado();

            Factura f = new Factura(0, idVentas, fecha, impuesto, subtotal, idEstado);
            if (repositorio.agregarFactura(f)) {
                JOptionPane.showMessageDialog(this, "Factura agregada.");
                limpiarCampos();
                cargarFacturas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }

    private void editarFactura() {
        int fila = tablaFacturas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una factura.");
            return;
        }

        try {
            int idFactura = (int) modeloTabla.getValueAt(fila, 0);
            int idVentas = Integer.parseInt(idVentasField.getText().trim());
            Date fecha = (Date) fechaSpinner.getValue();
            double impuesto = Double.parseDouble(impuestoField.getText().trim());
            double subtotal = Double.parseDouble(subtotalField.getText().trim());
            int idEstado = obtenerEstadoSeleccionado();

            Factura f = new Factura(idFactura, idVentas, fecha, impuesto, subtotal, idEstado);
            if (repositorio.actualizarFactura(f)) {
                JOptionPane.showMessageDialog(this, "Factura actualizada.");
                limpiarCampos();
                cargarFacturas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
        }
    }

    private void eliminarFactura() {
        int fila = tablaFacturas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una factura.");
            return;
        }

        int idFactura = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar factura?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (repositorio.eliminarFactura(idFactura)) {
                JOptionPane.showMessageDialog(this, "Factura eliminada.");
                cargarFacturas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar.");
            }
        }
    }

    private void limpiarCampos() {
        idVentasField.setText("");
        impuestoField.setText("");
        subtotalField.setText("");
        totalField.setText("");
        fechaSpinner.setValue(new Date());
        estadoGroup.clearSelection();
    }

    private int obtenerEstadoSeleccionado() {
        if (canceladoBtn.isSelected()) return 1;
        if (pendienteBtn.isSelected()) return 2;
        if (creditoBtn.isSelected()) return 3;
        return 0;
    }

    private String estadoTexto(int idEstado) {
        return switch (idEstado) {
            case 1 -> "Cancelado";
            case 2 -> "Pendiente";
            case 3 -> "Crédito";
            default -> "Desconocido";
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FacturaFrame().setVisible(true));
    }
}
