package repositorio;

import conexion.ConexionOracle;
import modelo.Compras;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class ComprasRepositorio {

    public boolean agregarCompra(Compras compra) {
        String sql = "{call agregar_compra(?,?,?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, compra.getIdIngrediente()); 
            cs.setDouble(2, compra.getCantidadIngredientes());
            cs.setDouble(3, compra.getMontoTotal());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar compra: " + e.getMessage());
            return false;
        }
    }


    // Listar compras usando la función con cursor
    public List<Compras> listarCompras() {
        List<Compras> lista = new ArrayList<>();
        String sql = "{? = call listar_compras()}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                Compras c = new Compras();
                c.setIdCompra(rs.getInt("id_compra"));
                c.setIdIngrediente(rs.getInt("id_ingrediente"));
                c.setFecha(rs.getDate("fecha"));
                c.setCantidadIngredientes(rs.getDouble("cantidad_ingredientes"));
                c.setMontoTotal(rs.getDouble("monto_total"));

                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar compras: " + e.getMessage());
        }

        return lista;
    }


    // Eliminar compra usando la funcion
    public boolean eliminarCompra(int idCompra) {
        String sql = "{call eliminar_compra(?)}"; 
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCompra);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar compra: " + e.getMessage());
            return false;
        }
    }
}



 // -----------------------------------------------


//public class ComprasRepositorio {
    

//   //Agregar compra usando stored procedure
//    public boolean agregarCompra(Compras compras) {
//        String sql = "{call agregar_compra(?,?,?)}";
//        try (Connection conn = ConexionOracle.conectar(); CallableStatement cs = conn.prepareCall(sql)) {
//
//            cs.setInt(1, compras.getIdIngrediente());
//            cs.setDouble(2, compras.getCantidadIngredientes());
//            cs.setDouble(3, compras.getMontoTotal());
//
//            cs.execute();
//            return true;
//
//        } catch (SQLException e) {
//            System.out.println("Error al agregar compra: " + e.getMessage());
//            return false;
//        }
//    }
//
//
//        // Listar compras
//    public List<Compras> listarCompras() {
//        List<Compras> lista = new ArrayList<>();
//        String sql = "{? = call listar_compras()}";
//
//        try (Connection conn = ConexionOracle.conectar(); CallableStatement cs = conn.prepareCall(sql)) {
//
//            cs.registerOutParameter(1, OracleTypes.CURSOR);
//            cs.execute();
//
//            ResultSet rs = (ResultSet) cs.getObject(1);
//            while (rs.next()) {
//                Compras c = new Compras();
//                c.setIdCompra(rs.getInt("id_compra"));
//                c.setIdIngrediente(rs.getInt("id_ingrediente"));
//                c.setFecha(rs.getDate("fecha"));
//               
//                lista.add(c);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error al listar ingredientes: " + e.getMessage());
//        }
//
//        return lista;
//    }
// 
//
//      // Eliminar compras usando procedimiento almacenado
//    public boolean eliminarCompra(int idCompra) {
//        String sql = "{call eliminar_compra(?)}";
//        try (Connection conn = ConexionOracle.conectar();
//             CallableStatement cs = conn.prepareCall(sql)) {
//
//            cs.setInt(1, idCompra);
//            cs.execute();
//            return true;
//
//        } catch (SQLException e) {
//            System.out.println("Error al eliminar compra: " + e.getMessage());
//            return false;
//        }
//    }
// 
//}

   // -----------------------------------------------

//public class ComprasRepositorio {
//
//   //  Insertar compra
//    public boolean agregarCompra(Compras compra) {
//      //  String sql = "INSERT INTO compras (id_ingrediente, cantidad_ingredientes, monto_total) VALUES (?, ?, ?)";
//        String sql = "INSERT INTO compras (id_ingrediente, fecha, cantidad_ingredientes, monto_total) VALUES (?, ?, ?, ?)";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, compra.getIdIngrediente());
//            ps.setDouble(2, compra.getCantidadIngredientes());
//            ps.setDouble(3, compra.getMontoTotal());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al agregar compra: " + e.getMessage());
//            return false;
//        }
//    }


//    // Listar todas las compras
//    public List<Compras> listarCompras() {
//        List<Compras> lista = new ArrayList<>();
//        String sql = "SELECT id_compra, id_ingrediente, fecha, cantidad_ingredientes, monto_total FROM compras ORDER BY id_compra";
//
//        try (Connection conn = ConexionOracle.conectar();
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery(sql)) {
//
//            while (rs.next()) {
//                Compras c = new Compras();
//                c.setIdCompra(rs.getInt("id_compra"));
//                c.setIdIngrediente(rs.getInt("id_ingrediente"));
//                c.setFecha(rs.getDate("fecha"));
//                c.setCantidadIngredientes(rs.getDouble("cantidad_ingredientes"));
//                c.setMontoTotal(rs.getDouble("monto_total"));
//                lista.add(c);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error al listar compras: " + e.getMessage());
//        }
//        return lista;
//    }

//    // Actualizar compra
//    public boolean actualizarCompra(Compras compra) {
//        String sql = "UPDATE compras SET id_ingrediente = ?, cantidad_ingredientes = ?, monto_total = ? WHERE id_compra = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, compra.getIdIngrediente());
//            ps.setDouble(2, compra.getCantidadIngredientes());
//            ps.setDouble(3, compra.getMontoTotal());
//            ps.setInt(4, compra.getIdCompra());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al actualizar compra: " + e.getMessage());
//            return false;
//        }
//    }

    
    
    
//    // Eliminar compra
//    public boolean eliminarCompra(int idCompra) {
//        String sql = "DELETE FROM compras WHERE id_compra = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, idCompra);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al eliminar compra: " + e.getMessage());
//            return false;
//        }
//    }
