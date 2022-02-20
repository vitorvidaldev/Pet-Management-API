package dev.vitorvidal.petmanagementapi.model.user;

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
        this.password = encryptPassword(password);
    }

    public UserEntity(
            UUID userId,
            String username,
            String email,
            String password,
            String signature,
            LocalDateTime creationDate,
            Boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.signature = signature;
        this.creationDate = creationDate;
        this.isActive = isActive;
    }

    private String encryptPassword(String password) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.signature.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] bytes = mac.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password");
        }
    }

    public Boolean validatePassword(String password) {
        return Objects.equals(encryptPassword(password), this.password);
    }
}
