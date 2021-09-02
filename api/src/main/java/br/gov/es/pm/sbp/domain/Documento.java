package br.gov.es.pm.sbp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Documento
 */
@Entity
@Table(name = "documento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sbp_documento")
public class Documento extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentoSequence")
    @SequenceGenerator(name = "documentoSequence", sequenceName = "documento_sequence", allocationSize = 1)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "path")
    private String path;

    @Column(name = "quantidade_paginas")
    private Integer quantidadePaginas = 0;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column(name = "processado")
    private Boolean processado = false;

    @Lob
    @Column(name = "erro_processamento")
    private String erroProcessamento;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("documentos")
    private Diretorio diretorio;

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

    public Documento nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Documento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getQuantidadePaginas() {
        return quantidadePaginas;
    }

    public Documento quantidadePaginas(Integer quantidadePaginas) {
        this.quantidadePaginas = quantidadePaginas;
        return this;
    }

    public void setQuantidadePaginas(Integer quantidadePaginas) {
        this.quantidadePaginas = quantidadePaginas;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public Documento dataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Boolean isProcessado() {
        return processado;
    }

    public Documento processado(Boolean processado) {
        this.processado = processado;
        return this;
    }

    public void setProcessado(Boolean processado) {
        this.processado = processado;
    }

    public String getErroProcessamento() {
        return erroProcessamento;
    }

    public Documento erroProcessamento(String erroProcessamento) {
        this.erroProcessamento = erroProcessamento;
        return this;
    }

    public void setErroProcessamento(String erroProcessamento) {
        this.erroProcessamento = erroProcessamento;
    }

    public Diretorio getDiretorio() {
        return diretorio;
    }

    public Documento diretorio(Diretorio diretorio) {
        this.diretorio = diretorio;
        return this;
    }

    public void setDiretorio(Diretorio diretorio) {
        this.diretorio = diretorio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documento)) {
            return false;
        }
        return id != null && id.equals(((Documento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Documento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", path='" + getPath() + "'" +
            ", quantidadePaginas=" + getQuantidadePaginas() +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            ", processado='" + isProcessado() + "'" +
            ", erroProcessamento='" + getErroProcessamento() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", createdDate=" + getCreatedDate() +
            ", lastModifiedBy=" + getLastModifiedBy() +
            ", lastModifiedDate=" + getLastModifiedDate() +
            "}";
    }
}
