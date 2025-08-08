//package repositorio;
//
//import conexion.ConexionOracle;
//import modelo.Cliente;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ClienteRepositorio {
//
//    // Crear cliente
//    public boolean agregarCliente(Cliente cliente) {
//        String sql = "INSERT INTO clientes "
//                   + "(nombre, primer_apellido, segundo_apellido, telefono, email, direccion) "
//                   + "VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, cliente.getNombre());
//            ps.setString(2, cliente.getPrimerApellido());
//            ps.setString(3, cliente.getSegundoApellido());
//            ps.setString(4, cliente.getTelefono());
//            ps.setString(5, cliente.getEmail());
//            ps.setString(6, cliente.getDireccion());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al agregar cliente: " + e.getMessage());
//            return false;
//        }
//    }
//
//    // Leer todos los clientes
//    public List<Cliente> listarClientes() {
//        List<Cliente> lista = new ArrayList<>();
//        String sql = "SELECT id_cliente, nombre, primer_apellido, segundo_apellido, telefono, email, direccion "
//                   + "FROM clientes";
//
//        try (Connection conn = ConexionOracle.conectar();
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery(sql)) {
//
//            while (rs.next()) {
//                Cliente c = new Cliente();
//                c.setIdCliente(rs.getInt("id_cliente"));
//                c.setNombre(rs.getString("nombre"));
//                c.setPrimerApellido(rs.getString("primer_apellido"));
//                c.setSegundoApellido(rs.getString("segundo_apellido"));
//                c.setTelefono(rs.getString("telefono"));
//                c.setEmail(rs.getString("email"));
//                c.setDireccion(rs.getString("direccion"));
//                lista.add(c);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error al listar clientes: " + e.getMessage());
//        }
//
//        return lista;
//    }
//
//    //Actualizar clientes
//  public boolean actualizarCliente(Cliente cliente) {
//    String sql = "UPDATE clientes SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, "
//               + "telefono = ?, email = ?, direccion = ? "
//               + "WHERE id_cliente = ?";
//
//    try (Connection conn = ConexionOracle.conectar();
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//
//        ps.setString(1, cliente.getNombre());
//        ps.setString(2, cliente.getPrimerApellido());
//        ps.setString(3, cliente.getSegundoApellido());
//        ps.setString(4, cliente.getTelefono());
//        ps.setString(5, cliente.getEmail());
//        ps.setString(6, cliente.getDireccion());
//        ps.setInt(7, cliente.getIdCliente()); //
//
//        return ps.executeUpdate() > 0;
//
//    } catch (SQLException e) {
//        System.out.println("Error al actualizar cliente: " + e.getMessage());
//        return false;
//    }
//}
//
//    // Eliminar cliente
//    public boolean eliminarCliente(int idCliente) {
//        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, idCliente);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al eliminar cliente: " + e.getMessage());
//            return false;
//        }
//    }
//}
package repositorio;
import conexion.ConexionOracle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import modelo.Cliente;

public class ClienteRepositorio {

    
    public boolean agregarCliente(Cliente cliente) {
        String sql = "{call agregar_cliente(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, cliente.getNombre());
            cs.setString(2, cliente.getPrimerApellido());
            cs.setString(3, cliente.getSegundoApellido());
            cs.setString(4, cliente.getTelefono());
            cs.setString(5, cliente.getEmail());
            cs.setString(6, cliente.getDireccion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

   
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "{? = call listar_clientes()}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombre(rs.getString("nombre"));
                c.setPrimerApellido(rs.getString("primer_apellido"));
                c.setSegundoApellido(rs.getString("segundo_apellido"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setDireccion(rs.getString("direccion"));
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }

   
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "{call actualizar_cliente(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, cliente.getIdCliente());
            cs.setString(2, cliente.getNombre());
            cs.setString(3, cliente.getPrimerApellido());
            cs.setString(4, cliente.getSegundoApellido());
            cs.setString(5, cliente.getTelefono());
            cs.setString(6, cliente.getEmail());
            cs.setString(7, cliente.getDireccion());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

   
    public boolean eliminarCliente(int idCliente) {
        String sql = "{call eliminar_cliente(?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCliente);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
