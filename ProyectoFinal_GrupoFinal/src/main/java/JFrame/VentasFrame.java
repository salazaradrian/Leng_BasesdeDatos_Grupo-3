package JFrame;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import conexion.ConexionOracle;
import javax.swing.table.DefaultTableModel;

public class VentasFrame extends JFrame {

    private JComboBox<String> comboClientes, comboProductos, comboEmpleados;
    private JTextField cantidadField, montoTotalField;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;

    public VentasFrame() {
        try {
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

            cargarCombos();
            setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar VentasFrame: " + e.getMessage());
        }
    }

    private void cargarCombos() {
        try (Connection conn = ConexionOracle.conectar()) {
            Statement stmt = conn.createStatement();

            ResultSet rsClientes = stmt.executeQuery("SELECT nombre FROM clientes");
            while (rsClientes.next()) {
                comboClientes.addItem(rsClientes.getString("nombre"));
            }

            ResultSet rsProductos = stmt.executeQuery("SELECT nombre || ' - ' || precio AS producto FROM productos");
            while (rsProductos.next()) {
                comboProductos.addItem(rsProductos.getString("producto"));
            }

            ResultSet rsEmpleados = stmt.executeQuery("SELECT nombre FROM empleados");
            while (rsEmpleados.next()) {
                comboEmpleados.addItem(rsEmpleados.getString("nombre"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar combos: " + e.getMessage());
        }
    }

    private void calcularMontoTotal() {
        try {
            String productoSeleccionado = (String) comboProductos.getSelectedItem();
            if (productoSeleccionado == null) {
                montoTotalField.setText("");
                return;
            }
            double precio = Double.parseDouble(productoSeleccionado.split(" - ")[1]);
            int cantidad = Integer.parseInt(cantidadField.getText().trim());
            double total = precio * cantidad;
            montoTotalField.setText(String.format("%.2f", total));
        } catch (Exception e) {
            montoTotalField.setText("");
        }
    }

    private void cargarVentas() {
        modeloTabla.setRowCount(0);
        try (Connection conn = ConexionOracle.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ventas")) {

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getInt("id_venta"),
                    rs.getString("cliente"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("monto_total"),
                    rs.getString("empleado")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar ventas: " + e.getMessage());
        }
    }

    private void agregarVenta() {
        try (Connection conn = ConexionOracle.conectar()) {
            String sql = "INSERT INTO ventas (cliente, producto, cantidad, monto_total, empleado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, (String) comboClientes.getSelectedItem());
            ps.setString(2, (String) comboProductos.getSelectedItem());
            ps.setInt(3, Integer.parseInt(cantidadField.getText().trim()));
            ps.setDouble(4, Double.parseDouble(montoTotalField.getText().trim()));
            ps.setString(5, (String) comboEmpleados.getSelectedItem());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Venta agregada.");
            limpiarCampos();
            cargarVentas();

        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar venta: " + e.getMessage());
        }
    }

    private void editarVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una venta para editar.");
            return;
        }

        int idVenta = (int) modeloTabla.getValueAt(fila, 0);

        try (Connection conn = ConexionOracle.conectar()) {
            String sql = "UPDATE ventas SET cliente=?, producto=?, cantidad=?, monto_total=?, empleado=? WHERE id_venta=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, (String) comboClientes.getSelectedItem());
            ps.setString(2, (String) comboProductos.getSelectedItem());
            ps.setInt(3, Integer.parseInt(cantidadField.getText().trim()));
            ps.setDouble(4, Double.parseDouble(montoTotalField.getText().trim()));
            ps.setString(5, (String) comboEmpleados.getSelectedItem());
            ps.setInt(6, idVenta);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Venta actualizada.");
            limpiarCampos();
            cargarVentas();

        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al editar venta: " + e.getMessage());
        }
    }

    private void eliminarVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una venta para eliminar.");
            return;
        }

        int idVenta = (int) modeloTabla.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexionOracle.conectar()) {
                String sql = "DELETE FROM ventas WHERE id_venta=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idVenta);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Venta eliminada.");
                limpiarCampos();
                cargarVentas();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar venta: " + e.getMessage());
            }
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
            comboClientes.setSelectedItem(modeloTabla.getValueAt(fila, 1));
            comboProductos.setSelectedItem(modeloTabla.getValueAt(fila, 2));
            cantidadField.setText(modeloTabla.getValueAt(fila, 3).toString());
            montoTotalField.setText(modeloTabla.getValueAt(fila, 4).toString());
            comboEmpleados.setSelectedItem(modeloTabla.getValueAt(fila, 5));
        }
    }
}
