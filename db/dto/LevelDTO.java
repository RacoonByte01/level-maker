package db.dto;

public class LevelDTO {
    // Basic Infromation
    Integer id;
    String nombre, data, correo;

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
     * For Updates or Delete LevelDTO for Database
     * 
     * @param id
     * @param nombre
     * @param data
     */
    public LevelDTO(Integer id, String nombre, String data) {
        this.id = id;
        this.nombre = nombre;
        this.data = data;
    }
}