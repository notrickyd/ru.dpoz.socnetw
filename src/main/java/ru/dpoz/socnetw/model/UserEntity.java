package ru.dpoz.socnetw.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@Getter
@Setter
public class UserEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID userId;

    @Column(columnDefinition = "varchar(30) not null")
    String firstName;

    @Column(columnDefinition = "varchar(30) not null")
    String lastName;

    @Column(columnDefinition = "smallint")
    Short age;

    @Column(columnDefinition = "varchar(1) not null")
    String gender;

    @Column(columnDefinition = "varchar(30) not null")
    String city;

}
