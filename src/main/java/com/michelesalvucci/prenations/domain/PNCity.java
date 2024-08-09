package com.michelesalvucci.prenations.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "pren_cities")
@Data
@EqualsAndHashCode(callSuper = false)
public class PNCity extends PNTenantScopedAbstractAuditingEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "population")
    private Long population;

    @Column(name = "nation_id", insertable = false, updatable = false)
    private Long nationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cities" }, allowSetters = true)
    private PNNation nation;
}
