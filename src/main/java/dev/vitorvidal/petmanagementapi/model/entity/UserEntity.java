package dev.vitorvidal.petmanagementapi.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
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
    @Column
    private String signature;
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
        this.signature = UUID.randomUUID().toString().replace("-", "");
        this.isActive = true;
        this.creationDate = LocalDateTime.now();
        this.password = password;
    }
}
