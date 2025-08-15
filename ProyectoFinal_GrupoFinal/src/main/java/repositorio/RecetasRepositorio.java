package repositorio;

import conexion.ConexionOracle;
import modelo.Receta;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecetasRepositorio {

    // Obtener recetas
    public List<String> obtenerRecetas() {      //metodo que devuelve una lista de strings
        List<String> recetas = new ArrayList<>(); // se crea una lista vacia - aqui se guardaran los resultados
        String sql = "{? = call pkg_recetas.obtener_recetas()}";  //aqui se define la llamada al P.A . "?" representa el paramtro de salida(el cursor que devuelve el P.A)

        try (Connection conn = ConexionOracle.conectar();       //conexion a la BD
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR);   //registra el 1er parametro "?" como un cursor de salida. INdica que el P.A devovlera un conjunto de resultados
            stmt.execute();                     // ejecuta el P.A

            ResultSet rs = (ResultSet) stmt.getObject(1);     //Recupera el cursor devuelto por el P.A, permite recorrer los resultados fila por fila
            while (rs.next()) {    //itera cada fila
                int id = rs.getInt("id_receta");        //extrae los valores de las columna id_receta
                String nombre = rs.getString("nombre"); //extrae los valores de las columna nombre
                recetas.add(id + " - " + nombre);  //agrega una cadena con el formato "id - nombre" a la lista recetas.
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener recetas: " + e.getMessage());   //captura si hay errores
        }

        return recetas;  //devuelve las listas de recetas
    }

    // Agregar receta
    public boolean agregarReceta(Receta receta) {   //Define un método público que devuelve true o false según si la receta se pudo agregar correctamente.
        String sql = "{call pkg_recetas.agregar_receta(?)}";   //Define la llamada al procedimiento almacenado agregar_receta, que espera un parámetro de entrada (el nombre de la receta).

        try (Connection conn = ConexionOracle.conectar();   //conexion a la BD
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, receta.getNombre()); //Asigna el valor del nombre de la receta al primer parámetro del procedimiento (?).receta.getNombre() obtiene el nombre desde el objeto Receta.
            stmt.execute();  //Ejecuta el procedimiento almacenado en la base de datos.
            return true;  //Si todo salió bien, devuelve true indicando que la receta fue agregada exitosamente.

        } catch (SQLException e) {
            System.out.println("Error al agregar receta: " + e.getMessage());  //Si ocurre un error de SQL, lo imprime en consola y devuelve false.
            return false;
        }
    }

    // Listar recetas
    public List<Receta> listarRecetas() {  //método público que devuelve una lista de objetos Receta.
        List<Receta> lista = new ArrayList<>();  //lista vacía llamada lista donde se guardarán las recetas obtenidas.
        String sql = "{? = call pkg_recetas.listar_recetas()}"; //Define la llamada al P.A listar_recetas(), que devuelve un cursor como parámetro de salida.

        try (Connection conn = ConexionOracle.conectar();  //CONEXION A LA BD
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, OracleTypes.CURSOR); //Registra el primer parámetro (?) como un cursor de salida.
            stmt.execute(); //Ejecuta el procedimiento almacenado.

            ResultSet rs = (ResultSet) stmt.getObject(1); //Recupera el cursor devuelto por el procedimiento como un ResultSet, que permite recorrer los resultados fila por fila.
            while (rs.next()) {  //Itera sobre cada fila del ResultSet.
                Receta r = new Receta();
                r.setIdReceta(rs.getInt("id_receta"));
                r.setNombre(rs.getString("nombre"));
                lista.add(r);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar recetas: " + e.getMessage());
        }

        return lista;  //Devuelve la lista de recetas obtenidas.
    }

    // Actualizar receta
    public boolean actualizarReceta(Receta receta) { //Recibe como parámetro un objeto Receta que contiene el ID y el nuevo nombre.
        String sql = "{call pkg_recetas.actualizar_receta(?, ?)}"; //Define la llamada al procedimiento almacenado actualizar_receta, 

        try (Connection conn = ConexionOracle.conectar();  //conexion a la BD
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, receta.getIdReceta()); //Asigna los valores a los parámetros del procedimiento
            stmt.setString(2, receta.getNombre());
            stmt.execute();
            return true;  //EJECUTA P.A

        } catch (SQLException e) {
            System.out.println("Error al actualizar receta: " + e.getMessage()); //SI OCURRE ERROR, IMPRIME EN CONSOLA
            return false;
        }
    }

    // Eliminar receta
    public boolean eliminarReceta(int idReceta) {
        String sql = "{call pkg_recetas.eliminar_receta(?)}";   //EJECUCION DEL P.A 

        try (Connection conn = ConexionOracle.conectar();  //SE ESTABLECE CONEXION
             CallableStatement stmt = conn.prepareCall(sql)) {  //SE PREPRAPRA LA LLAMDA

            stmt.setInt(1, idReceta); //SE LE PASA EL ID_RECETA COMO PARAMETRO
            stmt.execute(); //SE EJECUTA
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar receta: " + e.getMessage());
            return false;
        }
    }
}

