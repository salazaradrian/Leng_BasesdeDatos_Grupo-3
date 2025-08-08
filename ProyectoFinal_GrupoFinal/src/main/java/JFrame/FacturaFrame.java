package JFrame;

import modelo.Factura;
import repositorio.FacturaRepositorio;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class FacturaFrame extends JFrame {
    // Componentes de la interfaz de usuario
    private JTextField idVentasField, valorBaseField, subtotalField, impuestoField;
    private JTable tablaFacturas;
    private DefaultTableModel modeloTabla;
    private JSpinner fechaSpinner;
    private JButton agregarBtn, editarBtn, eliminarBtn, listarBtn, limpiarBtn;

    // Repositorio para interactuar con la base de datos
    private FacturaRepositorio repositorio;
    private static final double TASA_IMPUESTO = 0.13; // Tasa del 13%

    
    public FacturaFrame() {
        setTitle("Gestión de Facturas");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Inicializar el repositorio
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
        
        JLabel valorBaseLabel = new JLabel("Valor Base:");
        valorBaseLabel.setBounds(20, 100, 100, 25);
        add(valorBaseLabel);

        valorBaseField = new JTextField();
        valorBaseField.setBounds(120, 100, 150, 25);
        add(valorBaseField);

        JLabel impuestoLabel = new JLabel("Impuesto (13%):");
        impuestoLabel.setBounds(20, 140, 100, 25);
        add(impuestoLabel);

        impuestoField = new JTextField();
        impuestoField.setBounds(120, 140, 150, 25);
        impuestoField.setEditable(false); // Campo de solo lectura
        add(impuestoField);
        
        JLabel subtotalLabel = new JLabel("Subtotal (Total):");
        subtotalLabel.setBounds(20, 180, 100, 25);
        add(subtotalLabel);

        subtotalField = new JTextField();
        subtotalField.setBounds(120, 180, 150, 25);
        subtotalField.setEditable(false); // Campo de solo lectura
        add(subtotalField);

 
        agregarBtn = new JButton("Agregar");
        agregarBtn.setBounds(500, 20, 100, 25);
        add(agregarBtn);

        editarBtn = new JButton("Editar");
        editarBtn.setBounds(500, 60, 100, 25);
        add(editarBtn);

        eliminarBtn = new JButton("Eliminar");
        eliminarBtn.setBounds(500, 100, 100, 25);
        add(eliminarBtn);

        listarBtn = new JButton("Listar");
        listarBtn.setBounds(500, 140, 100, 25);
        add(listarBtn);
        
        limpiarBtn = new JButton("Limpiar");
        limpiarBtn.setBounds(500, 180, 100, 25);
        add(limpiarBtn);

        // Tabla y modelo
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Venta", "Fecha", "Impuesto", "Subtotal"}, 0);
        tablaFacturas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
        scrollPane.setBounds(20, 280, 790, 300);
        add(scrollPane);

        // listeners de los botones
        agregarBtn.addActionListener(e -> agregarFactura());
        editarBtn.addActionListener(e -> editarFactura());
        eliminarBtn.addActionListener(e -> eliminarFactura());
        listarBtn.addActionListener(e -> cargarFacturas());
        limpiarBtn.addActionListener(e -> limpiarCampos());
        
        // Listener para seleccionar una fila de la tabla y cargar los datos en los campos
        tablaFacturas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
           
            public void valueChanged(ListSelectionEvent e) {
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
                    // Calculamos el valor base a partir del subtotal y el impuesto
                    try {
                        double subtotal = Double.parseDouble(subtotalField.getText());
                        double impuesto = Double.parseDouble(impuestoField.getText());
                        double valorBase = subtotal / (1 + TASA_IMPUESTO);
                        valorBaseField.setText(String.format("%.2f", valorBase));
                    } catch (NumberFormatException ex) {
                        valorBaseField.setText("");
                    }
                }
            }
        });
        
        // Listener para el calculo automático de impuesto y subtotal
        valorBaseField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                calcularImpuestoYSubtotal();
            }
        });
        
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
                f.getImpuesto() + f.getSubtotal() 
            });
        }
    }

   
    private void agregarFactura() {
        try {
            int idVentas = Integer.parseInt(idVentasField.getText().trim());
            Date fecha = (Date) fechaSpinner.getValue();
            double subtotalFinal = Double.parseDouble(subtotalField.getText().trim());
            double impuesto = Double.parseDouble(impuestoField.getText().trim());

            //calcular subtotal base
            double subtotalBase = subtotalFinal - impuesto; 
            
            Factura f = new Factura(0, idVentas, fecha, impuesto, subtotalBase);
            if (repositorio.agregarFactura(f)) {
                JOptionPane.showMessageDialog(this, "Factura agregada exitosamente.");
                limpiarCampos();
                cargarFacturas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar la factura.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos. Asegúrate de que los campos numéricos estén correctos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage());
        }
    }

    
    private void editarFactura() {
        int fila = tablaFacturas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una factura para editar.");
            return;
        }

        try {
            int idFactura = (int) modeloTabla.getValueAt(fila, 0);
            int idVentas = Integer.parseInt(idVentasField.getText().trim());
            Date fecha = (Date) fechaSpinner.getValue();
            double subtotalFinal = Double.parseDouble(subtotalField.getText().trim());
            double impuesto = Double.parseDouble(impuestoField.getText().trim());
            
            double subtotalBase = subtotalFinal - impuesto;

            Factura f = new Factura(idFactura, idVentas, fecha, impuesto, subtotalBase);
            if (repositorio.editarFactura(f)) {
                JOptionPane.showMessageDialog(this, "Factura actualizada exitosamente.");
                limpiarCampos();
                cargarFacturas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la factura.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Datos inválidos. Asegúrate de que los campos numéricos estén correctos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage());
        }
    }

    
    private void eliminarFactura() {
        int fila = tablaFacturas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una factura para eliminar.");
            return;
        }

        int idFactura = (int) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta factura?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (repositorio.eliminarFactura(idFactura)) {
                JOptionPane.showMessageDialog(this, "Factura eliminada exitosamente.");
                limpiarCampos();
                cargarFacturas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la factura.");
            }
        }
    }
    
   
    private void calcularImpuestoYSubtotal() {
        try {
            double valorBase = Double.parseDouble(valorBaseField.getText().trim());
            double impuesto = valorBase * TASA_IMPUESTO;
            double subtotal = valorBase + impuesto;
            impuestoField.setText(String.format("%.2f", impuesto));
            subtotalField.setText(String.format("%.2f", subtotal));
        } catch (NumberFormatException ex) {
            impuestoField.setText("");
            subtotalField.setText("");
        }
    }
    
    
    private void limpiarCampos() {
        idVentasField.setText("");
        valorBaseField.setText("");
        subtotalField.setText("");
        impuestoField.setText("");
        fechaSpinner.setValue(new Date());
        tablaFacturas.clearSelection();
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FacturaFrame().setVisible(true));
    }
}


