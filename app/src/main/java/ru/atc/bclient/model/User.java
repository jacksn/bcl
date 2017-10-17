package ru.atc.bclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dim_user")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_user_id")
public class User extends BaseEntity {
    @Column(name = "user_login")
    @NotNull
    @Size(max = 100)
    private String login;

    @Column(name = "user_full_name")
    @NotNull
    @Size(max = 300)
    private String fullName;

    @Column(name = "user_password")
    @NotNull
    @Size(max = 100)
    private String password;

    public User(Integer id, String login, String fullName, String password) {
        this(login, fullName, password);
        setId(id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, fullName, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(password, user.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ",login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
