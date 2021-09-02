package br.gov.es.pm.sbp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A LogBusca.
 */
@Entity
@Table(name = "log_busca")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sbp_logbusca")
@EntityListeners(AuditingEntityListener.class)
public class LogBusca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logBuscaSequence")
    @SequenceGenerator(name = "logBuscaSequence", sequenceName = "log_busca_sequence", allocationSize = 1)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "conteudo_buscado", nullable = false)
    private String conteudoBuscado;

    @NotNull
    @Column(name = "quantidade_encontrada", nullable = false)
    private Integer quantidadeEncontrada;

    @NotNull
    @Column(name = "tempo_ms", nullable = false)
    private Long tempoMs;

    @NotNull
    @Column(name = "erro", nullable = false)
    private Boolean erro = false;

    @Lob
    @Column(name = "erro_msg")
    private String erroMsg;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    protected Instant createdDate = Instant.now();

    @ManyToOne
    @JsonIgnoreProperties("logBuscas")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudoBuscado() {
        return conteudoBuscado;
    }

    public LogBusca conteudoBuscado(String conteudoBuscado) {
        this.conteudoBuscado = conteudoBuscado;
        return this;
    }

    public void setConteudoBuscado(String conteudoBuscado) {
        this.conteudoBuscado = conteudoBuscado;
    }

    public Integer getQuantidadeEncontrada() {
        return quantidadeEncontrada;
    }

    public LogBusca quantidadeEncontrada(Integer quantidadeEncontrada) {
        this.quantidadeEncontrada = quantidadeEncontrada;
        return this;
    }

    public void setQuantidadeEncontrada(Integer quantidadeEncontrada) {
        this.quantidadeEncontrada = quantidadeEncontrada;
    }

    public Long getTempoMs() {
        return tempoMs;
    }

    public LogBusca tempoMs(Long tempoMs) {
        this.tempoMs = tempoMs;
        return this;
    }

    public void setTempoMs(Long tempoMs) {
        this.tempoMs = tempoMs;
    }

    public Boolean isErro() {
        return erro;
    }

    public LogBusca erro(Boolean erro) {
        this.erro = erro;
        return this;
    }

    public void setErro(Boolean erro) {
        this.erro = erro;
    }

    public String getErroMsg() {
        return erroMsg;
    }

    public LogBusca erroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
        return this;
    }

    public void setErroMsg(String erroMsg) {
        this.erroMsg = erroMsg;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public LogBusca user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogBusca)) {
            return false;
        }
        return id != null && id.equals(((LogBusca) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LogBusca{" +
            "id=" + getId() +
            ", conteudoBuscado='" + getConteudoBuscado() + "'" +
            ", quantidadeEncontrada=" + getQuantidadeEncontrada() +
            ", tempoMs=" + getTempoMs() +
            ", erro='" + isErro() + "'" +
            ", erroMsg='" + getErroMsg() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
