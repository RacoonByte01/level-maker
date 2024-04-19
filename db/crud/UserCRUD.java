package db.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.dto.DTOUtils;
import db.dto.UserDTO;

/**
 * UserCRUD
 * 
 * @author JaviLeL
 * @version 1.0
 */
public class UserCRUD extends CRUD {
    public UserCRUD() {
        super();
    }

    @Override
    public boolean insert(Object objUserDTO) {
        UserDTO user = (UserDTO) objUserDTO;
        String sentence = "INSERT INTO USUARIOS (CORREO, PASS) VALUES (?, ?);";
        try {
            // Set basic information
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, user.getCorreo());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.executeUpdate();

            // Set the complex information
            sentence = "INSERT INTO INFORMACION_USUARIOS (CORREO, NOMBRE, TELEFONO, FECHA_CREACION) VALUES (?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, user.getCorreo());
            preparedStatement.setString(2, user.getNombre());
            preparedStatement.setString(3, user.getTelefono());
            preparedStatement.setString(4, DTOUtils.getActualDay());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // System.out.println("Error to insert: " + e);
            return false;
        }
    }

    @Override
    public Object select(Object correoStr, boolean more) {
        UserDTO result = null;
        String email = (String) correoStr;
        if (more) {
            String sentence = "SELECT u.PASS, iu.NOMBRE, iu.TELEFONO, iu.FECHA_CREACION FROM USUARIOS u, INFORMACION_USUARIOS iu WHERE u.CORREO = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sentence);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String pass = resultSet.getString(1);
                    String nombre = resultSet.getString(2);
                    String telefono = resultSet.getString(3);
                    result = new UserDTO(email, pass, nombre, telefono);
                }
            } catch (SQLException e) {
                System.out.println("Error to select:\n " + e);
            }
        } else {
            String sentence = "SELECT PASS FROM USUARIOS WHERE CORREO = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sentence);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String pass = resultSet.getString(1);
                    result = new UserDTO(email, pass);
                }
            } catch (SQLException e) {
                System.out.println("Error to select:\n " + e);
            }
        }
        return result;
    }

    @Override
    public boolean update(Object newObjUserDTO) {
        UserDTO user = (UserDTO) newObjUserDTO;
        String sentence = "UPDATE USUARIOS SET PASS = ? WHERE CORREO = ?;";
        try {
            // Set basic information
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, user.getPass());
            preparedStatement.setString(2, user.getCorreo());
            preparedStatement.executeUpdate();

            // Set the complex information
            sentence = "UPDATE INFORMACION_USUARIOS SET NOMBRE = ?, TELEFONO = ? WHERE CORREO = ?;";
            preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, user.getNombre());
            preparedStatement.setString(2, user.getTelefono());
            preparedStatement.setString(3, user.getCorreo());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // System.out.println("Error to update:\n " + e);
            return false;
        }
    }

    @Override
    public boolean delete(Object emailStr) {
        String email = (String) emailStr;
        String sentence = "DELETE FROM USUARIOS WHERE CORREO = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            // System.out.println("Error to delete:\n " + e);
            return false;
        }
    }
}
