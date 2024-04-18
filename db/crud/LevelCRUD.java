package db.crud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.dto.DTOUtils;
import db.dto.LevelDTO;

public class LevelCRUD extends CRUD {
    public LevelCRUD() {
        super();
    }

    @Override
    public void insert(Object objUserDTO) {
        LevelDTO level = (LevelDTO) objUserDTO;
        String sentence = "INSERT INTO NIVEL (NOMBRE, FECHA_CREACION, DATALEVEL, CORREO) VALUES (?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, level.getNombre());
            preparedStatement.setString(2, DTOUtils.getActualDay());
            preparedStatement.setString(3, level.getData());
            preparedStatement.setString(4, level.getCorreo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error to insert: " + e);
        }
    }

    @Override
    public Object select(Object input, boolean more) {
        List<LevelDTO> levelDTOs = new ArrayList<>();
        if (input.getClass() == String.class) {
            String email = (String) input;
            String sentence = "SELECT ID, NOMBRE, FECHA_CREACION FROM NIVEL WHERE CORREO = ?";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sentence);
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt(1);
                    String nombre = resultSet.getString(2);
                    String fechaCreacion = resultSet.getString(3);
                    levelDTOs.add(new LevelDTO(id, nombre, fechaCreacion));
                }
            } catch (SQLException e) {
                System.out.println("Error select:\n" + e);
            }
        } else if (input.getClass() == Integer.class) {
            Integer id = (Integer) input;
            String sentence = "SELECT NOMBRE, FECHA_CREACION, DATALEVEL FROM NIVEL WHERE ID = ?";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(sentence);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String nombre = resultSet.getString(1);
                    String fechaCreacion = resultSet.getString(2);
                    String dataLevel = resultSet.getString(3);
                    levelDTOs.add(new LevelDTO(id, nombre, dataLevel, fechaCreacion));
                }
            } catch (SQLException e) {
                System.out.println("Error select:\n" + e);
            }
        }
        return levelDTOs;
    }

    @Override
    public void update(Object newObjLevelDTO) {
        LevelDTO level = (LevelDTO) newObjLevelDTO;
        String sentence = "UPDATE NIVEL SET NOMBRE = ?, DATALEVEL = ? WHERE ID = ?;";
        try {
            // Set basic information
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setString(1, level.getNombre());
            preparedStatement.setString(2, level.getData());
            preparedStatement.setInt(3, level.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error update:\n" + e);
        }
    }

    @Override
    public void delete(Object idInteger) {
        Integer id = (Integer) idInteger;
        String sentence = "DELETE FROM NIVEL WHERE ID = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error to delete:\n " + e);
        }
    }
}
