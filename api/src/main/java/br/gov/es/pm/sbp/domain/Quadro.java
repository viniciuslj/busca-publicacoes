package br.gov.es.pm.sbp.domain;


import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * Quadros funcionais
 */
@ApiModel(description = "Quadros funcionais")
@Entity
@Table(name = "quadro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sbp_quadro")
public class Quadro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quadroSequence")
    @SequenceGenerator(name = "quadroSequence", sequenceName = "quadro_sequence", allocationSize = 1)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "nome_longo", nullable = false)
    private String nomeLongo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Quadro nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeLongo() {
        return nomeLongo;
    }

    public Quadro nomeLongo(String nomeLongo) {
        this.nomeLongo = nomeLongo;
        return this;
    }

    public void setNomeLongo(String nomeLongo) {
        this.nomeLongo = nomeLongo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quadro)) {
            return false;
        }
        return id != null && id.equals(((Quadro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Quadro{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeLongo='" + getNomeLongo() + "'" +
            "}";
    }
}
