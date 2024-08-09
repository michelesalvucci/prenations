package com.michelesalvucci.prenations.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.HashSet;
import java.util.Set;

/**
 * A Nation.
 */
@Entity
@Table(name = "pren_nations")
@Data
@EqualsAndHashCode(callSuper = false)
public class PNNation extends PNTenantScopedAbstractAuditingEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "valid")
    private Boolean valid;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nation")
    @JsonIgnoreProperties(value = { "nation" }, allowSetters = true)
    private Set<PNCity> cities = new HashSet<>();
}
