package br.gov.es.pm.sbp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Diretorio
 */
@Entity
@Table(name = "diretorio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sbp_diretorio")
public class Diretorio extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diretorioSequence")
    @SequenceGenerator(name = "diretorioSequence", sequenceName = "diretorio_sequence", allocationSize = 1)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "restrito", nullable = false)
    private Boolean restrito;

    @NotNull
    @Column(name = "filtro_data", nullable = false)
    private Boolean filtroData;

    @OneToMany(mappedBy = "diretorio")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Documento> documentos = new HashSet<>();

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

    public Diretorio nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Diretorio descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean isRestrito() {
        return restrito;
    }

    public Diretorio restrito(Boolean restrito) {
        this.restrito = restrito;
        return this;
    }

    public void setRestrito(Boolean restrito) {
        this.restrito = restrito;
    }

    public Boolean isFiltroData() {
        return filtroData;
    }

    public Diretorio filtroData(Boolean filtroData) {
        this.filtroData = filtroData;
        return this;
    }

    public void setFiltroData(Boolean filtroData) {
        this.filtroData = filtroData;
    }

    public Set<Documento> getDocumentos() {
        return documentos;
    }

    public Diretorio documentos(Set<Documento> documentos) {
        this.documentos = documentos;
        return this;
    }

    public Diretorio addDocumento(Documento documento) {
        this.documentos.add(documento);
        documento.setDiretorio(this);
        return this;
    }

    public Diretorio removeDocumento(Documento documento) {
        this.documentos.remove(documento);
        documento.setDiretorio(null);
        return this;
    }

    public void setDocumentos(Set<Documento> documentos) {
        this.documentos = documentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diretorio)) {
            return false;
        }
        return id != null && id.equals(((Diretorio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Diretorio{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", descricao='" + descricao + '\'' +
            ", restrito=" + restrito +
            ", filtroData=" + filtroData +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy=" + lastModifiedBy +
            ", lastModifiedDate=" + lastModifiedDate +
            '}';
    }
}
