package db.dto;

/**
 * User
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class UserDTO {
    // Basic
    String correo, pass;
    // Optional Information
    String nombre, telefono;

    /**
     * Basic constructor to use
     * 
     * @param correo
     * @param pass
     */
    public UserDTO(String correo, String pass) {
        this.correo = correo;
        this.pass = pass;
    }

    /**
     * Constructor to set more information to user
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

    /**
     * When we set pass we need make always the MD5
     * 
     * @param pass
     */
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
