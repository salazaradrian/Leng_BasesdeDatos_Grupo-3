package repositorio;
//import conexion.ConexionOracle;
//import modelo.Empleado;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author Adrian Salazar R
// */
//public class EmpleadoRepositorio {
//    
//    //Metodo para AGREGAR empleado
//    public boolean agregarEmpleado(Empleado empleado) {
//        String sql = "INSERT INTO empleados  "
//                   + "(nombre, primer_apellido, segundo_apellido,  salario, cargo) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, empleado.getNombre());
//            ps.setString(2, empleado.getPrimerApellido());
//            ps.setString(3, empleado.getSegundoApellido());
//            ps.setDouble(4, empleado.getsalario());
//            ps.setString(5, empleado.getcargo());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al agregar empleado: " + e.getMessage());
//            return false;
//        }
//    }
//     //Metodo para LISTAR empleado
//    public List<Empleado> listarEmpleados() {
//        List<Empleado> lista = new ArrayList<>();
//        String sql = "SELECT * FROM empleados";
//
//        try (Connection conn = ConexionOracle.conectar();
//             Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery(sql)) {
//
//            while (rs.next()) {
//                Empleado e = new Empleado();
//                e.setIdEmpleado(rs.getInt("id_empleado"));
//                e.setNombre(rs.getString("nombre"));
//                e.setPrimerApellido(rs.getString("primer_apellido"));
//                e.setSegundoApellido(rs.getString("segundo_apellido"));
//                e.setsalario(rs.getDouble("salario"));
//                e.setcargo(rs.getString("cargo"));
//                lista.add(e);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error al listar empleados: " + e.getMessage());
//        }
//
//        return lista;
//    }
//        //Metodo para ACTUALIZAR empleado
//    public boolean actualizarEmpleado(Empleado empleado) {
//        String sql = "UPDATE empleados SET nombre = ?, primer_apellido = ?, segundo_apellido = ?, salario = ?, cargo = ? WHERE id_empleado = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setString(1, empleado.getNombre());
//            ps.setString(2, empleado.getPrimerApellido());
//            ps.setString(3, empleado.getSegundoApellido());
//            ps.setDouble(4, empleado.getsalario());
//            ps.setString(5, empleado.getcargo());
//            ps.setInt(6, empleado.getIdEmpleado());
//
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al actualizar empleado: " + e.getMessage());
//            return false;
//        }
//    }
//                //Metodo para ELIMINAR empleado
//    public boolean eliminarEmpleado(int idEmpleado) {
//        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
//        try (Connection conn = ConexionOracle.conectar();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, idEmpleado);
//            return ps.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            System.out.println("Error al eliminar empleado: " + e.getMessage());
//            return false;
//        }
//    }
//}
// 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;
import modelo.Empleado;
import conexion.ConexionOracle;

public class EmpleadoRepositorio {

    // Agregar empleado usando paquete
    public boolean agregarEmpleado(Empleado empleado) {
        String sql = "{call agregar_empleado(?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, empleado.getNombre());
            cs.setString(2, empleado.getPrimerApellido());
            cs.setString(3, empleado.getSegundoApellido());
            cs.setDouble(4, empleado.getsalario());
            cs.setString(5, empleado.getcargo());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar empleado: " + e.getMessage());
            return false;
        }
    }

    // Listar empleados usando función del paquete
    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "{? = call listar_empleados()}";

        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            while (rs.next()) {
                Empleado e = new Empleado();
                e.setIdEmpleado(rs.getInt("id_empleado"));
                e.setNombre(rs.getString("nombre"));
                e.setPrimerApellido(rs.getString("primer_apellido"));
                e.setSegundoApellido(rs.getString("segundo_apellido"));
                e.setsalario(rs.getDouble("salario"));
                e.setcargo(rs.getString("cargo"));
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }

        return lista;
    }

    // Actualizar empleado usando paquete
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "{call actualizar_empleado(?, ?, ?, ?, ?, ?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, empleado.getIdEmpleado());
            cs.setString(2, empleado.getNombre());
            cs.setString(3, empleado.getPrimerApellido());
            cs.setString(4, empleado.getSegundoApellido());
            cs.setDouble(5, empleado.getsalario());
            cs.setString(6, empleado.getcargo());

            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    // Eliminar empleado usando paquete
    public boolean eliminarEmpleado(int idEmpleado) {
        String sql = "{call eliminar_empleado(?)}";
        try (Connection conn = ConexionOracle.conectar();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idEmpleado);
            cs.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}
