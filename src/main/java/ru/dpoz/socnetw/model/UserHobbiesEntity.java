package ru.dpoz.socnetw.model;

import lombok.Data;
import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user_hobbies")
@Data
public class UserHobbiesEntity implements Serializable
{

    @Id
    UUID userId;

    @Id
    @Column(nullable = false)
    Short hobbyId;

}
