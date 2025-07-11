package repositorio;

import conexion.ConexionOracle;
import modelo.Ingrediente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredienteRepositorio {

    // Agregar ingrediente
    public boolean agregarIngrediente(Ingrediente ingrediente) {
    String sql = "INSERT INTO ingredientes (nombre, cantidad) VALUES (?, ?)";
    try (Connection conn = ConexionOracle.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, ingrediente.getNombre());
        stmt.setInt(2, ingrediente.getCantidad());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    // Listar todos los ingredientes
    public List<Ingrediente> listarIngredientes() {
    List<Ingrediente> lista = new ArrayList<>();
    String sql = "SELECT id_ingrediente, nombre, cantidad FROM ingredientes";

    try (Connection conn = ConexionOracle.conectar();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Ingrediente ing = new Ingrediente();
            ing.setIdIngrediente(rs.getInt("id_ingrediente"));
            ing.setNombre(rs.getString("nombre"));
            ing.setCantidad(rs.getInt("cantidad"));
            lista.add(ing);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}


    // Actualizar ingrediente
   public boolean actualizarIngrediente(Ingrediente ingrediente) {
    String sql = "UPDATE ingredientes SET nombre = ?, cantidad = ? WHERE id_ingrediente = ?";
    try (Connection conn = ConexionOracle.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, ingrediente.getNombre());
        stmt.setInt(2, ingrediente.getCantidad());
        stmt.setInt(3, ingrediente.getIdIngrediente());

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    // Eliminar ingrediente
    public boolean eliminarIngrediente(int idIngrediente) {
        String sql = "DELETE FROM ingredientes WHERE id_ingrediente = ?";

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idIngrediente);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar ingrediente: " + e.getMessage());
            return false;
        }
    }
}
