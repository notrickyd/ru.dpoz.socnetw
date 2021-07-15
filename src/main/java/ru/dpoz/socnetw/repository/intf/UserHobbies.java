package ru.dpoz.socnetw.repository.intf;

import ru.dpoz.socnetw.model.HobbyEntity;

import java.util.List;
import java.util.UUID;

public interface UserHobbies
{
    void add(UUID userId, int hobbyId);
    List<HobbyEntity> get(UUID userId);
}
