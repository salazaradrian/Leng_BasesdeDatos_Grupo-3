
package repositorio;

import conexion.ConexionOracle;
import modelo.Producto;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorio {

//    public boolean agregarProducto(Producto p) {
//        String sql = "{call agregar_producto(?, ?, ?, ?, ?)}";
//        try (Connection conn = ConexionOracle.conectar();
//             CallableStatement stmt = conn.prepareCall(sql)) {
//            stmt.setString(1, p.getNombre());
//            stmt.setString(2, p.getTipo());
//            stmt.setDouble(3, p.getPrecio());
//            stmt.setString(4, p.getDescripcion());
//            stmt.setInt(5, p.getIdReceta());
//            stmt.execute();
//            return true;
//        } catch (SQLException e) {
//            System.out.println("Error al agregar producto: " + e.getMessage());
//            return false;
//        }
//    }
    
    public boolean agregarProducto(Producto p) {
    String sql = "{call agregar_producto(?, ?, ?, ?, ?)}";
    try (Connection conn = ConexionOracle.conectar();
         CallableStatement stmt = conn.prepareCall(sql)) {
        stmt.setString(1, p.getNombre());
        stmt.setString(2, p.getTipo());
        stmt.setDouble(3, p.getPrecio());
        stmt.setString(4, p.getDescripcion());
        stmt.setObject(5, p.getIdReceta(), java.sql.Types.INTEGER); // Manejo de null
        stmt.execute();
        return true;
    } catch (SQLException e) {
        System.out.println("Error al agregar producto: " + e.getMessage());
        return false;
    }
}


    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "{? = call listar_productos()}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.registerOutParameter(1, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet rs = (ResultSet) stmt.getObject(1);
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getString("descripcion"),
                    rs.getInt("id_receta")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizarProducto(Producto p) {
        String sql = "{call actualizar_producto(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, p.getIdProducto());
            stmt.setString(2, p.getNombre());
            stmt.setString(3, p.getTipo());
            stmt.setDouble(4, p.getPrecio());
            stmt.setString(5, p.getDescripcion());
            stmt.setInt(6, p.getIdReceta());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarProducto(int id) {
        String sql = "{call eliminar_producto(?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}
