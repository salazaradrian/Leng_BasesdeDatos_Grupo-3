package repositorio;

import conexion.ConexionOracle;
import modelo.Ingrediente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import oracle.jdbc.OracleTypes;


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

