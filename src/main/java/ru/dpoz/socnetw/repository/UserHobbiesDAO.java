package ru.dpoz.socnetw.repository;

import org.hibernate.type.PostgresUUIDType;
import org.hibernate.type.UUIDBinaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dpoz.socnetw.model.HobbyEntity;
import ru.dpoz.socnetw.repository.intf.UserHobbies;

import java.sql.Types;
import java.util.List;
import java.util.UUID;

@Repository("UsersHobbies")
public class UserHobbiesDAO implements UserHobbies
{
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public void add(UUID userId, int hobbyId)
    {
        String SQL_ADD = "insert into user_hobbies (user_id, hobby_id) values (:uid, :hid);";
        jdbc.update(SQL_ADD, new MapSqlParameterSource()
                .addValue("uid", userId, PostgresUUIDType.INSTANCE.sqlType())
                .addValue("hid", hobbyId, Types.SMALLINT)
        );
    }

    @Override
    public List<HobbyEntity> get(UUID userId)
    {
        String SQL_GET = "select h.* from user_hobbies uh " +
                "inner join hobby h on h.hobby_id = uh.hobby_id " +
                "where uh.user_id = :uid";
        return jdbc.query(
                SQL_GET,
                new MapSqlParameterSource().addValue("uid", userId, PostgresUUIDType.INSTANCE.sqlType()),
                new BeanPropertyRowMapper<>(HobbyEntity.class)
                );
    }
}
