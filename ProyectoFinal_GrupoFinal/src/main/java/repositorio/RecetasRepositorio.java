

package repositorio;

import conexion.ConexionOracle;
import modelo.Receta;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecetasRepositorio {

  
    public List<String> obtenerRecetas() {
        List<String> recetas = new ArrayList<>();
        String sql = "{? = call obtener_recetas()}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
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

    // Agregar receta
    public boolean agregarReceta(Receta receta) {
        String sql = "{call agregar_receta(?, ?)}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, receta.getNombre());
            stmt.setInt(2, receta.getIdIngrediente());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar receta: " + e.getMessage());
            return false;
        }
    }

    // Listar recetas completas
    public List<Receta> listarRecetas() {
        List<Receta> lista = new ArrayList<>();
        String sql = "{? = call listar_recetas()}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();

            ResultSet rs = (ResultSet) stmt.getObject(1);
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
        String sql = "{call actualizar_receta(?, ?, ?)}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, receta.getIdReceta());
            stmt.setString(2, receta.getNombre());
            stmt.setInt(3, receta.getIdIngrediente());
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar receta: " + e.getMessage());
            return false;
        }
    }

    // Eliminar receta
    public boolean eliminarReceta(int idReceta) {
        String sql = "{call eliminar_receta(?)}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idReceta);
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar receta: " + e.getMessage());
            return false;
        }
    }
}
