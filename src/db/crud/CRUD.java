package db.crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * class Crud
 * Esta clase es la encargada de conectar a la base de datos del servidor
 * 
 * @author Javi.LeL
 * @version 1.3
 */
public abstract class CRUD {
    protected Connection connection;
    /* Informacion de la base de datos */
    private static String NAME_DB, IP_DB, PORT_DB, USER_DB, PASS_DB;

    /**
     * Crea la conexion con la base de datos
     */
    public CRUD() {
        try {
            // Intentara establecer conexion con la base de datos
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + IP_DB + ":" + PORT_DB + "/" + NAME_DB,
                    USER_DB,
                    PASS_DB);
        } catch (SQLException e) {
            // En caso de no lograrlo lanzara un excepcion
            System.out.println("Error to conect BBDD:\n" + e);
        }
    }

    /**
     * Este metodo sera el encargado de insertar un objeto en la base de datos
     * 
     * @param o
     * @return
     */
    abstract public boolean insert(Object o);

    /**
     * Este metodo sera el encargado de traer informacion simple (lo esencial) de la
     * base de datos
     * 
     * @param o
     * @return
     */
    public Object select(Object o) {
        return select(o, false);
    }

    /**
     * Este metodo podra traer tanto lo esencial de la base de datos como todo lo
     * que posee en esa fila la base de datos
     * 
     * @param o
     * @param more
     * @return
     */
    abstract public Object select(Object o, boolean more);

    /**
     * Este metodo se encarga de actualizar un objeto alvergado en la base de datos
     * 
     * @param o
     * @return
     */
    abstract public boolean update(Object o);

    /**
     * Este metodo se encarga de borrar un registro de la base de datos
     * 
     * @param o
     * @return
     */
    abstract public boolean delete(Object o);

    /**
     * Este metodo se encarga de leer la informacion del fichero de configuracion de
     * la base de datos para su posetrior uso de estos para crear la conexion con la
     * respectiba base de datos
     * 
     * @throws IOException
     */
    public static void readData() throws IOException {
        File file = new File("db/db.cfg");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            /*
             * Lee todas las filas ignorando la metadata y le asigna la infomacion encotrada
             * a sus respetiva varible
             */
            IP_DB = ignoreMetaData(br.readLine());
            PORT_DB = ignoreMetaData(br.readLine());
            NAME_DB = ignoreMetaData(br.readLine());
            USER_DB = ignoreMetaData(br.readLine());
            PASS_DB = ignoreMetaData(br.readLine());
        }
    }

    /**
     * Este metodo se encarga de ignorar la metadata del fichero de configuracion de
     * la base de datos
     * 
     * @param data
     * @return
     * @throws IOException
     */
    public static String ignoreMetaData(String data) throws IOException {
        String resultado;
        resultado = data.substring(data.indexOf("=") + 2);
        resultado = resultado.substring(0, resultado.length() - 1);
        return resultado;
    }

    /**
     * Metodo encargado de generar la metadata del fichero de texto
     * 
     * @param metaname
     * @param data
     * @return
     */
    public static String generateMetadata(String metaname, String data) {
        return metaname + "=\"" + data.trim() + "\"";
    }
}