package ru.dpoz.socnetw.repository;

import org.hibernate.type.PostgresUUIDType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dpoz.socnetw.SocnetwApplication;
import ru.dpoz.socnetw.config.WebSecurityConfig;
import ru.dpoz.socnetw.model.UserEntity;
import ru.dpoz.socnetw.model.UserSecretEntity;
import ru.dpoz.socnetw.repository.intf.UserSecret;

import java.sql.Types;
import java.util.UUID;

@Repository("UserSecret")
public class UserSecretDAO implements UserSecret
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public UserSecretEntity findUsername(String username)
    {
        //TODO добавить Limit 1 после переезда на mySQL
        String SQL_LOGIN_EXISTS = "select password, username, user_id from user_secret where username = :l";
        try {
            return jdbc.queryForObject(
                    SQL_LOGIN_EXISTS,
                    new MapSqlParameterSource().addValue("l", username, Types.VARCHAR),
                    new BeanPropertyRowMapper<>(UserSecretEntity.class)
            );
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void add(String username, String password, UUID userId)
    {
        String SQL_ADD = "insert into user_secret (username, password, user_id) values (:l, :p, :uid);";
        password = WebSecurityConfig.passwordEncoder().encode(password);
        jdbc.update(SQL_ADD, new MapSqlParameterSource()
                .addValue("l", username, Types.VARCHAR)
                .addValue("p", password, Types.VARCHAR)
                .addValue("uid", userId, PostgresUUIDType.INSTANCE.sqlType())
        );
    }

    @Override
    public void add(UserSecretEntity userSecret)
    {
        this.add(userSecret.getUsername(),
                userSecret.getPassword(),
                userSecret.getUserId());
    }

}
