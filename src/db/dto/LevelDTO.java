package db.dto;

/**
 * class LevelDTO
 * Esta clase se encarga de manejar los datos de un nivel de forma mucho mas
 * sencilla
 * 
 * @author Javi.LeL
 * @version 1.0
 */
public class LevelDTO {
    // Informacion que manejara el objeto
    Integer id;
    String nombre, data, correo, fechaCreacion;

    // More information

    /**
     * Crea un dto
     * 
     * @param nombre
     * @param data
     * @param correo
     */
    public LevelDTO(String nombre, String data, String correo) {
        this.nombre = nombre;
        this.data = data;
        this.correo = correo;
    }

    /**
     * Crea un dto
     * 
     * @param id
     * @param nombre
     * @param data
     * @param fechaCreacion
     */
    public LevelDTO(Integer id, String nombre, String data, String fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.data = data;
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Crea un dto
     * 
     * @param id
     * @param nombre
     * @param fechaCreacion
     */
    public LevelDTO(Integer id, String nombre, String fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public String toString() {
        return "LevelDTO [id=" + id + ", nombre=" + nombre + ", data=" + data + ", correo=" + correo
                + ", fechaCreacion=" + fechaCreacion + "]\n";
    }
}