package br.uniceub.saafa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Funcionario.
 */
@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(unique = true)
    private Cargo cargo;

    @OneToMany(mappedBy = "funcionario")
    @JsonIgnore
    private Set<Departamento> departamentos = new HashSet<>();

    @ManyToOne
    private Expediente expedientes;

    @ManyToMany(mappedBy = "funcionarios")
    @JsonIgnore
    private Set<FluxoAtendimento> consultas = new HashSet<>();

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

    public Funcionario nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Funcionario cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Funcionario endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public Funcionario cargo(Cargo cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Set<Departamento> getDepartamentos() {
        return departamentos;
    }

    public Funcionario departamentos(Set<Departamento> departamentos) {
        this.departamentos = departamentos;
        return this;
    }

    public Funcionario addDepartamento(Departamento departamento) {
        this.departamentos.add(departamento);
        departamento.setFuncionario(this);
        return this;
    }

    public Funcionario removeDepartamento(Departamento departamento) {
        this.departamentos.remove(departamento);
        departamento.setFuncionario(null);
        return this;
    }

    public void setDepartamentos(Set<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public Expediente getExpedientes() {
        return expedientes;
    }

    public Funcionario expedientes(Expediente expediente) {
        this.expedientes = expediente;
        return this;
    }

    public void setExpedientes(Expediente expediente) {
        this.expedientes = expediente;
    }

    public Set<FluxoAtendimento> getConsultas() {
        return consultas;
    }

    public Funcionario consultas(Set<FluxoAtendimento> fluxoAtendimentos) {
        this.consultas = fluxoAtendimentos;
        return this;
    }

    public Funcionario addConsulta(FluxoAtendimento fluxoAtendimento) {
        this.consultas.add(fluxoAtendimento);
        fluxoAtendimento.getFuncionarios().add(this);
        return this;
    }

    public Funcionario removeConsulta(FluxoAtendimento fluxoAtendimento) {
        this.consultas.remove(fluxoAtendimento);
        fluxoAtendimento.getFuncionarios().remove(this);
        return this;
    }

    public void setConsultas(Set<FluxoAtendimento> fluxoAtendimentos) {
        this.consultas = fluxoAtendimentos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Funcionario funcionario = (Funcionario) o;
        if (funcionario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), funcionario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            "}";
    }
}