//--------------------------------------------

//package JFrame;
//
//import modelo.Factura;
//import repositorio.FacturaRepositorio;
//
//import javax.swing.*;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * JFrame para la gestión de facturas.
// * Permite agregar, editar, eliminar y listar facturas utilizando la clase FacturaRepositorio.
// */
//public class FacturaFrame extends JFrame {
//    // Componentes de la interfaz de usuario
//    private JTextField idVentasField, impuestoField, subtotalField;
//    private JTable tablaFacturas;
//    private DefaultTableModel modeloTabla;
//    private JSpinner fechaSpinner;
//    private JButton agregarBtn, editarBtn, eliminarBtn, listarBtn, limpiarBtn;
//
//    // Repositorio para interactuar con la base de datos
//    private FacturaRepositorio repositorio;
//
//    /**
//     * Constructor del JFrame.
//     * Inicializa la interfaz de usuario y los listeners.
//     */
//    public FacturaFrame() {
//        setTitle("Gestión de Facturas");
//        setSize(850, 650);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(null);
//        setLocationRelativeTo(null);
//
//        // Inicializamos el repositorio
//        repositorio = new FacturaRepositorio();
//
//        // Creamos y configuramos los componentes del formulario
//        JLabel idVentasLabel = new JLabel("ID Venta:");
//        idVentasLabel.setBounds(20, 20, 100, 25);
//        add(idVentasLabel);
//
//        idVentasField = new JTextField();
//        idVentasField.setBounds(120, 20, 150, 25);
//        add(idVentasField);
//
//        JLabel fechaLabel = new JLabel("Fecha:");
//        fechaLabel.setBounds(20, 60, 100, 25);
//        add(fechaLabel);
//
//        fechaSpinner = new JSpinner(new SpinnerDateModel());
//        fechaSpinner.setBounds(120, 60, 150, 25);
//        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechaSpinner, "yyyy-MM-dd");
//        fechaSpinner.setEditor(dateEditor);
//        add(fechaSpinner);
//
//        JLabel impuestoLabel = new JLabel("Impuesto:");
//        impuestoLabel.setBounds(20, 100, 100, 25);
//        add(impuestoLabel);
//
//        impuestoField = new JTextField();
//        impuestoField.setBounds(120, 100, 150, 25);
//        add(impuestoField);
//
//        JLabel subtotalLabel = new JLabel("Subtotal:");
//        subtotalLabel.setBounds(20, 140, 100, 25);
//        add(subtotalLabel);
//
//        subtotalField = new JTextField();
//        subtotalField.setBounds(120, 140, 150, 25);
//        add(subtotalField);
//
//        // Creamos y configuramos los botones de acción
//        agregarBtn = new JButton("Agregar");
//        agregarBtn.setBounds(500, 20, 100, 25);
//        add(agregarBtn);
//
//        editarBtn = new JButton("Editar");
//        editarBtn.setBounds(500, 60, 100, 25);
//        add(editarBtn);
//
//        eliminarBtn = new JButton("Eliminar");
//        eliminarBtn.setBounds(500, 100, 100, 25);
//        add(eliminarBtn);
//
//        listarBtn = new JButton("Listar");
//        listarBtn.setBounds(500, 140, 100, 25);
//        add(listarBtn);
//        
//        limpiarBtn = new JButton("Limpiar");
//        limpiarBtn.setBounds(500, 180, 100, 25);
//        add(limpiarBtn);
//
//        // Creamos la tabla y su modelo
//        modeloTabla = new DefaultTableModel(new String[]{"ID", "Venta", "Fecha", "Impuesto", "Subtotal"}, 0);
//        tablaFacturas = new JTable(modeloTabla);
//        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
//        scrollPane.setBounds(20, 280, 790, 300);
//        add(scrollPane);
//
//        // Añadimos los listeners a los botones
//        agregarBtn.addActionListener(e -> agregarFactura());
//        editarBtn.addActionListener(e -> editarFactura());
//        eliminarBtn.addActionListener(e -> eliminarFactura());
//        listarBtn.addActionListener(e -> cargarFacturas());
//        limpiarBtn.addActionListener(e -> limpiarCampos());
//        
//        // Listener para seleccionar una fila de la tabla y cargar los datos en los campos
//        tablaFacturas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//                if (!e.getValueIsAdjusting() && tablaFacturas.getSelectedRow() != -1) {
//                    int fila = tablaFacturas.getSelectedRow();
//                    idVentasField.setText(modeloTabla.getValueAt(fila, 1).toString());
//                    try {
//                        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(modeloTabla.getValueAt(fila, 2).toString());
//                        fechaSpinner.setValue(fecha);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                    impuestoField.setText(modeloTabla.getValueAt(fila, 3).toString());
//                    subtotalField.setText(modeloTabla.getValueAt(fila, 4).toString());
//                }
//            }
//        });
//    }
//
//    /**
//     * Carga la lista de facturas de la base de datos y la muestra en la tabla.
//     */
//    private void cargarFacturas() {
//        modeloTabla.setRowCount(0);
//        List<Factura> lista = repositorio.listarFacturas();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        for (Factura f : lista) {
//            modeloTabla.addRow(new Object[]{
//                f.getIdFactura(),
//                f.getIdVentas(),
//                sdf.format(f.getFecha()),
//                f.getImpuesto(),
//                f.getSubtotal()
//            });
//        }
//    }
//
//    /**
//     * Agrega una nueva factura a la base de datos.
//     */
//    private void agregarFactura() {
//        try {
//            int idVentas = Integer.parseInt(idVentasField.getText().trim());
//            Date fecha = (Date) fechaSpinner.getValue();
//            double impuesto = Double.parseDouble(impuestoField.getText().trim());
//            double subtotal = Double.parseDouble(subtotalField.getText().trim());
//
//            Factura f = new Factura(0, idVentas, fecha, impuesto, subtotal);
//            if (repositorio.agregarFactura(f)) {
//                JOptionPane.showMessageDialog(this, "Factura agregada exitosamente.");
//                limpiarCampos();
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al agregar la factura.");
//            }
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(this, "Datos inválidos. Asegúrate de que los campos numéricos estén correctos.");
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage());
//        }
//    }
//
//    /**
//     * Edita una factura existente en la base de datos.
//     */
//    private void editarFactura() {
//        int fila = tablaFacturas.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Por favor, selecciona una factura para editar.");
//            return;
//        }
//
//        try {
//            int idFactura = (int) modeloTabla.getValueAt(fila, 0);
//            int idVentas = Integer.parseInt(idVentasField.getText().trim());
//            Date fecha = (Date) fechaSpinner.getValue();
//            double impuesto = Double.parseDouble(impuestoField.getText().trim());
//            double subtotal = Double.parseDouble(subtotalField.getText().trim());
//
//            Factura f = new Factura(idFactura, idVentas, fecha, impuesto, subtotal);
//            if (repositorio.editarFactura(f)) {
//                JOptionPane.showMessageDialog(this, "Factura actualizada exitosamente.");
//                limpiarCampos();
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al actualizar la factura.");
//            }
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(this, "Datos inválidos. Asegúrate de que los campos numéricos estén correctos.");
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Ocurrió un error: " + ex.getMessage());
//        }
//    }
//
//    /**
//     * Elimina una factura de la base de datos.
//     */
//    private void eliminarFactura() {
//        int fila = tablaFacturas.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Por favor, selecciona una factura para eliminar.");
//            return;
//        }
//
//        int idFactura = (int) modeloTabla.getValueAt(fila, 0);
//        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta factura?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            if (repositorio.eliminarFactura(idFactura)) {
//                JOptionPane.showMessageDialog(this, "Factura eliminada exitosamente.");
//                limpiarCampos();
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al eliminar la factura.");
//            }
//        }
//    }
//    
//    /**
//     * Limpia todos los campos del formulario.
//     */
//    private void limpiarCampos() {
//        idVentasField.setText("");
//        impuestoField.setText("");
//        subtotalField.setText("");
//        fechaSpinner.setValue(new Date());
//        tablaFacturas.clearSelection();
//    }
//
//    /**
//     * Método principal para ejecutar la aplicación.
//     */
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new FacturaFrame().setVisible(true));
//    }
//}



