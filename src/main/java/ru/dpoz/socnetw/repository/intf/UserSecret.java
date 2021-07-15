package ru.dpoz.socnetw.repository.intf;

import ru.dpoz.socnetw.model.UserSecretEntity;

import java.util.UUID;

public interface UserSecret
{
    void add(String username, String password, UUID userId);
    void add(UserSecretEntity userSecret);
    UserSecretEntity findUsername(String username);
}
