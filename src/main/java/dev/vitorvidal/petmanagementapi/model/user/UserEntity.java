package dev.vitorvidal.petmanagementapi.model.user;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.util.Objects;

@Table
public class UserEntity {

    @PrimaryKey
    private String id; // TODO initialize id
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String signature; // TODO initialize signature
    @Column
    private String creationDate; // TODO initialize creationDate
    @Column
    private Boolean isActive; // TODO initialize isActive

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
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
