package repositorio;

import conexion.ConexionOracle;
import modelo.Ingrediente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import oracle.jdbc.OracleTypes;

//public class IngredienteRepositorio {
//
//    // Agregar ingrediente 
//    public boolean agregarIngrediente(Ingrediente ingrediente) {
//    String sql = "INSERT INTO ingredientes (nombre, cantidad) VALUES (?, ?)";
//    try (Connection conn = ConexionOracle.conectar();
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//        stmt.setString(1, ingrediente.getNombre());
//        stmt.setInt(2, ingrediente.getCantidad());
//
//        return stmt.executeUpdate() > 0;
//    } catch (SQLException e) {
//        e.printStackTrace();
//        return false;
//    }
//}
public class IngredienteRepositorio {

    //Agregar ingrediente usando stored procedure
    public boolean agregarIngrediente(Ingrediente ingrediente) {
        String sql = "{call pkg_ingredientes.agregar_ingrediente(?,?)}";
        try (Connection conn = ConexionOracle.conectar(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, ingrediente.getNombre());
            cs.setInt(2, ingrediente.getCantidad());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    
//        // Listar todos los ingredientes
//    public List<Ingrediente> listarIngredientes() {
//    List<Ingrediente> lista = new ArrayList<>();
//    String sql = "SELECT id_ingrediente, nombre, cantidad FROM ingredientes";
//
//    try (Connection conn = ConexionOracle.conectar();
//         Statement stmt = conn.createStatement();
//         ResultSet rs = stmt.executeQuery(sql)) {
//
//        while (rs.next()) {
//            Ingrediente ing = new Ingrediente();
//            ing.setIdIngrediente(rs.getInt("id_ingrediente"));
//            ing.setNombre(rs.getString("nombre"));
//            ing.setCantidad(rs.getInt("cantidad"));
//            lista.add(ing);
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return lista;
//}
    
    
    // Listar todos los ingredientes
    public List<Ingrediente> listarIngredientes() {
        List<Ingrediente> lista = new ArrayList<>();
        String sql = "{? = call pkg_ingredientes.listar_ingredientes()}";

        try (Connection conn = ConexionOracle.conectar(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                Ingrediente c = new Ingrediente();
                c.setIdIngrediente(rs.getInt("id_ingrediente"));
                c.setNombre(rs.getString("nombre"));
                c.setCantidad(rs.getInt("cantidad"));
               
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar ingredientes: " + e.getMessage());
        }

        return lista;
    }
    
    
//        // Actualizar ingrediente
//   public boolean actualizarIngrediente(Ingrediente ingrediente) {
//    String sql = "UPDATE ingredientes SET nombre = ?, cantidad = ? WHERE id_ingrediente = ?";
//    try (Connection conn = ConexionOracle.conectar();
//         PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//        stmt.setString(1, ingrediente.getNombre());
//        stmt.setInt(2, ingrediente.getCantidad());
//        stmt.setInt(3, ingrediente.getIdIngrediente());
//
//        return stmt.executeUpdate() > 0;
//    } catch (SQLException e) {
//        e.printStackTrace();
//        return false;
//    }
//}

    // Actualizar ingrediente
    public boolean actualizarIngrediente(Ingrediente ingrediente) {
        String sql = "{call pkg_ingredientes.actualizar_ingrediente (?,?)}";
           try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, ingrediente.getIdIngrediente());
            cs.setString(2, ingrediente.getNombre());
           

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar ingrediente: " + e.getMessage());
            return false;
        }
    }

//    // Eliminar ingrediente
//    public boolean eliminarIngrediente(int idIngrediente) {
//        String sql = "DELETE FROM ingredientes WHERE id_ingrediente = ?";
//
//        try (Connection conn = ConexionOracle.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, idIngrediente);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al eliminar ingrediente: " + e.getMessage());
//            return false;
//        }
//    }
    
      // Eliminar ingrediente usando procedimiento almacenado
    public boolean eliminarIngrediente(int idIngrediente) {
        String sql = "{call pkg_ingredientes.eliminar_ingrediente(?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idIngrediente);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar ingrediente: " + e.getMessage());
            return false;
        }
    }
    
}

