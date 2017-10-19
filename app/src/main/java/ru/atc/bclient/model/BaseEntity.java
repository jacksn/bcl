package ru.atc.bclient.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public abstract class BaseEntity {
    @Id
    // PROPERTY access for id due to bug: https://hibernate.atlassian.net/browse/HHH-3718
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    @Access(value = AccessType.PROPERTY)
    @Getter
    @Setter
    private Integer id;

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
