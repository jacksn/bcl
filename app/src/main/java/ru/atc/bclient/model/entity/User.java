package ru.atc.bclient.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true, exclude = "legalEntities")
@Entity
@Table(name = "dim_user")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_user_id")
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "rel_user_legal_entity",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "legal_entity_id"))
    private Set<LegalEntity> legalEntities;

    public User(Integer id, String login, String fullName, String password) {
        this(login, fullName, password);
        setId(id);
    }
}
