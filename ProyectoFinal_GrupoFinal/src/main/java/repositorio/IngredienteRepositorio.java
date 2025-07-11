package repositorio;

import conexion.ConexionOracle;
import modelo.Ingrediente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredienteRepositorio {

    // Agregar ingrediente
    public boolean agregarIngrediente(Ingrediente ingrediente) {
        String sql = "INSERT INTO ingredientes (nombre) VALUES (?)";

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ingrediente.getNombre());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar ingrediente: " + e.getMessage());
            return false;
        }
    }

    // Listar todos los ingredientes
    public List<Ingrediente> listarIngredientes() {
        List<Ingrediente> lista = new ArrayList<>();
        String sql = "SELECT id_ingrediente, nombre FROM ingredientes";

        try (Connection conn = ConexionOracle.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Ingrediente ing = new Ingrediente();
                ing.setIdIngrediente(rs.getInt("id_ingrediente"));
                ing.setNombre(rs.getString("nombre"));
                lista.add(ing);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar ingredientes: " + e.getMessage());
        }

        return lista;
    }

    // Actualizar ingrediente
    public boolean actualizarIngrediente(Ingrediente ingrediente) {
        String sql = "UPDATE ingredientes SET nombre = ? WHERE id_ingrediente = ?";

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ingrediente.getNombre());
            ps.setInt(2, ingrediente.getIdIngrediente());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar ingrediente: " + e.getMessage());
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
