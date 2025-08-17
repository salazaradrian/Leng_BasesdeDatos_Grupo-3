package repositorio;

import modelo.Venta;
import conexion.ConexionOracle;
import oracle.jdbc.OracleTypes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// VentasRepositorio.java
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.Date;


public class VentasRepositorio {

    // Agregar venta con procedimiento
    public boolean agregarVenta(Venta venta) {
        String sql = "{call pkg_ventas.agregar_venta(?,?,?,?,?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, venta.getIdCliente());
            cs.setDouble(2, venta.getMontoTotal());
            cs.setInt(3, venta.getCantidadProductosTotal());
            cs.setInt(4, venta.getIdEmpleado());
            cs.setInt(5, venta.getIdProducto());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar venta: " + e.getMessage());
            return false;
        }
    }

    // Editar venta con procedimiento
    public boolean editarVenta(Venta venta) {
        String sql = "{call pkg_ventas.editar_venta(?,?,?,?,?,?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, venta.getIdVenta());
            cs.setInt(2, venta.getIdCliente());
            cs.setDouble(3, venta.getMontoTotal());
            cs.setInt(4, venta.getCantidadProductosTotal());
            cs.setInt(5, venta.getIdEmpleado());
            cs.setInt(6, venta.getIdProducto());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al editar venta: " + e.getMessage());
            return false;
        }
    }

    // Eliminar venta con funcion
    public boolean eliminarVenta(int idVentas) {
        String sql = "{call pkg_ventas.eliminar_venta(?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idVentas);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar venta: " + e.getMessage());
            return false;
        }
    }

 


    // Listar ventas usando funcion
    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();
        String sql = "{? = call pkg_ventas.listar_ventas()}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            // Nota: Aquí, el ResultSet no se cierra automáticamente con try-with-resources.
            ResultSet rs = (ResultSet) cs.getObject(1);
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
            System.out.println("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }
    
  
    
}



//public class VentasRepositorio {
//
//    public List<Venta> listarVentas() {
//        List<Venta> lista = new ArrayList<>();
//        String sql = "SELECT * FROM ventas";
//
//        try (Connection conn = ConexionOracle.conectar();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                Venta v = new Venta();
//                v.setIdVenta(rs.getInt("id_ventas"));
//                v.setIdCliente(rs.getInt("id_cliente"));
//                v.setMontoTotal(rs.getDouble("monto_total"));
//                v.setCantidadProductosTotal(rs.getInt("cantidad_productos_total"));
//                v.setIdEmpleado(rs.getInt("id_empleado"));
//                v.setIdProducto(rs.getInt("id_producto"));
//
//                lista.add(v);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return lista;
//    }
//
//    public boolean agregarVenta(Venta v) {
//        String sql = "INSERT INTO ventas (id_cliente, monto_total, cantidad_productos_total, id_empleado, id_producto) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, v.getIdCliente());
//            ps.setDouble(2, v.getMontoTotal());
//            ps.setInt(3, v.getCantidadProductosTotal());
//            ps.setInt(4, v.getIdEmpleado());
//            ps.setInt(5, v.getIdProducto());
//
//            int filas = ps.executeUpdate();
//            return filas > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean actualizarVenta(Venta v) {
//        String sql = "UPDATE ventas SET id_cliente = ?, monto_total = ?, cantidad_productos_total = ?, id_empleado = ?, id_producto = ? WHERE id_ventas = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, v.getIdCliente());
//            ps.setDouble(2, v.getMontoTotal());
//            ps.setInt(3, v.getCantidadProductosTotal());
//            ps.setInt(4, v.getIdEmpleado());
//            ps.setInt(5, v.getIdProducto());
//            ps.setInt(6, v.getIdVenta());
//
//            int filas = ps.executeUpdate();
//            return filas > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean eliminarVenta(int id) {
//        String sql = "DELETE FROM ventas WHERE id_ventas = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//
//            int filas = ps.executeUpdate();
//            return filas > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
// }
//}
//}
