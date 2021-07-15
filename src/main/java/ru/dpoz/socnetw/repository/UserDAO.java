package ru.dpoz.socnetw.repository;

import org.hibernate.type.PostgresUUIDType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dpoz.socnetw.model.UserEntity;
import ru.dpoz.socnetw.repository.intf.User;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Repository("Users")
public class UserDAO implements User
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public UUID add(UserEntity user)
    {
        UUID newId = UUID.randomUUID();
        String SQL_ADD = "insert into users (user_id, first_name, last_name, age, gender, city) " +
                "values (:uid, :first, :last, :age, :gender, :city)";
        jdbc.update(SQL_ADD,
                new MapSqlParameterSource()
                        .addValue("uid", newId, PostgresUUIDType.INSTANCE.sqlType())
                        .addValue("first", user.getFirstName(), Types.VARCHAR)
                        .addValue("last", user.getLastName(), Types.VARCHAR)
                        .addValue("age", user.getAge(), Types.SMALLINT)
                        .addValue("gender", user.getGender(), Types.VARCHAR)
                        .addValue("city", user.getCity(), Types.VARCHAR)
        );
        return newId;
    }

    @Override
    public List<UserEntity> getAll()
    {
        String SQL_GET_ALL = "select * from users order by first_name, last_name";
        return jdbc.query(
                SQL_GET_ALL,
                new MapSqlParameterSource(),
                new BeanPropertyRowMapper<>(UserEntity.class)
        );

    }

    @Override
    public UserEntity get(UUID userId)
    {
        String SQL_GET = "select * from users where user_id = :uid";
        return jdbc.queryForObject(
                SQL_GET,
                new MapSqlParameterSource().addValue("uid", userId, PostgresUUIDType.INSTANCE.sqlType()),
                new BeanPropertyRowMapper<>(UserEntity.class)
        );
    }
}
