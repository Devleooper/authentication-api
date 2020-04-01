package co.com.jwtapp.authenticationapi.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "AUTH_USER")
public class AuthUser implements Serializable {
    private static final long serialVersionUID = -2450427553969419267L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    @Type(type = "pg-uuid")
    private UUID uid = UUID.randomUUID();

    public AuthUser() {
    }

    public AuthUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public AuthUser(String name, String password, UUID uid) {
        this.name = name;
        this.password = password;
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }
}
