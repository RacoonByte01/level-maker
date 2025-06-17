package db.dto;

/**
 * class UserDTO
 * Esta clase se encarga de manejar los datos de usuario de foma mas sencilla
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class UserDTO {
    // Basic
    String correo, pass;
    // Optional Information
    String nombre, telefono;

    /**
     * Constructor basico
     * 
     * @param correo
     * @param pass
     */
    public UserDTO(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }

    /**
     * Constructor para poner informacion al usuario
     * 
     * @param correo
     * @param pass
     * @param nombre
     * @param telefono
     */
    public UserDTO(String correo, String pass, String nombre, String telefono) {
        this.correo = correo;
        this.pass = pass;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public void setPass(String pass) {
        this.pass = DTOUtils.getMD5(pass);
    }

    public String getCorreo() {
        return correo;
    }

    public String getPass() {
        return pass;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "UserDTO [correo=" + correo + ", pass=" + pass + ", nombre=" + nombre + ", telefono=" + telefono + "]";
    }
}
