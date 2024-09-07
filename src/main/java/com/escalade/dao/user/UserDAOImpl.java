package com.escalade.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.escalade.dao.DAOException;
import com.escalade.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User registerUser(String email, String firstName, String lastName, String password) throws DAOException {
        final String query = "INSERT INTO users (first_name, last_name, password, email, member) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(query, new String[] {"id"});
                
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, password);
                ps.setString(4, email);
                ps.setBoolean(5, false);
                
                return ps;
            }, keyHolder);

            int id = keyHolder.getKey().intValue();
            
            return new User(id, firstName, lastName, email, false);
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public User authenticateUser(String email, String password) throws DAOException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        
        try {
            List<User> users = jdbcTemplate.query(query, (PreparedStatementSetter) ps -> {
                ps.setString(1, email);
                ps.setString(2, password);
            }, new UserRowMapper());

            if (users.size() == 1) {
                return users.get(0);
            } else {
            	return null;
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public User getUserById(int id) throws DAOException {
        String query = "SELECT * FROM users WHERE id = ?";
        
        try {
            List<User> users = jdbcTemplate.query(query, (PreparedStatementSetter) ps -> {
                ps.setInt(1, id);
            }, new UserRowMapper());

            if (users.size() == 1) {
                return users.get(0);
            } else {
            	throw new DAOException("Un problème est survenu");
            }
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    @Override
    public boolean isEmailAlreadyUsed(String email) throws DAOException {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        try {
            Integer count = jdbcTemplate.queryForObject(query, Integer.class, email);
            
            return count != null && count > 0;
        } catch (Exception e) {
            throw new DAOException("Impossible de communiquer avec la base de données");
        }
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            boolean member = rs.getBoolean("member");
            
            return new User(id, firstName, lastName, email, member);
        }
    }
}
