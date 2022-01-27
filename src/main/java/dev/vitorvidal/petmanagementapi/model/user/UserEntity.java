package dev.vitorvidal.petmanagementapi.model.user;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

// TODO automatically create tables based on class instances
@Table(value = "user")
public class UserEntity {

    @PrimaryKey(value = "user_id")
    private UUID userId; // TODO initialize id
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String signature; // TODO initialize signature
    @Column
    private LocalDateTime creationDate; // TODO initialize creationDate
    @Column
    private Boolean isActive; // TODO initialize isActive

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    Boolean validatePassword(String password) {
        // TODO add hash algorithm
//        var hash = new HMACParameterSpec(64).
        String hash = null;
        return Objects.equals(hash, this.password);
    }
}
