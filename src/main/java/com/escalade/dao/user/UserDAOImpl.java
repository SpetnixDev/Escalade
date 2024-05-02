package com.escalade.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.escalade.dao.DAOException;
import com.escalade.dao.DAOFactory;
import com.escalade.model.User;
import com.escalade.util.DBUtils;

public class UserDAOImpl implements UserDAO {
	private DAOFactory daoFactory;
	
	public UserDAOImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public User registerUser(String email, String firstName, String lastName, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try {
			connection = daoFactory.getConnection();
			
			String query = "INSERT INTO users (first_name, last_name, password, email, member) VALUES (?, ?, ?, ?, ?)";
			
			preparedStatement = connection.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, email);
			preparedStatement.setBoolean(5, false);
			preparedStatement.executeUpdate();
			
			connection.commit();
			
			resultSet = preparedStatement.getGeneratedKeys();
			
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				
				user = new User(id, firstName, lastName, email, false);
			} else {
				throw new DAOException("Un problème est survenu lors de la création de l'utilisateur");
			}
			
			return user;
		} catch (SQLException e) {
			try {
                if (connection != null) connection.rollback();
            } catch (SQLException e2) {}
			
            throw new DAOException("Impossible de communiquer avec la base de données");
		} finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

	@Override
	public User authenticateUser(String email, String password) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
        	connection = daoFactory.getConnection();
        	
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
            	
                user = new User(id, firstName, lastName, email, member);
            }

            return user;
        } catch (SQLException e) {
        	throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}

	/*
	@Override
	public User getUserById(int id) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
        	connection = daoFactory.getConnection();
        	
            String query = "SELECT * FROM users WHERE id = ?";
            
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                //user = new User(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getBoolean("member"));
            }
        } catch (SQLException e) {
        	throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }

        return user;
	}*/

	@Override
	public boolean isEmailAlreadyUsed(String email) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
        	connection = daoFactory.getConnection();
        	
        	String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        	
        	preparedStatement = connection.prepareStatement(query);
        	preparedStatement.setString(1, email);
        	resultSet = preparedStatement.executeQuery();
        	
        	if (resultSet.next()) return resultSet.getInt(1) > 0;
            
            return false;
        } catch (SQLException e) {
        	throw new DAOException("Impossible de communiquer avec la base de données");
        } finally {
            DBUtils.closeQuietly(connection, resultSet, preparedStatement);
        }
	}
}
