package ru.atc.bclient.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dim_user")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_user_id")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "user.legalEntities",
                attributeNodes = @NamedAttributeNode("legalEntities")),
        @NamedEntityGraph(name = "user.legalEntities.accounts",
                attributeNodes = @NamedAttributeNode(value = "legalEntities", subgraph = "accounts"),
                subgraphs = @NamedSubgraph(name = "accounts", attributeNodes = @NamedAttributeNode("accounts")))
})
public class User extends BaseEntity {
    @NonNull
    @Column(name = "user_login")
    @NotNull
    @Size(max = 100)
    private String login;

    @NonNull
    @Column(name = "user_full_name")
    @NotNull
    @Size(max = 300)
    private String fullName;

    @NonNull
    @Column(name = "user_password")
    @NotNull
    @Size(max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_user_legal_entity",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "legal_entity_id"))
    private Set<LegalEntity> legalEntities;

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
                ", login='" + login + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
