package repositorio;

import conexion.ConexionOracle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Receta;

public class RecetasRepositorio {
    
    
    //obtener recertas
public List<String> obtenerRecetas() {
    List<String> recetas = new ArrayList<>();
    String sql = "SELECT id_receta, nombre FROM recetas";

    try (Connection conn = ConexionOracle.conectar();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            int id = rs.getInt("id_receta");
            String nombre = rs.getString("nombre");
            recetas.add(id + " - " + nombre);
        }

    } catch (SQLException e) {
        System.out.println("Error al obtener recetas: " + e.getMessage());
    }

    return recetas;
}

    // Crear receta
    public boolean agregarReceta(Receta receta) {
        String sql = "INSERT INTO recetas (nombre, id_ingrediente) VALUES (?, ?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, receta.getNombre());
            ps.setInt(2, receta.getIdIngrediente());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar receta: " + e.getMessage());
            return false;
        }
    }

    // Leer todas las recetas
    public List<Receta> listarRecetas() {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT id_receta, nombre, id_ingrediente FROM recetas";

        try (Connection conn = ConexionOracle.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Receta r = new Receta();
                r.setIdReceta(rs.getInt("id_receta"));
                r.setNombre(rs.getString("nombre"));
                r.setIdIngrediente(rs.getInt("id_ingrediente"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar recetas: " + e.getMessage());
        }

        return lista;
    }

    // Actualizar receta
    public boolean actualizarReceta(Receta receta) {
        String sql = "UPDATE recetas SET nombre = ?, id_ingrediente = ? WHERE id_receta = ?";

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, receta.getNombre());
            ps.setInt(2, receta.getIdIngrediente());
            ps.setInt(3, receta.getIdReceta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar receta: " + e.getMessage());
            return false;
        }
    }

    // Eliminar receta
    public boolean eliminarReceta(int idReceta) {
        String sql = "DELETE FROM recetas WHERE id_receta = ?";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idReceta);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar receta: " + e.getMessage());
            return false;
        }
    }
   

}
