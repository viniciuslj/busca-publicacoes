package br.gov.es.pm.sbp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * Pagina
 */
@Entity
@Table(name = "pagina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sbp_pagina")
public class Pagina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paginaSequence")
    @SequenceGenerator(name = "paginaSequence", sequenceName = "pagina_sequence", allocationSize = 1)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Lob
    @Column(name = "conteudo")
    private String conteudo;

    @NotNull
    @Column(name = "numero", nullable = false)
    private Integer numero;

    @ManyToOne
    @JsonIgnoreProperties("paginas")
    private Documento documento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public Pagina conteudo(String conteudo) {
        this.conteudo = conteudo;
        return this;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Integer getNumero() {
        return numero;
    }

    public Pagina numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Documento getDocumento() {
        return documento;
    }

    public Pagina documento(Documento documento) {
        this.documento = documento;
        return this;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pagina)) {
            return false;
        }
        return id != null && id.equals(((Pagina) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pagina{" +
            "id=" + getId() +
            ", conteudo='" + getConteudo() + "'" +
            ", numero=" + getNumero() +
            "}";
    }

    public static Pagina make(Documento documento, String conteudo, Integer numero) {
        Pagina pagina = new Pagina();
        pagina.setDocumento(documento);
        pagina.setConteudo(conteudo);
        pagina.setNumero(numero);
        return pagina;
    }
}
