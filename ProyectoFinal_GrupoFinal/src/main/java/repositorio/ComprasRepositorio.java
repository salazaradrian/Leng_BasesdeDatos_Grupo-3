package repositorio;

import conexion.ConexionOracle;
import modelo.Compras;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class ComprasRepositorio {

    public boolean agregarCompra(Compras compra) {
      //  String sql = "{call agregar_compra(?,?,?)}";
        String sql = "{call pkg_compras.agregar_compra(?,?,?)}";
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


    // Listar compras
    public List<Compras> listarCompras() {
        List<Compras> lista = new ArrayList<>();
        //String sql = "{? = call listar_compras()}";
        String sql = "{? = call pkg_compras.listar_compras()}";
        
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


    // Eliminar compra
    public boolean eliminarCompra(int idCompra) {
        //String sql = "{call eliminar_compra(?)}";
        String sql = "{call pkg_compras.eliminar_compra(?)}";
        
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

