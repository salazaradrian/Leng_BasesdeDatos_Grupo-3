package JFrame;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import modelo.Venta;
import repositorio.VentasRepositorio;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import conexion.ConexionOracle;

public class VentasFrame extends JFrame {

    // Instanciamos el repositorio para usar sus métodos
    private VentasRepositorio ventasRepo;

    // Componentes de la UI
    private JComboBox<String> comboClientes, comboProductos, comboEmpleados;
    private JTextField cantidadField, montoTotalField;
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    
    // Mapas para almacenar los IDs de los combos
    private Map<String, Integer> clientesMap = new HashMap<>();
    private Map<String, Integer> productosMap = new HashMap<>();
    private Map<String, Integer> empleadosMap = new HashMap<>();


    public VentasFrame() {
        try {
            setTitle("Gestión de Ventas");
            setSize(900, 600);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(null);
            setLocationRelativeTo(null);

            // Inicializamos el repositorio
            ventasRepo = new VentasRepositorio();
            
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

            modeloTabla = new DefaultTableModel(new String[]{"ID Venta", "ID Cliente", "ID Producto", "Cantidad", "Monto Total", "ID Empleado", "Fecha"}, 0);
            tablaVentas = new JTable(modeloTabla);
            JScrollPane scrollPane = new JScrollPane(tablaVentas);
            scrollPane.setBounds(20, 270, 840, 260);
            add(scrollPane);

            // Conectamos los botones a los métodos que usan el repositorio
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
            //cargarVentas(); // Cargar la tabla al iniciar el frame
            setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar VentasFrame: " + e.getMessage());
        }
    }

    // --- Métodos de la UI para interactuar con el repositorio ---
    
