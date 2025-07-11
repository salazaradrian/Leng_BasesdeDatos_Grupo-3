
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

    // Crear receta
    public boolean agregarReceta(Receta receta) {
        String sql = "INSERT INTO recetas "
                   + "(nombre) "
                   + "VALUES (?)";
        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, receta.getNombre());
            

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar receta: " + e.getMessage());
            return false;
        }
    }

    // Leer todos los clientes
    public List<Receta> listarRecetas() {
        List<Receta> lista = new ArrayList<>();
        String sql = "SELECT nombre"
                   + "FROM recetas";

        try (Connection conn = ConexionOracle.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Receta c = new Receta();
                c.setIdReceta(rs.getInt("id_receta"));
                c.setNombre(rs.getString("nombre"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar recetas: " + e.getMessage());
        }

        return lista;
    }

    //Actualizar recetas
  public boolean actualizarReceta(Receta receta) {
    String sql = "UPDATE recetas SET nombre = ?,"
             + "WHERE id_cliente = ?";

    try (Connection conn = ConexionOracle.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, receta.getNombre());
        ps.setInt(2, receta.getIdReceta()); //

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

