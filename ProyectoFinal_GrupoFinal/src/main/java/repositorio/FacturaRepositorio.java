// FacturaRepositorio.java
package repositorio;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Factura;
import conexion.ConexionOracle;
import oracle.jdbc.OracleTypes;

public class FacturaRepositorio {

    
    public boolean agregarFactura(Factura factura) {
        // Agregar factura con procedimiento.
        String sql = "{call pkg_factura.agregar_factura(?,?,?,?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, factura.getIdVentas());
            cs.setDate(2, new java.sql.Date(factura.getFecha().getTime()));
            cs.setDouble(3, factura.getImpuesto());
            cs.setDouble(4, factura.getSubtotal());

            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al agregar factura: " + e.getMessage());
            return false;
        }
    }

    
    public boolean editarFactura(Factura factura) {
        // Procedimiento para editar factura.
        String sql = "{call pkg_factura.editar_factura(?,?,?,?,?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, factura.getIdFactura());
            cs.setInt(2, factura.getIdVentas());
            cs.setDate(3, new java.sql.Date(factura.getFecha().getTime())); //getTime() es el "puente" que convierte el objeto java.util.Date en un valor numérico (long) para que la clase java.sql.Date pueda interpretarlo y crear un objeto de fecha válido para la base de datos.
            cs.setDouble(4, factura.getImpuesto());
            cs.setDouble(5, factura.getSubtotal());

            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al editar factura: " + e.getMessage());
            return false;
        }
    }


    public boolean eliminarFactura(int idFactura) {
        // Procedimiento  para eliminar factura.
        String sql = "{call pkg_factura.eliminar_factura(?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idFactura);
            cs.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }

    public List<Factura> listarFacturas() {
        List<Factura> lista = new ArrayList<>();
        // Funcion listar facturas.
        String sql = "{? = call pkg_factura.listar_facturas()}";

        // try-with-resources para la conexión y el CallableStatement
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            // try-with-resources anidado para el ResultSet, asegurando su cierre.
            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                while (rs.next()) {
                    Factura f = new Factura();
                    f.setIdFactura(rs.getInt("id_factura"));
                    f.setIdVentas(rs.getInt("id_ventas"));
                    f.setFecha(rs.getDate("fecha"));
                    f.setImpuesto(rs.getDouble("impuesto"));
                    f.setSubtotal(rs.getDouble("subtotal"));
                    lista.add(f);
                }
            } // El ResultSet se cierra automáticamente aquí.
        } catch (SQLException e) {
            System.out.println("Error al listar facturas: " + e.getMessage());
        } // La conexión y el CallableStatement se cierran automáticamente aquí.
        return lista;
    }
}













//package repositorio;
//
//import modelo.Factura;
//import conexion.ConexionOracle;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FacturaRepositorio {
//
//    public boolean agregarFactura(Factura factura) {
//        String sql = "INSERT INTO facturas (id_ventas, fecha, impuesto, subtotal, total, id_estado) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, factura.getIdVentas());
//            stmt.setDate(2, new java.sql.Date(factura.getFecha().getTime()));
//            stmt.setDouble(3, factura.getImpuesto());
//            stmt.setDouble(4, factura.getSubtotal());
//            stmt.setDouble(5, factura.getSubtotal() + factura.getImpuesto());
//            stmt.setInt(6, factura.getIdEstado());
//
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public List<Factura> listarFacturas() {
//        List<Factura> lista = new ArrayList<>();
//        String sql = "SELECT * FROM facturas";
//
//        try (Connection conn = ConexionOracle.conectar();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                Factura f = new Factura();
//                f.setIdFactura(rs.getInt("id_factura"));
//                f.setIdVentas(rs.getInt("id_ventas"));
//                f.setFecha(rs.getDate("fecha"));
//                f.setImpuesto(rs.getDouble("impuesto"));
//                f.setSubtotal(rs.getDouble("subtotal"));
//                f.setIdEstado(rs.getInt("id_estado"));
//                lista.add(f);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return lista;
//    }
//
//    public boolean actualizarFactura(Factura factura) {
//        String sql = "UPDATE facturas SET id_ventas = ?, fecha = ?, impuesto = ?, subtotal = ?, id_estado = ? WHERE id_factura = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, factura.getIdVentas());
//            stmt.setDate(2, new java.sql.Date(factura.getFecha().getTime()));
//            stmt.setDouble(3, factura.getImpuesto());
//            stmt.setDouble(4, factura.getSubtotal());
//            stmt.setInt(5, factura.getIdEstado());
//            stmt.setInt(6, factura.getIdFactura());
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean eliminarFactura(int idFactura) {
//        String sql = "DELETE FROM facturas WHERE id_factura = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, idFactura);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}

