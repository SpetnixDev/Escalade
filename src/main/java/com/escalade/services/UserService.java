package com.escalade.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.escalade.config.DBConnector;
import com.escalade.model.Topo;
import com.escalade.model.User;

public class UserService {	
	private TopoService topoService;
	
	public UserService() {
		this.topoService = new TopoService();
	}
	
	public User registerUser(String firstName, String lastName, String email, String password) {
		Connection connection = DBConnector.getDBConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			String query = "INSERT INTO users (first_name, last_name, password, email, member) VALUES (?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, email);
			preparedStatement.setBoolean(5, false);
			
			int affectedRows = preparedStatement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Error creating a new user.");
			}
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				user = new User(id, firstName, lastName, email, false);
			} else {
				throw new SQLException("Creating user failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		
		return user;
	}
	
	public User authenticateUser(String email, String password) {
        Connection connection = DBConnector.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	int id = resultSet.getInt("id");
            	String firstName = resultSet.getString("first_name");
            	String lastName = resultSet.getString("last_name");
            	boolean member = resultSet.getBoolean("member");
                
                List<Topo> topos = topoService.requestToposFromUser(id);
            	
                user = new User(id, firstName, lastName, email, member);
                user.setTopos(topos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
	}
	
	public boolean isEmailAlreadyUsed(String email) {
        Connection connection = DBConnector.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
        	String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        	preparedStatement = connection.prepareStatement(query);
        	preparedStatement.setString(1, email);
        	resultSet = preparedStatement.executeQuery();
        	
        	if (resultSet.next()) {
        		return resultSet.getInt(1) > 0;
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
	}
	
	public User getUserById(int userId) {
        Connection connection = DBConnector.getDBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            String query = "SELECT * FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                //user = new User(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getBoolean("member"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}