    private void cargarCombos() {
        try (Connection conn = ConexionOracle.conectar()) {
            Statement stmt = conn.createStatement();

            ResultSet rsClientes = stmt.executeQuery("SELECT id_cliente, nombre FROM clientes");
            while (rsClientes.next()) {
                int id = rsClientes.getInt("id_cliente");
                String nombre = rsClientes.getString("nombre");
                comboClientes.addItem(nombre);
                clientesMap.put(nombre, id);
            }

            ResultSet rsProductos = stmt.executeQuery("SELECT id_producto, nombre || ' - ' || precio AS producto FROM productos");
            while (rsProductos.next()) {
                int id = rsProductos.getInt("id_producto");
                String producto = rsProductos.getString("producto");
                comboProductos.addItem(producto);
                productosMap.put(producto, id);
            }

            ResultSet rsEmpleados = stmt.executeQuery("SELECT id_empleado, nombre FROM empleados");
            while (rsEmpleados.next()) {
                int id = rsEmpleados.getInt("id_empleado");
                String nombre = rsEmpleados.getString("nombre");
                comboEmpleados.addItem(nombre);
                empleadosMap.put(nombre, id);
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
        List<Venta> ventas = ventasRepo.listarVentas();
        for (Venta v : ventas) {
            modeloTabla.addRow(new Object[]{
                v.getIdVenta(),
                v.getIdCliente(), // Ahora mostramos el ID en lugar del nombre
                v.getIdProducto(),
                v.getCantidadProductosTotal(),
                v.getMontoTotal(),
                v.getIdEmpleado(),
                v.getFecha()
            });
        }
    }

    private void agregarVenta() {
        try {
            String nombreCliente = (String) comboClientes.getSelectedItem();
            String nombreProducto = (String) comboProductos.getSelectedItem();
            String nombreEmpleado = (String) comboEmpleados.getSelectedItem();
            
           
            if (nombreCliente == null || nombreProducto == null || nombreEmpleado == null ||
                cantidadField.getText().trim().isEmpty() || montoTotalField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Venta nuevaVenta = new Venta();
            nuevaVenta.setIdCliente(clientesMap.get(nombreCliente));
            nuevaVenta.setIdProducto(productosMap.get(nombreProducto));
            nuevaVenta.setIdEmpleado(empleadosMap.get(nombreEmpleado));
            nuevaVenta.setCantidadProductosTotal(Integer.parseInt(cantidadField.getText().trim()));
            nuevaVenta.setMontoTotal(Double.parseDouble(montoTotalField.getText().trim()));

            if (ventasRepo.agregarVenta(nuevaVenta)) {
                JOptionPane.showMessageDialog(this, "Venta agregada.");
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una venta para editar.");
            return;
        }
        
        try {
            int idVenta = (int) modeloTabla.getValueAt(fila, 0);
            
            String nombreCliente = (String) comboClientes.getSelectedItem();
            String nombreProducto = (String) comboProductos.getSelectedItem();
            String nombreEmpleado = (String) comboEmpleados.getSelectedItem();
            
            Venta ventaAEditar = new Venta();
            ventaAEditar.setIdVenta(idVenta);
            ventaAEditar.setIdCliente(clientesMap.get(nombreCliente));
            ventaAEditar.setIdProducto(productosMap.get(nombreProducto));
            ventaAEditar.setIdEmpleado(empleadosMap.get(nombreEmpleado));
            ventaAEditar.setCantidadProductosTotal(Integer.parseInt(cantidadField.getText().trim()));
            ventaAEditar.setMontoTotal(Double.parseDouble(montoTotalField.getText().trim()));
            
            if (ventasRepo.editarVenta(ventaAEditar)) {
                JOptionPane.showMessageDialog(this, "Venta actualizada.");
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al editar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en el formato de los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarVenta() {
        int fila = tablaVentas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una venta para eliminar.");
            return;
        }

        int idVenta = (int) modeloTabla.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar venta con ID " + idVenta + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (ventasRepo.eliminarVenta(idVenta)) {
                JOptionPane.showMessageDialog(this, "Venta eliminada.");
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar venta.", "Error", JOptionPane.ERROR_MESSAGE);
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
           
            int idCliente = (int) modeloTabla.getValueAt(fila, 1);
            int idProducto = (int) modeloTabla.getValueAt(fila, 2);
            int idEmpleado = (int) modeloTabla.getValueAt(fila, 5);

            comboClientes.setSelectedItem(getKeyByValue(clientesMap, idCliente));
            comboProductos.setSelectedItem(getKeyByValue(productosMap, idProducto));
            comboEmpleados.setSelectedItem(getKeyByValue(empleadosMap, idEmpleado));

            cantidadField.setText(modeloTabla.getValueAt(fila, 3).toString());
            montoTotalField.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }
    
    // Método auxiliar para obtener la clave (nombre) a partir de un valor (id)
    private <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentasFrame().setVisible(true));
    }
}









//package JFrame;
//
//import javax.swing.*;
//import java.awt.event.*;
//import java.sql.*;
//import conexion.ConexionOracle;
//import javax.swing.table.DefaultTableModel;
//
//public class VentasFrame extends JFrame {
//
//    private JComboBox<String> comboClientes, comboProductos, comboEmpleados;
//    private JTextField cantidadField, montoTotalField;
//    private JTable tablaVentas;
//    private DefaultTableModel modeloTabla;
//
//    public VentasFrame() {
//        try {
//            setTitle("Gestión de Ventas");
//            setSize(900, 600);
//            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            setLayout(null);
//            setLocationRelativeTo(null);
//
//            JLabel clienteLabel = new JLabel("Cliente:");
//            clienteLabel.setBounds(20, 20, 100, 25);
//            add(clienteLabel);
//
//            comboClientes = new JComboBox<>();
//            comboClientes.setBounds(120, 20, 200, 25);
//            add(comboClientes);
//
//            JLabel productoLabel = new JLabel("Producto:");
//            productoLabel.setBounds(20, 60, 100, 25);
//            add(productoLabel);
//
//            comboProductos = new JComboBox<>();
//            comboProductos.setBounds(120, 60, 200, 25);
//            add(comboProductos);
//
//            JLabel cantidadLabel = new JLabel("Cantidad:");
//            cantidadLabel.setBounds(20, 100, 100, 25);
//            add(cantidadLabel);
//
//            cantidadField = new JTextField();
//            cantidadField.setBounds(120, 100, 200, 25);
//            add(cantidadField);
//
//            JLabel montoTotalLabel = new JLabel("Monto Total:");
//            montoTotalLabel.setBounds(20, 140, 100, 25);
//            add(montoTotalLabel);
//
//            montoTotalField = new JTextField();
//            montoTotalField.setBounds(120, 140, 200, 25);
//            montoTotalField.setEditable(false);
//            add(montoTotalField);
//
//            JLabel empleadoLabel = new JLabel("Empleado:");
//            empleadoLabel.setBounds(20, 180, 100, 25);
//            add(empleadoLabel);
//
//            comboEmpleados = new JComboBox<>();
//            comboEmpleados.setBounds(120, 180, 200, 25);
//            add(comboEmpleados);
//
//            JButton agregarBtn = new JButton("Agregar");
//            agregarBtn.setBounds(500, 20, 120, 30);
//            add(agregarBtn);
//
//            JButton editarBtn = new JButton("Editar");
//            editarBtn.setBounds(500, 60, 120, 30);
//            add(editarBtn);
//
//            JButton eliminarBtn = new JButton("Eliminar");
//            eliminarBtn.setBounds(500, 100, 120, 30);
//            add(eliminarBtn);
//
//            JButton listarBtn = new JButton("Listar");
//            listarBtn.setBounds(500, 140, 120, 30);
//            add(listarBtn);
//
//            modeloTabla = new DefaultTableModel(new String[]{"ID Venta", "Cliente", "Producto", "Cantidad", "Monto Total", "Empleado"}, 0);
//            tablaVentas = new JTable(modeloTabla);
//            JScrollPane scrollPane = new JScrollPane(tablaVentas);
//            scrollPane.setBounds(20, 270, 840, 260);
//            add(scrollPane);
//
//            listarBtn.addActionListener(e -> cargarVentas());
//            agregarBtn.addActionListener(e -> agregarVenta());
//            editarBtn.addActionListener(e -> editarVenta());
//            eliminarBtn.addActionListener(e -> eliminarVenta());
//
//            tablaVentas.addMouseListener(new MouseAdapter() {
//                public void mouseClicked(MouseEvent e) {
//                    cargarVentaDesdeTabla();
//                }
//            });
//
//            cantidadField.addKeyListener(new KeyAdapter() {
//                public void keyReleased(KeyEvent e) {
//                    calcularMontoTotal();
//                }
//            });
//
//            comboProductos.addActionListener(e -> calcularMontoTotal());
//
//            cargarCombos();
//            setVisible(true);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error al cargar VentasFrame: " + e.getMessage());
//        }
//    }
//
//    private void cargarCombos() {
//        try (Connection conn = ConexionOracle.conectar()) {
//            Statement stmt = conn.createStatement();
//
//            ResultSet rsClientes = stmt.executeQuery("SELECT nombre FROM clientes");
//            while (rsClientes.next()) {
//                comboClientes.addItem(rsClientes.getString("nombre"));
//            }
//
//            ResultSet rsProductos = stmt.executeQuery("SELECT nombre || ' - ' || precio AS producto FROM productos");
//            while (rsProductos.next()) {
//                comboProductos.addItem(rsProductos.getString("producto"));
//            }
//
//            ResultSet rsEmpleados = stmt.executeQuery("SELECT nombre FROM empleados");
//            while (rsEmpleados.next()) {
//                comboEmpleados.addItem(rsEmpleados.getString("nombre"));
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Error al cargar combos: " + e.getMessage());
//        }
//    }
//
//    private void calcularMontoTotal() {
//        try {
//            String productoSeleccionado = (String) comboProductos.getSelectedItem();
//            if (productoSeleccionado == null) {
//                montoTotalField.setText("");
//                return;
//            }
//            double precio = Double.parseDouble(productoSeleccionado.split(" - ")[1]);
//            int cantidad = Integer.parseInt(cantidadField.getText().trim());
//            double total = precio * cantidad;
//            montoTotalField.setText(String.format("%.2f", total));
//        } catch (Exception e) {
//            montoTotalField.setText("");
//        }
//    }
//
//    private void cargarVentas() {
//        modeloTabla.setRowCount(0);
//        try (Connection conn = ConexionOracle.conectar();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM ventas")) {
//
//            while (rs.next()) {
//                modeloTabla.addRow(new Object[]{
//                    rs.getInt("id_venta"),
//                    rs.getString("cliente"),
//                    rs.getString("producto"),
//                    rs.getInt("cantidad"),
//                    rs.getDouble("monto_total"),
//                    rs.getString("empleado")
//                });
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Error al cargar ventas: " + e.getMessage());
//        }
//    }
//
//    private void agregarVenta() {
//        try (Connection conn = ConexionOracle.conectar()) {
//            String sql = "INSERT INTO ventas (cliente, producto, cantidad, monto_total, empleado) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            ps.setString(1, (String) comboClientes.getSelectedItem());
//            ps.setString(2, (String) comboProductos.getSelectedItem());
//            ps.setInt(3, Integer.parseInt(cantidadField.getText().trim()));
//            ps.setDouble(4, Double.parseDouble(montoTotalField.getText().trim()));
//            ps.setString(5, (String) comboEmpleados.getSelectedItem());
//
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Venta agregada.");
//            limpiarCampos();
//            cargarVentas();
//
//        } catch (SQLException | NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Error al agregar venta: " + e.getMessage());
//        }
//    }
//
//    private void editarVenta() {
//        int fila = tablaVentas.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Selecciona una venta para editar.");
//            return;
//        }
//
//        int idVenta = (int) modeloTabla.getValueAt(fila, 0);
//
//        try (Connection conn = ConexionOracle.conectar()) {
//            String sql = "UPDATE ventas SET cliente=?, producto=?, cantidad=?, monto_total=?, empleado=? WHERE id_venta=?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//
//            ps.setString(1, (String) comboClientes.getSelectedItem());
//            ps.setString(2, (String) comboProductos.getSelectedItem());
//            ps.setInt(3, Integer.parseInt(cantidadField.getText().trim()));
//            ps.setDouble(4, Double.parseDouble(montoTotalField.getText().trim()));
//            ps.setString(5, (String) comboEmpleados.getSelectedItem());
//            ps.setInt(6, idVenta);
//
//            ps.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Venta actualizada.");
//            limpiarCampos();
//            cargarVentas();
//
//        } catch (SQLException | NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Error al editar venta: " + e.getMessage());
//        }
//    }
//
//    private void eliminarVenta() {
//        int fila = tablaVentas.getSelectedRow();
//        if (fila == -1) {
//            JOptionPane.showMessageDialog(this, "Selecciona una venta para eliminar.");
//            return;
//        }
//
//        int idVenta = (int) modeloTabla.getValueAt(fila, 0);
//
//        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar venta?", "Confirmar", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            try (Connection conn = ConexionOracle.conectar()) {
//                String sql = "DELETE FROM ventas WHERE id_venta=?";
//                PreparedStatement ps = conn.prepareStatement(sql);
//                ps.setInt(1, idVenta);
//                ps.executeUpdate();
//
//                JOptionPane.showMessageDialog(this, "Venta eliminada.");
//                limpiarCampos();
//                cargarVentas();
//
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(this, "Error al eliminar venta: " + e.getMessage());
//            }
//        }
//    }
//
//    private void limpiarCampos() {
//        comboClientes.setSelectedIndex(-1);
//        comboProductos.setSelectedIndex(-1);
//        comboEmpleados.setSelectedIndex(-1);
//        cantidadField.setText("");
//        montoTotalField.setText("");
//    }
//
//    private void cargarVentaDesdeTabla() {
//        int fila = tablaVentas.getSelectedRow();
//        if (fila != -1) {
//            comboClientes.setSelectedItem(modeloTabla.getValueAt(fila, 1));
//            comboProductos.setSelectedItem(modeloTabla.getValueAt(fila, 2));
//            cantidadField.setText(modeloTabla.getValueAt(fila, 3).toString());
//            montoTotalField.setText(modeloTabla.getValueAt(fila, 4).toString());
//            comboEmpleados.setSelectedItem(modeloTabla.getValueAt(fila, 5));
//        }
//    }
//}
