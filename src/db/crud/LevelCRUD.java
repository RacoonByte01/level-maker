package db.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.dto.DTOUtils;
import db.dto.LevelDTO;

/**
 * class LevelCrud
 * Extiende de crud para usar los datos recolectado por el archivo de
 * configuracion
 * 
 * @author Javi.LeL
 * @version 1.2
 */
public class LevelCRUD extends CRUD {
    /**
     * Constructor para crear el constructor que usara
     */
    public LevelCRUD() {
        super();
    }

    /**
     * Este metodo se encarga de insertar un objeto de tipo LevelDTO en la base de
     * datos
     * 
     * @param objLevelDTO
     * @return true si la lo inserto de forma correcta o false si se produjo algun
     *         tipo de error
     */
    @Override
    public boolean insert(Object objLevelDTO) {
        // Se castea el objeto al objeto correspondiente
        LevelDTO level = (LevelDTO) objLevelDTO;
        // Se crea una sentencia de la base de datos preparad
        String sentence = "INSERT INTO NIVEL (NOMBRE, FECHA_CREACION, DATALEVEL, CORREO) VALUES (?, ?, ?, ?);";
        try {
            // Se le pasan los parametros a la sentencia preparada
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, level.getNombre());
            preparedStatement.setString(2, DTOUtils.getActualDay());
            preparedStatement.setString(3, level.getData());
            preparedStatement.setString(4, level.getCorreo());
            // Y se ejecuta la sentencia de la base de datos
            preparedStatement.executeUpdate();
            // Al haberse logrado correctamente este devuelve un true
            return true;
        } catch (SQLException e) {
            // Si se preovocase cualquier tipo de error este lanzaria un false
            return false;
        }
    }

    /**
     * Este metodo nos permite traer de la base de datos de todos los niveles
     * dependiendo del tipo de clase que sea el input
     * 
     * @param input
     * @param more  en este caso se ignora ya que lo hace de forma inteligente
     * @return devuelve una lista con todos los
     */
    @Override
    public Object select(Object input, boolean more) {
        // Se crea la lista que alvergara todos los niveles
        List<LevelDTO> levelDTOs = new ArrayList<>();
        // Si la entrada de informacion es de tipo String significara que busca todos
        // niveles que posee un usuario
        if (input.getClass() == String.class) {
            // Se caste la informacion a su tipo correspondiente
            String email = (String) input;
            // Se crea la sentencia preparada de la base de datos
            String sentence = "SELECT ID, NOMBRE, FECHA_CREACION FROM NIVEL WHERE CORREO = ?";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sentence);
                // Se le pasan los datos correspondientes
                preparedStatement.setString(1, email);
                // Y se guardan los resultados de la sentencia de la base de datos
                ResultSet resultSet = preparedStatement.executeQuery();
                // Se recorren los resultados de uno en uno hasta que no queden
                while (resultSet.next()) {
                    // Se guardan lo resultados en sus variables especificas
                    Integer id = resultSet.getInt(1);
                    String nombre = resultSet.getString(2);
                    String fechaCreacion = resultSet.getString(3);
                    // Se crea un objeto de tipo LevelDTO para posteriormente guardarlo en la lista
                    // con todos los niveles
                    levelDTOs.add(new LevelDTO(id, nombre, fechaCreacion));
                }
            } catch (SQLException e) {
                // Si se produce cualquier tipo de error se devulve un fallo por consola
                System.out.println("Error select:\n" + e);
            }
            // En caso de que el tipo de input es de tipo integer significara que esta
            // accediendo a un nivel y por tanto es necesario toda la informacion del nivel
        } else if (input.getClass() == Integer.class) {
            // Se castea al tipo correspondiente
            Integer id = (Integer) input;
            // Se crea la sentencia preparada de la base de datos
            String sentence = "SELECT NOMBRE, FECHA_CREACION, DATALEVEL FROM NIVEL WHERE ID = ?";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sentence);
                // Se le pasan la id a la sentencia
                preparedStatement.setInt(1, id);
                // Se obtienen los resultados obtenidos
                ResultSet resultSet = preparedStatement.executeQuery();
                // Se leen los resultados de uno en uno hasta que ya no queden mas
                while (resultSet.next()) {
                    // Los resultado se obtienen y se guardan en sus respectivas variables
                    String nombre = resultSet.getString(1);
                    String fechaCreacion = resultSet.getString(2);
                    String dataLevel = resultSet.getString(3);
                    // Posteriormente se crea una lista con los objetos creados en este caso siempre
                    // sera una lista de un solo objeto de tipo LevelDTO
                    levelDTOs.add(new LevelDTO(id, nombre, dataLevel, fechaCreacion));
                }
            } catch (SQLException e) {
                // En caso de cualquier error inesperado se devolvera el fallo por consola
                System.out.println("Error select:\n" + e);
            }
        }
        // Se devuel la lista generada
        return levelDTOs;
    }

    /**
     * Este metodo se encarga actualizar un nivel especifico de la base de datos
     * 
     * @param newObjLevelDTO
     * @return true si se ha actualizado correctamente false si ha habido un
     *         problema
     */
    @Override
    public boolean update(Object newObjLevelDTO) {
        // Se castea el objeto al objetos esperado
        LevelDTO level = (LevelDTO) newObjLevelDTO;
        // Se crea la sentencia QSL
        String sentence = "UPDATE NIVEL SET NOMBRE = ?, DATALEVEL = ? WHERE ID = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            // Se le pasa la informacion necesaria a la base de datos
            preparedStatement.setString(1, level.getNombre());
            preparedStatement.setString(2, level.getData());
            preparedStatement.setInt(3, level.getId());
            // Se ejecutara la sentencia SQL
            preparedStatement.executeUpdate();
            // Ya que se ha producido sin problemas esta devolvera un true
            return true;
        } catch (SQLException e) {
            // En caso contrario esta devolvera un false
            return false;
        }
    }

    /**
     * Este metodo se encargara de borrar un nivel de la base de datos
     * 
     * @param idInteger
     * @return true si se ha actualizado correctamente false si ha habido un
     *         problema
     */
    @Override
    public boolean delete(Object idInteger) {
        // Se castea el objeto recivido al tipo correspondiente, ya que los niveles
        // funcionan por id este es necesario que sea un Integer
        Integer id = (Integer) idInteger;
        // Se crea la sentencia SQL
        String sentence = "DELETE FROM NIVEL WHERE ID = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            // Se le pasa la infomacion a la sentecia
            preparedStatement.setInt(1, id);
            // Se ejeecuta la sentencia
            preparedStatement.executeUpdate();
            // Al no producirse problemas esta devolvlera un true
            return true;
        } catch (SQLException e) {
            // En cualquier otro caso este devolvera un false
            return false;
        }
    }
}
