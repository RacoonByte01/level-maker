package db.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.dto.DTOUtils;
import db.dto.UserDTO;

/**
 * class UserCRUD
 * Extiende de crud para usar los datos recolectado por el archivo de
 * configuracion
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class UserCRUD extends CRUD {
    /**
     * Constructor para crear el constructor que usara
     */
    public UserCRUD() {
        super();
    }

    /**
     * Este metodo se encarga de insertar un objeto de tipo UserDTO en la base de
     * datos
     * 
     * @param objUserDTO
     * @return true si la lo inserto de forma correcta o false si se produjo algun
     *         tipo de error
     */
    @Override
    public boolean insert(Object objUserDTO) {
        // Se castea la informacion recivida a la que se va ha utilizar
        UserDTO user = (UserDTO) objUserDTO;
        // Se crea una sentencia SQL
        String sentence = "INSERT INTO USUARIOS (CORREO, PASS) VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            // Se pasa la informacion necesaria para construir la sentencia
            preparedStatement.setString(1, user.getCorreo());
            preparedStatement.setString(2, DTOUtils.getMD5(user.getPass()));
            // Y se ejecuta
            preparedStatement.executeUpdate();
            /*
             * Se prepara otra sentencia y que el usuario necesita conectarse a dos tablas
             * de la base de datos
             */
            sentence = "INSERT INTO INFORMACION_USUARIOS (CORREO, NOMBRE, TELEFONO, FECHA_CREACION) VALUES (?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sentence);
            // Se la pasa la informacion necesaria
            preparedStatement.setString(1, user.getCorreo());
            preparedStatement.setString(2, user.getNombre());
            preparedStatement.setString(3, user.getTelefono());
            preparedStatement.setString(4, DTOUtils.getActualDay());
            // Y se ejecuta la sentencia de la base de datos
            preparedStatement.executeUpdate();
            // Ya que todo ha ido bien este devolvera un true
            return true;
        } catch (SQLException e) {
            // En caso contrario esta devolvera un false
            return false;
        }
    }

    /**
     * Este metodo se encarga de devolver la informacion de la base de datos al
     * programa en este caso si se quiere solo la informacion del login (corre y
     * contrasena) este deve de ser false en caso de quere toda la informacion del
     * usuario se tendra que pasar un true
     * 
     * @param correoStr
     * @param more
     * @return
     */
    @Override
    public Object select(Object correoStr, boolean more) {
        // Se crea el resultado y se establece que es nulo en caso de que se produzca un
        // error inesperados
        UserDTO result = null;
        // Se castea el correoStr a su correspondiente objeto
        String email = (String) correoStr;
        // Si se requiere de mas informacion este
        if (more) {
            // Creara una se sentencia de base de datos
            String sentence = "SELECT u.PASS, iu.NOMBRE, iu.TELEFONO, iu.FECHA_CREACION FROM USUARIOS u, INFORMACION_USUARIOS iu WHERE u.CORREO = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sentence);
                // Se le pasaran los datos correspondientes a la sentencia SQL
                preparedStatement.setString(1, email);
                // Se obtendran todos los datos recolectados
                ResultSet resultSet = preparedStatement.executeQuery();
                // Y se leera uno por uno hasta que no quede ningun dato mas
                while (resultSet.next()) {
                    // Se guardan todos los datos en sus correspondientes variables
                    String pass = resultSet.getString(1);
                    String nombre = resultSet.getString(2);
                    String telefono = resultSet.getString(3);
                    // Se guardan los datos en un objeto de tipo UserDTO
                    result = new UserDTO(email, pass, nombre, telefono);
                }
            } catch (SQLException e) {
                // En caso de error este devolvera un aviso por consola
                System.out.println("Error to select:\n " + e);
            }
        } else {
            // Si no se requiere de mas se creara una sentencia SQL
            String sentence = "SELECT PASS FROM USUARIOS WHERE CORREO = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sentence);
                // Se le pasara la informacion correspondiente a la sentencia SQL creada
                preparedStatement.setString(1, email);
                // Se guardara el resultado de la base de datos
                ResultSet resultSet = preparedStatement.executeQuery();
                // Se leeran todos los resultados
                while (resultSet.next()) {
                    // Se guardaran los resultados en sus respectivas variables
                    String pass = resultSet.getString(1);
                    // Para poseterioirmente crear un resultado de tipo UserDTO
                    result = new UserDTO(email, pass);
                }
            } catch (SQLException e) {
                // Si se produce cualquier tipo de error este se imprimira por pantalla
                System.out.println("Error to select:\n " + e);
            }
        }
        // Y se devolvera el resultado esperado
        return result;
    }

    /**
     * Este metodo se encarga actualizar un usuario especifico de la base de datos
     * 
     * @param emailStr
     * @return true si se ha actualizado correctamente false si ha habido un
     *         problema
     */
    @Override
    public boolean update(Object newObjUserDTO) {
        // Se castea el parametro a su tipo correspondiente
        UserDTO user = (UserDTO) newObjUserDTO;
        // Se creara la sentencia SQL
        String sentence = "UPDATE USUARIOS SET PASS = ? WHERE CORREO = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            // Se le asignara la infromacion necesaria
            preparedStatement.setString(1, DTOUtils.getMD5(user.getPass()));
            preparedStatement.setString(2, user.getCorreo());
            // Se ejecutara la sentencia SQL
            preparedStatement.executeUpdate();
            // Se creara otra sentencia
            sentence = "UPDATE INFORMACION_USUARIOS SET NOMBRE = ?, TELEFONO = ? WHERE CORREO = ?;";
            preparedStatement = connection.prepareStatement(sentence);
            // Se le pasar la informacion necesario
            preparedStatement.setString(1, user.getNombre());
            preparedStatement.setString(2, user.getTelefono());
            preparedStatement.setString(3, user.getCorreo());
            // Y se ejecutara la sentencia SQL
            preparedStatement.executeUpdate();
            // Ya que todo se a producido como lo esperado se devuelve un true
            return true;
        } catch (SQLException e) {
            // En caso contrario este devolvera un false
            return false;
        }
    }

    /**
     * Este metodo se encargara de borrar un usuario de la base de datos
     * 
     * @param idInteger
     * @return true si se ha actualizado correctamente false si ha habido un
     *         problema
     */
    @Override
    public boolean delete(Object emailStr) {
        // Se castea el correo al tipo correspondiente
        String email = (String) emailStr;
        // Se crea la sentencia SQL
        String sentence = "DELETE FROM USUARIOS WHERE CORREO = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            // Se pasan los parametros necesarios
            preparedStatement.setString(1, email);
            // Se ejecuta la sentencia SQL
            preparedStatement.executeUpdate();
            // Ya que todo fue bien esta devolvera un true
            return true;
        } catch (SQLException e) {
            // En caso contrario devolvera un false
            return false;
        }
    }
}
