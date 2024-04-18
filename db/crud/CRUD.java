package db.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class CRUD {
    protected Connection connection;
    /* Data of DB */
    private final String NAME_DB = "TFG_JAVIER";
    private final String IP_DB = "javilel.ddns.net";
    private final String PORT_DB = "2342";
    private final String USER_DB = "tfg";
    private final String PASS_DB = "pass";

    public CRUD() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + IP_DB + ":" + PORT_DB + "/" + NAME_DB,
                    USER_DB,
                    PASS_DB);
        } catch (SQLException e) {
            System.out.println("Error to conect BBDD:\n" + e);
        }
    }

    abstract public void insert(Object o);

    public Object select(Object o) {
        return select(o, false);
    }

    abstract public Object select(Object o, boolean more);

    abstract public void update(Object o);

    abstract public void delete(Object o);
}
