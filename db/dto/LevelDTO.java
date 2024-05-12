package db.dto;

public class LevelDTO {
    // Basic Infromation
    Integer id;
    String nombre, data, correo, fechaCreacion;

    // More information

    /**
     * Create new LevelDTO for Database
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