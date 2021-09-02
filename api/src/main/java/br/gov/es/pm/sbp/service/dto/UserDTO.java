package br.gov.es.pm.sbp.service.dto;

import br.gov.es.pm.sbp.config.Constants;

import br.gov.es.pm.sbp.domain.Authority;
import br.gov.es.pm.sbp.domain.Cargo;
import br.gov.es.pm.sbp.domain.Quadro;
import br.gov.es.pm.sbp.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 11, max = 11)
    private String login;

    @Size(max = 200)
    private String nome;

    private Integer rg;

    private Integer numeroFuncional;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private Cargo cargo;

    private Quadro quadro;

    private boolean activated = false;

    private Instant createdDate;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.nome = user.getNome();
        this.rg = user.getRg();
        this.numeroFuncional = user.getNumeroFuncional();
        this.email = user.getEmail();
        this.quadro = user.getQuadro();
        this.cargo = user.getCargo();
        this.activated = user.getActivated();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedDate = user.getLastModifiedDate();

        this.authorities = user.getAuthorities().stream()
            .map(Authority::getName)
            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getRg() {
        return rg;
    }

    public void setRg(Integer rg) {
        this.rg = rg;
    }

    public Integer getNumeroFuncional() {
        return numeroFuncional;
    }

    public void setNumeroFuncional(Integer numeroFuncional) {
        this.numeroFuncional = numeroFuncional;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Quadro getQuadro() {
        return quadro;
    }

    public void setQuadro(Quadro quadro) {
        this.quadro = quadro;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + id +
            ", login='" + login + '\'' +
            ", nome='" + nome + '\'' +
            ", rg=" + rg +
            ", numeroFuncional=" + numeroFuncional +
            ", email='" + email + '\'' +
            ", cargo=" + cargo +
            ", quadro=" + quadro +
            ", activated=" + activated +
            ", createdDate=" + createdDate +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            '}';
    }
}
