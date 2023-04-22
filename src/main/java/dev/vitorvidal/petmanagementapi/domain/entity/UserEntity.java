package dev.vitorvidal.petmanagementapi.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(value = "user")
public class UserEntity {

    @PrimaryKey(value = "email")
    private String email;
    @Column
    private String username;
    @Column(value = "user_id")
    @Indexed
    private UUID userId;
    @Column
    private String password;
    @Column(value = "creation_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime creationDate;
    @Column(value = "is_active")
    private Boolean isActive;

    public UserEntity() {
    }

    public UserEntity(String username, String email, String password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.isActive = true;
        this.creationDate = LocalDateTime.now();
        this.password = password;
    }
}
