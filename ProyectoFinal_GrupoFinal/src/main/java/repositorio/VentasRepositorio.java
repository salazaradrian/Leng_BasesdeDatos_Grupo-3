package repositorio;

import modelo.Venta;
import conexion.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentasRepositorio {

    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT * FROM ventas";

        try (Connection conn = ConexionOracle.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Venta v = new Venta();
                v.setIdVenta(rs.getInt("id_ventas"));
                v.setIdCliente(rs.getInt("id_cliente"));
                v.setMontoTotal(rs.getDouble("monto_total"));
                v.setCantidadProductosTotal(rs.getInt("cantidad_productos_total"));
                v.setIdEmpleado(rs.getInt("id_empleado"));
                v.setIdProducto(rs.getInt("id_producto"));

                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean agregarVenta(Venta v) {
        String sql = "INSERT INTO ventas (id_cliente, monto_total, cantidad_productos_total, id_empleado, id_producto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, v.getIdCliente());
            ps.setDouble(2, v.getMontoTotal());
            ps.setInt(3, v.getCantidadProductosTotal());
            ps.setInt(4, v.getIdEmpleado());
            ps.setInt(5, v.getIdProducto());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarVenta(Venta v) {
        String sql = "UPDATE ventas SET id_cliente = ?, monto_total = ?, cantidad_productos_total = ?, id_empleado = ?, id_producto = ? WHERE id_ventas = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, v.getIdCliente());
            ps.setDouble(2, v.getMontoTotal());
            ps.setInt(3, v.getCantidadProductosTotal());
            ps.setInt(4, v.getIdEmpleado());
            ps.setInt(5, v.getIdProducto());
            ps.setInt(6, v.getIdVenta());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarVenta(int id) {
        String sql = "DELETE FROM ventas WHERE id_ventas = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
 }
}
}