//--------------------------------------------





//package JFrame;
//
//import modelo.Factura;
//import repositorio.FacturaRepositorio;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//public class FacturaFrame extends JFrame {
//    private JTextField idVentasField, impuestoField, subtotalField, totalField;
//    private JTable tablaFacturas;
//    private DefaultTableModel modeloTabla;
//    private JSpinner fechaSpinner;
//    private JRadioButton canceladoBtn, pendienteBtn, creditoBtn;
//    private ButtonGroup estadoGroup;
//    private FacturaRepositorio repositorio;
//
//    public FacturaFrame() {
//        setTitle("Gestión de Facturas");
//        setSize(850, 650);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(null);
//        setLocationRelativeTo(null);
//
//        repositorio = new FacturaRepositorio();
//
//        JLabel idVentasLabel = new JLabel("ID Venta:");
//        idVentasLabel.setBounds(20, 20, 100, 25);
//        add(idVentasLabel);
//
//        idVentasField = new JTextField();
//        idVentasField.setBounds(120, 20, 150, 25);
//        add(idVentasField);
//
//        JLabel fechaLabel = new JLabel("Fecha:");
//        fechaLabel.setBounds(20, 60, 100, 25);
//        add(fechaLabel);
//
//        fechaSpinner = new JSpinner(new SpinnerDateModel());
//        fechaSpinner.setBounds(120, 60, 150, 25);
//        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechaSpinner, "yyyy-MM-dd");
//        fechaSpinner.setEditor(dateEditor);
//        add(fechaSpinner);
//
//        JLabel impuestoLabel = new JLabel("Impuesto:");
//        impuestoLabel.setBounds(20, 100, 100, 25);
//        add(impuestoLabel);
//
//        impuestoField = new JTextField();
//        impuestoField.setBounds(120, 100, 150, 25);
//        add(impuestoField);
//
//        JLabel subtotalLabel = new JLabel("Subtotal:");
//        subtotalLabel.setBounds(20, 140, 100, 25);
//        add(subtotalLabel);
//
//        subtotalField = new JTextField();
//        subtotalField.setBounds(120, 140, 150, 25);
//        add(subtotalField);
//
//        JLabel totalLabel = new JLabel("Total:");
//        totalLabel.setBounds(20, 180, 100, 25);
//        add(totalLabel);
//
//        totalField = new JTextField();
//        totalField.setBounds(120, 180, 150, 25);
//        totalField.setEditable(false);
//        add(totalField);
//
//        JLabel estadoLabel = new JLabel("Estado:");
//        estadoLabel.setBounds(20, 220, 100, 25);
//        add(estadoLabel);
//
//        canceladoBtn = new JRadioButton("Cancelado");
//        pendienteBtn = new JRadioButton("Pendiente");
//        creditoBtn = new JRadioButton("Crédito");
//
//        canceladoBtn.setBounds(120, 220, 100, 25);
//        pendienteBtn.setBounds(220, 220, 100, 25);
//        creditoBtn.setBounds(320, 220, 100, 25);
//
//        estadoGroup = new ButtonGroup();
//        estadoGroup.add(canceladoBtn);
//        estadoGroup.add(pendienteBtn);
//        estadoGroup.add(creditoBtn);
//
//        add(canceladoBtn);
//        add(pendienteBtn);
//        add(creditoBtn);
//
//        JButton agregarBtn = new JButton("Agregar");
//        agregarBtn.setBounds(500, 20, 100, 25);
//        add(agregarBtn);
//
//        JButton editarBtn = new JButton("Editar");
//        editarBtn.setBounds(500, 60, 100, 25);
//        add(editarBtn);
//
//        JButton eliminarBtn = new JButton("Eliminar");
//        eliminarBtn.setBounds(500, 100, 100, 25);
//        add(eliminarBtn);
//
//        JButton listarBtn = new JButton("Listar");
//        listarBtn.setBounds(500, 140, 100, 25);
//        add(listarBtn);
//
//        modeloTabla = new DefaultTableModel(new String[]{"ID", "Venta", "Fecha", "Impuesto", "Subtotal", "Total", "Estado"}, 0);
//        tablaFacturas = new JTable(modeloTabla);
//        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
//        scrollPane.setBounds(20, 280, 790, 300);
//        add(scrollPane);
//        
//        tablaFacturas.getSelectionModel().addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting() && tablaFacturas.getSelectedRow() != -1) {
//                int fila = tablaFacturas.getSelectedRow();
//                idVentasField.setText(modeloTabla.getValueAt(fila, 1).toString());
//                try {
//                    Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(modeloTabla.getValueAt(fila, 2).toString());
//                    fechaSpinner.setValue(fecha);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                impuestoField.setText(modeloTabla.getValueAt(fila, 3).toString());
//                subtotalField.setText(modeloTabla.getValueAt(fila, 4).toString());
//                totalField.setText(modeloTabla.getValueAt(fila, 5).toString());
//
//                String estado = modeloTabla.getValueAt(fila, 6).toString();
//                switch (estado) {
//                    case "Cancelado" ->
//                        canceladoBtn.setSelected(true);
//                    case "Pendiente" ->
//                        pendienteBtn.setSelected(true);
//                    case "Crédito" ->
//                        creditoBtn.setSelected(true);
//                }
//            }
//        });
//
//       
//
//        agregarBtn.addActionListener(e -> agregarFactura());
//        editarBtn.addActionListener(e -> editarFactura());
//        eliminarBtn.addActionListener(e -> eliminarFactura());
//        listarBtn.addActionListener(e -> cargarFacturas());
//
//        // Calcular total automáticamente
//        KeyAdapter calcularTotalListener = new KeyAdapter() {
//            public void keyReleased(KeyEvent e) {
//                try {
//                    double subtotal = Double.parseDouble(subtotalField.getText());
//                    double impuesto = Double.parseDouble(impuestoField.getText());
//                    double total = subtotal + impuesto;
//                    totalField.setText(String.format("%.2f", total));
//                } catch (NumberFormatException ex) {
//                    totalField.setText("");
//                }
//            }
//        };
//
//        subtotalField.addKeyListener(calcularTotalListener);
//        impuestoField.addKeyListener(calcularTotalListener);
//    }
//
//    private void cargarFacturas() {
//        modeloTabla.setRowCount(0);
//        List<Factura> lista = repositorio.listarFacturas();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        for (Factura f : lista) {
//            modeloTabla.addRow(new Object[]{
//                f.getIdFactura(),
//                f.getIdVentas(),
//                sdf.format(f.getFecha()),
//                f.getImpuesto(),
//                f.getSubtotal(),
//                f.getSubtotal() + f.getImpuesto(),
//                estadoTexto(f.getIdEstado())
//            });
//        }
//    }
//
//    private void agregarFactura() {
//        try {
//            int idVentas = Integer.parseInt(idVentasField.getText().trim());
//            Date fecha = (Date) fechaSpinner.getValue();
//            double impuesto = Double.parseDouble(impuestoField.getText().trim());
//            double subtotal = Double.parseDouble(subtotalField.getText().trim());
//            int idEstado = obtenerEstadoSeleccionado();
//
//            Factura f = new Factura(0, idVentas, fecha, impuesto, subtotal, idEstado);
//            if (repositorio.agregarFactura(f)) {
//                JOptionPane.showMessageDialog(this, "Factura agregada.");
//                limpiarCampos();
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al agregar.");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
//        }
//    }
//
//    private void editarFactura() {
//        int fila = tablaFacturas.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Selecciona una factura.");
//            return;
//        }
//
//        try {
//            int idFactura = (int) modeloTabla.getValueAt(fila, 0);
//            int idVentas = Integer.parseInt(idVentasField.getText().trim());
//            Date fecha = (Date) fechaSpinner.getValue();
//            double impuesto = Double.parseDouble(impuestoField.getText().trim());
//            double subtotal = Double.parseDouble(subtotalField.getText().trim());
//            int idEstado = obtenerEstadoSeleccionado();
//
//            Factura f = new Factura(idFactura, idVentas, fecha, impuesto, subtotal, idEstado);
//            if (repositorio.actualizarFactura(f)) {
//                JOptionPane.showMessageDialog(this, "Factura actualizada.");
//                limpiarCampos();
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al actualizar.");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
//        }
//    }
//
//    private void eliminarFactura() {
//        int fila = tablaFacturas.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Selecciona una factura.");
//            return;
//        }
//
//        int idFactura = (int) modeloTabla.getValueAt(fila, 0);
//        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar factura?", "Confirmar", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            if (repositorio.eliminarFactura(idFactura)) {
//                JOptionPane.showMessageDialog(this, "Factura eliminada.");
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al eliminar.");
//            }
//        }
//    }
//
//    private void limpiarCampos() {
//        idVentasField.setText("");
//        impuestoField.setText("");
//        subtotalField.setText("");
//        totalField.setText("");
//        fechaSpinner.setValue(new Date());
//        estadoGroup.clearSelection();
//    }
//
//    private int obtenerEstadoSeleccionado() {
//        if (canceladoBtn.isSelected()) return 1;
//        if (pendienteBtn.isSelected()) return 2;
//        if (creditoBtn.isSelected()) return 3;
//        return 0;
//    }
//
//    private String estadoTexto(int idEstado) {
//        return switch (idEstado) {
//            case 1 -> "Cancelado";
//            case 2 -> "Pendiente";
//            case 3 -> "Crédito";
//            default -> "Desconocido";
//        };
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new FacturaFrame().setVisible(true));
//    }
//}/package JFrame;
//
//import modelo.Factura;
//import repositorio.FacturaRepositorio;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//public class FacturaFrame extends JFrame {
//    private JTextField idVentasField, impuestoField, subtotalField, totalField;
//    private JTable tablaFacturas;
//    private DefaultTableModel modeloTabla;
//    private JSpinner fechaSpinner;
//    private JRadioButton canceladoBtn, pendienteBtn, creditoBtn;
//    private ButtonGroup estadoGroup;
//    private FacturaRepositorio repositorio;
//
//    public FacturaFrame() {
//        setTitle("Gestión de Facturas");
//        setSize(850, 650);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(null);
//        setLocationRelativeTo(null);
//
//        repositorio = new FacturaRepositorio();
//
//        JLabel idVentasLabel = new JLabel("ID Venta:");
//        idVentasLabel.setBounds(20, 20, 100, 25);
//        add(idVentasLabel);
//
//        idVentasField = new JTextField();
//        idVentasField.setBounds(120, 20, 150, 25);
//        add(idVentasField);
//
//        JLabel fechaLabel = new JLabel("Fecha:");
//        fechaLabel.setBounds(20, 60, 100, 25);
//        add(fechaLabel);
//
//        fechaSpinner = new JSpinner(new SpinnerDateModel());
//        fechaSpinner.setBounds(120, 60, 150, 25);
//        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(fechaSpinner, "yyyy-MM-dd");
//        fechaSpinner.setEditor(dateEditor);
//        add(fechaSpinner);
//
//        JLabel impuestoLabel = new JLabel("Impuesto:");
//        impuestoLabel.setBounds(20, 100, 100, 25);
//        add(impuestoLabel);
//
//        impuestoField = new JTextField();
//        impuestoField.setBounds(120, 100, 150, 25);
//        add(impuestoField);
//
//        JLabel subtotalLabel = new JLabel("Subtotal:");
//        subtotalLabel.setBounds(20, 140, 100, 25);
//        add(subtotalLabel);
//
//        subtotalField = new JTextField();
//        subtotalField.setBounds(120, 140, 150, 25);
//        add(subtotalField);
//
//        JLabel totalLabel = new JLabel("Total:");
//        totalLabel.setBounds(20, 180, 100, 25);
//        add(totalLabel);
//
//        totalField = new JTextField();
//        totalField.setBounds(120, 180, 150, 25);
//        totalField.setEditable(false);
//        add(totalField);
//
//        JLabel estadoLabel = new JLabel("Estado:");
//        estadoLabel.setBounds(20, 220, 100, 25);
//        add(estadoLabel);
//
//        canceladoBtn = new JRadioButton("Cancelado");
//        pendienteBtn = new JRadioButton("Pendiente");
//        creditoBtn = new JRadioButton("Crédito");
//
//        canceladoBtn.setBounds(120, 220, 100, 25);
//        pendienteBtn.setBounds(220, 220, 100, 25);
//        creditoBtn.setBounds(320, 220, 100, 25);
//
//        estadoGroup = new ButtonGroup();
//        estadoGroup.add(canceladoBtn);
//        estadoGroup.add(pendienteBtn);
//        estadoGroup.add(creditoBtn);
//
//        add(canceladoBtn);
//        add(pendienteBtn);
//        add(creditoBtn);
//
//        JButton agregarBtn = new JButton("Agregar");
//        agregarBtn.setBounds(500, 20, 100, 25);
//        add(agregarBtn);
//
//        JButton editarBtn = new JButton("Editar");
//        editarBtn.setBounds(500, 60, 100, 25);
//        add(editarBtn);
//
//        JButton eliminarBtn = new JButton("Eliminar");
//        eliminarBtn.setBounds(500, 100, 100, 25);
//        add(eliminarBtn);
//
//        JButton listarBtn = new JButton("Listar");
//        listarBtn.setBounds(500, 140, 100, 25);
//        add(listarBtn);
//
//        modeloTabla = new DefaultTableModel(new String[]{"ID", "Venta", "Fecha", "Impuesto", "Subtotal", "Total", "Estado"}, 0);
//        tablaFacturas = new JTable(modeloTabla);
//        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
//        scrollPane.setBounds(20, 280, 790, 300);
//        add(scrollPane);
//        
//        tablaFacturas.getSelectionModel().addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting() && tablaFacturas.getSelectedRow() != -1) {
//                int fila = tablaFacturas.getSelectedRow();
//                idVentasField.setText(modeloTabla.getValueAt(fila, 1).toString());
//                try {
//                    Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(modeloTabla.getValueAt(fila, 2).toString());
//                    fechaSpinner.setValue(fecha);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                impuestoField.setText(modeloTabla.getValueAt(fila, 3).toString());
//                subtotalField.setText(modeloTabla.getValueAt(fila, 4).toString());
//                totalField.setText(modeloTabla.getValueAt(fila, 5).toString());
//
//                String estado = modeloTabla.getValueAt(fila, 6).toString();
//                switch (estado) {
//                    case "Cancelado" ->
//                        canceladoBtn.setSelected(true);
//                    case "Pendiente" ->
//                        pendienteBtn.setSelected(true);
//                    case "Crédito" ->
//                        creditoBtn.setSelected(true);
//                }
//            }
//        });
//
//       
//
//        agregarBtn.addActionListener(e -> agregarFactura());
//        editarBtn.addActionListener(e -> editarFactura());
//        eliminarBtn.addActionListener(e -> eliminarFactura());
//        listarBtn.addActionListener(e -> cargarFacturas());
//
//        // Calcular total automáticamente
//        KeyAdapter calcularTotalListener = new KeyAdapter() {
//            public void keyReleased(KeyEvent e) {
//                try {
//                    double subtotal = Double.parseDouble(subtotalField.getText());
//                    double impuesto = Double.parseDouble(impuestoField.getText());
//                    double total = subtotal + impuesto;
//                    totalField.setText(String.format("%.2f", total));
//                } catch (NumberFormatException ex) {
//                    totalField.setText("");
//                }
//            }
//        };
//
//        subtotalField.addKeyListener(calcularTotalListener);
//        impuestoField.addKeyListener(calcularTotalListener);
//    }
//
//    private void cargarFacturas() {
//        modeloTabla.setRowCount(0);
//        List<Factura> lista = repositorio.listarFacturas();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        for (Factura f : lista) {
//            modeloTabla.addRow(new Object[]{
//                f.getIdFactura(),
//                f.getIdVentas(),
//                sdf.format(f.getFecha()),
//                f.getImpuesto(),
//                f.getSubtotal(),
//                f.getSubtotal() + f.getImpuesto(),
//                estadoTexto(f.getIdEstado())
//            });
//        }
//    }
//
//    private void agregarFactura() {
//        try {
//            int idVentas = Integer.parseInt(idVentasField.getText().trim());
//            Date fecha = (Date) fechaSpinner.getValue();
//            double impuesto = Double.parseDouble(impuestoField.getText().trim());
//            double subtotal = Double.parseDouble(subtotalField.getText().trim());
//            int idEstado = obtenerEstadoSeleccionado();
//
//            Factura f = new Factura(0, idVentas, fecha, impuesto, subtotal, idEstado);
//            if (repositorio.agregarFactura(f)) {
//                JOptionPane.showMessageDialog(this, "Factura agregada.");
//                limpiarCampos();
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al agregar.");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
//        }
//    }
//
//    private void editarFactura() {
//        int fila = tablaFacturas.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Selecciona una factura.");
//            return;
//        }
//
//        try {
//            int idFactura = (int) modeloTabla.getValueAt(fila, 0);
//            int idVentas = Integer.parseInt(idVentasField.getText().trim());
//            Date fecha = (Date) fechaSpinner.getValue();
//            double impuesto = Double.parseDouble(impuestoField.getText().trim());
//            double subtotal = Double.parseDouble(subtotalField.getText().trim());
//            int idEstado = obtenerEstadoSeleccionado();
//
//            Factura f = new Factura(idFactura, idVentas, fecha, impuesto, subtotal, idEstado);
//            if (repositorio.actualizarFactura(f)) {
//                JOptionPane.showMessageDialog(this, "Factura actualizada.");
//                limpiarCampos();
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al actualizar.");
//            }
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "Datos inválidos: " + ex.getMessage());
//        }
//    }
//
//    private void eliminarFactura() {
//        int fila = tablaFacturas.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Selecciona una factura.");
//            return;
//        }
//
//        int idFactura = (int) modeloTabla.getValueAt(fila, 0);
//        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar factura?", "Confirmar", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            if (repositorio.eliminarFactura(idFactura)) {
//                JOptionPane.showMessageDialog(this, "Factura eliminada.");
//                cargarFacturas();
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al eliminar.");
//            }
//        }
//    }
//
//    private void limpiarCampos() {
//        idVentasField.setText("");
//        impuestoField.setText("");
//        subtotalField.setText("");
//        totalField.setText("");
//        fechaSpinner.setValue(new Date());
//        estadoGroup.clearSelection();
//    }
//
//    private int obtenerEstadoSeleccionado() {
//        if (canceladoBtn.isSelected()) return 1;
//        if (pendienteBtn.isSelected()) return 2;
//        if (creditoBtn.isSelected()) return 3;
//        return 0;
//    }
//
//    private String estadoTexto(int idEstado) {
//        return switch (idEstado) {
//            case 1 -> "Cancelado";
//            case 2 -> "Pendiente";
//            case 3 -> "Crédito";
//            default -> "Desconocido";
//        };
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new FacturaFrame().setVisible(true));
//    }
//}
