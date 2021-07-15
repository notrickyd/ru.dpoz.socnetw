package ru.dpoz.socnetw.repository.intf;

import ru.dpoz.socnetw.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface User
{
    UUID add(UserEntity user);
    UserEntity get(UUID userId);
    List<UserEntity> getAll();
}
