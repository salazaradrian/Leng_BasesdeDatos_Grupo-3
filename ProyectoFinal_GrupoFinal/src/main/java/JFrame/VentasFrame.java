package JFrame;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import conexion.ConexionOracle;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class VentasFrame extends JFrame {

    private JComboBox<String> comboClientes, comboProductos, comboEmpleados;
    private JTextField cantidadField, montoTotalField;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;

    private List<Venta> listaVentas = new ArrayList<>();
    private int idVentaAutoIncremental = 1;

    public VentasFrame() {
        try {
            System.out.println("Abriendo VentasFrame...");

            setTitle("Gestión de Ventas");
            setSize(900, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(null);
            setLocationRelativeTo(null);

            JLabel clienteLabel = new JLabel("Cliente:");
            clienteLabel.setBounds(20, 20, 100, 25);
            add(clienteLabel);

            comboClientes = new JComboBox<>();
            comboClientes.setBounds(120, 20, 200, 25);
            add(comboClientes);

            JLabel productoLabel = new JLabel("Producto:");
            productoLabel.setBounds(20, 60, 100, 25);
            add(productoLabel);

            comboProductos = new JComboBox<>();
            comboProductos.setBounds(120, 60, 200, 25);
            add(comboProductos);

            JLabel cantidadLabel = new JLabel("Cantidad:");
            cantidadLabel.setBounds(20, 100, 100, 25);
            add(cantidadLabel);

            cantidadField = new JTextField();
            cantidadField.setBounds(120, 100, 200, 25);
            add(cantidadField);

            JLabel montoTotalLabel = new JLabel("Monto Total:");
            montoTotalLabel.setBounds(20, 140, 100, 25);
            add(montoTotalLabel);

            montoTotalField = new JTextField();
            montoTotalField.setBounds(120, 140, 200, 25);
            montoTotalField.setEditable(false);
            add(montoTotalField);

            JLabel empleadoLabel = new JLabel("Empleado:");
            empleadoLabel.setBounds(20, 180, 100, 25);
            add(empleadoLabel);

            comboEmpleados = new JComboBox<>();
            comboEmpleados.setBounds(120, 180, 200, 25);
            add(comboEmpleados);

            // Botones para agregar nuevos datos manualmente
            JButton btnNuevoCliente = new JButton("Nuevo Cliente");
            btnNuevoCliente.setBounds(330, 20, 150, 25);
            add(btnNuevoCliente);

            JButton btnNuevoProducto = new JButton("Nuevo Producto");
            btnNuevoProducto.setBounds(330, 60, 150, 25);
            add(btnNuevoProducto);

            JButton btnNuevoEmpleado = new JButton("Nuevo Empleado");
            btnNuevoEmpleado.setBounds(330, 180, 150, 25);
            add(btnNuevoEmpleado);

            JButton agregarBtn = new JButton("Agregar");
            agregarBtn.setBounds(500, 20, 120, 30);
            add(agregarBtn);

            JButton editarBtn = new JButton("Editar");
            editarBtn.setBounds(500, 60, 120, 30);
            add(editarBtn);

            JButton eliminarBtn = new JButton("Eliminar");
            eliminarBtn.setBounds(500, 100, 120, 30);
            add(eliminarBtn);

            JButton listarBtn = new JButton("Listar");
            listarBtn.setBounds(500, 140, 120, 30);
            add(listarBtn);

            modeloTabla = new DefaultTableModel(new String[]{"ID Venta", "Cliente", "Producto", "Cantidad", "Monto Total", "Empleado"}, 0);
            tablaVentas = new JTable(modeloTabla);
            JScrollPane scrollPane = new JScrollPane(tablaVentas);
            scrollPane.setBounds(20, 270, 840, 260);
            add(scrollPane);

            listarBtn.addActionListener(e -> cargarVentas());
            agregarBtn.addActionListener(e -> agregarVenta());
            editarBtn.addActionListener(e -> editarVenta());
            eliminarBtn.addActionListener(e -> eliminarVenta());

            tablaVentas.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    cargarVentaDesdeTabla();
                }
            });

            cantidadField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    calcularMontoTotal();
                }
            });

            comboProductos.addActionListener(e -> calcularMontoTotal());

            // Botones para ingresar datos manuales
            btnNuevoCliente.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(this, "Ingrese cliente (Ej: 4 - Pedro Soto):");
                if (input != null && !input.trim().isEmpty()) {
                    comboClientes.addItem(input.trim());
                }
            });

            btnNuevoProducto.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(this, "Ingrese producto con precio (Ej: 13 - Café - 120.0):");
                if (input != null && !input.trim().isEmpty()) {
                    comboProductos.addItem(input.trim());
                }
            });

            btnNuevoEmpleado.addActionListener(e -> {
                String input = JOptionPane.showInputDialog(this, "Ingrese empleado (Ej: 102 - José Quesada):");
                if (input != null && !input.trim().isEmpty()) {
                    comboEmpleados.addItem(input.trim());
                }
            });

            setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar VentasFrame: " + e.getMessage());
        }
    }

    private void calcularMontoTotal() {
        try {
            String productoSeleccionado = (String) comboProductos.getSelectedItem();
            if (productoSeleccionado == null) {
                montoTotalField.setText("");
                return;
            }
            double precio = Double.parseDouble(productoSeleccionado.split(" - ")[2]);
            int cantidad = Integer.parseInt(cantidadField.getText().trim());
            double total = precio * cantidad;
            montoTotalField.setText(String.format("%.2f", total));
        } catch (Exception e) {
            montoTotalField.setText("");
        }
    }

    private void cargarVentas() {
        modeloTabla.setRowCount(0);
        for (Venta v : listaVentas) {
            modeloTabla.addRow(new Object[]{
                    v.getIdVenta(),
                    v.getCliente(),
                    v.getProducto(),
                    v.getCantidadProductosTotal(),
                    v.getMontoTotal(),
                    v.getEmpleado()
            });
        }
    }

    private void agregarVenta() {
        try {
            String clienteSeleccionado = (String) comboClientes.getSelectedItem();
            String productoSeleccionado = (String) comboProductos.getSelectedItem();
            String empleadoSeleccionado = (String) comboEmpleados.getSelectedItem();
            int cantidad = Integer.parseInt(cantidadField.getText().trim());
            double montoTotal = Double.parseDouble(montoTotalField.getText().trim());

            if (clienteSeleccionado == null || productoSeleccionado == null || empleadoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar cliente, producto y empleado.");
                return;
            }

            Venta v = new Venta(
                    idVentaAutoIncremental++,
                    clienteSeleccionado,
                    productoSeleccionado,
                    cantidad,
                    montoTotal,
                    empleadoSeleccionado
            );

            listaVentas.add(v);
            JOptionPane.showMessageDialog(this, "Venta agregada.");
            limpiarCampos();
            cargarVentas();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad o monto inválidos.");
        }
    }

    private void editarVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una venta para editar.");
            return;
        }
        try {
            String clienteSeleccionado = (String) comboClientes.getSelectedItem();
            String productoSeleccionado = (String) comboProductos.getSelectedItem();
            String empleadoSeleccionado = (String) comboEmpleados.getSelectedItem();
            int cantidad = Integer.parseInt(cantidadField.getText().trim());
            double montoTotal = Double.parseDouble(montoTotalField.getText().trim());

            if (clienteSeleccionado == null || productoSeleccionado == null || empleadoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar cliente, producto y empleado.");
                return;
            }

            Venta v = listaVentas.get(fila);
            v.setCliente(clienteSeleccionado);
            v.setProducto(productoSeleccionado);
            v.setCantidadProductosTotal(cantidad);
            v.setMontoTotal(montoTotal);
            v.setEmpleado(empleadoSeleccionado);

            JOptionPane.showMessageDialog(this, "Venta actualizada.");
            limpiarCampos();
            cargarVentas();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad o monto inválidos.");
        }
    }

    private void eliminarVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una venta para eliminar.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            listaVentas.remove(fila);
            JOptionPane.showMessageDialog(this, "Venta eliminada.");
            limpiarCampos();
            cargarVentas();
        }
    }

    private void limpiarCampos() {
        comboClientes.setSelectedIndex(-1);
        comboProductos.setSelectedIndex(-1);
        comboEmpleados.setSelectedIndex(-1);
        cantidadField.setText("");
        montoTotalField.setText("");
    }

    private void cargarVentaDesdeTabla() {
        int fila = tablaVentas.getSelectedRow();
        if (fila != -1) {
            Venta v = listaVentas.get(fila);

            for (int i = 0; i < comboClientes.getItemCount(); i++) {
                if (comboClientes.getItemAt(i).equals(v.getCliente())) {
                    comboClientes.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < comboProductos.getItemCount(); i++) {
                if (comboProductos.getItemAt(i).equals(v.getProducto())) {
                    comboProductos.setSelectedIndex(i);
                    break;
                }
            }
            cantidadField.setText(String.valueOf(v.getCantidadProductosTotal()));
            montoTotalField.setText(String.format("%.2f", v.getMontoTotal()));
            for (int i = 0; i < comboEmpleados.getItemCount(); i++) {
                if (comboEmpleados.getItemAt(i).equals(v.getEmpleado())) {
                    comboEmpleados.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    static class Venta {
        private int idVenta;
        private String cliente;
        private String producto;
        private int cantidadProductosTotal;
        private double montoTotal;
        private String empleado;

        public Venta(int idVenta, String cliente, String producto, int cantidadProductosTotal, double montoTotal, String empleado) {
            this.idVenta = idVenta;
            this.cliente = cliente;
            this.producto = producto;
            this.cantidadProductosTotal = cantidadProductosTotal;
            this.montoTotal = montoTotal;
            this.empleado = empleado;
        }

        public int getIdVenta() {
            return idVenta;
        }

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }

        public String getProducto() {
            return producto;
        }

        public void setProducto(String producto) {
            this.producto = producto;
        }

        public int getCantidadProductosTotal() {
            return cantidadProductosTotal;
        }

        public void setCantidadProductosTotal(int cantidadProductosTotal) {
            this.cantidadProductosTotal = cantidadProductosTotal;
        }

        public double getMontoTotal() {
            return montoTotal;
        }

        public void setMontoTotal(double montoTotal) {
            this.montoTotal = montoTotal;
        }

        public String getEmpleado() {
            return empleado;
        }

        public void setEmpleado(String empleado) {
            this.empleado = empleado;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentasFrame::new);
}
}