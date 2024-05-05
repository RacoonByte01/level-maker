package db.crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class CRUD {
    protected Connection connection;
    /* Data of DB */
    private static String NAME_DB, IP_DB, PORT_DB, USER_DB, PASS_DB;

    public CRUD() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://" + IP_DB + ":" + PORT_DB + "/" + NAME_DB,
                    USER_DB,
                    PASS_DB);
        } catch (SQLException e) {
            System.out.println("Error to conect BBDD:\n" + e);
        }
    }

    abstract public boolean insert(Object o);

    public Object select(Object o) {
        return select(o, false);
    }

    abstract public Object select(Object o, boolean more);

    abstract public boolean update(Object o);

    abstract public boolean delete(Object o);

    public static void readData() throws IOException {
        File file = new File("server.dat");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            IP_DB = ignoreMetaData(br.readLine());
            PORT_DB = ignoreMetaData(br.readLine());
            NAME_DB = ignoreMetaData(br.readLine());
            USER_DB = ignoreMetaData(br.readLine());
            PASS_DB = ignoreMetaData(br.readLine());
        }
    }

    private static String ignoreMetaData(String data) throws IOException {
        String resultado;
        resultado = data.substring(data.indexOf("=") + 2);
        resultado = resultado.substring(0, resultado.length() - 1);
        return resultado;
    }
}