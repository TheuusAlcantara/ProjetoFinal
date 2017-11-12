package br.uniceub.saafa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FluxoAtendimento.
 */
@Entity
@Table(name = "fluxo_atendimento")
public class FluxoAtendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome_paciente", nullable = false)
    private String nomePaciente;

    @NotNull
    @Column(name = "razao_visita", nullable = false)
    private String razaoVisita;

    @NotNull
    @Column(name = "hora_chegada", nullable = false)
    private String horaChegada;

    @OneToOne
    @JoinColumn(unique = true)
    private Agendamento agendamento;

    @OneToOne
    @JoinColumn(unique = true)
    private UBS ubs;

    @OneToMany(mappedBy = "fluxoAtendimento")
    @JsonIgnore
    private Set<Atividade> fluxoAtendimentos = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "fluxo_atendimento_funcionario",
               joinColumns = @JoinColumn(name="fluxo_atendimentos_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="funcionarios_id", referencedColumnName="id"))
    private Set<Funcionario> funcionarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public FluxoAtendimento nomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
        return this;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getRazaoVisita() {
        return razaoVisita;
    }

    public FluxoAtendimento razaoVisita(String razaoVisita) {
        this.razaoVisita = razaoVisita;
        return this;
    }

    public void setRazaoVisita(String razaoVisita) {
        this.razaoVisita = razaoVisita;
    }

    public String getHoraChegada() {
        return horaChegada;
    }

    public FluxoAtendimento horaChegada(String horaChegada) {
        this.horaChegada = horaChegada;
        return this;
    }

    public void setHoraChegada(String horaChegada) {
        this.horaChegada = horaChegada;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public FluxoAtendimento agendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
        return this;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public UBS getUbs() {
        return ubs;
    }

    public FluxoAtendimento ubs(UBS uBS) {
        this.ubs = uBS;
        return this;
    }

    public void setUbs(UBS uBS) {
        this.ubs = uBS;
    }

    public Set<Atividade> getFluxoAtendimentos() {
        return fluxoAtendimentos;
    }

    public FluxoAtendimento fluxoAtendimentos(Set<Atividade> atividades) {
        this.fluxoAtendimentos = atividades;
        return this;
    }

    public FluxoAtendimento addFluxoAtendimento(Atividade atividade) {
        this.fluxoAtendimentos.add(atividade);
        atividade.setFluxoAtendimento(this);
        return this;
    }

    public FluxoAtendimento removeFluxoAtendimento(Atividade atividade) {
        this.fluxoAtendimentos.remove(atividade);
        atividade.setFluxoAtendimento(null);
        return this;
    }

    public void setFluxoAtendimentos(Set<Atividade> atividades) {
        this.fluxoAtendimentos = atividades;
    }

    public Set<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public FluxoAtendimento funcionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        return this;
    }

    public FluxoAtendimento addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.getConsultas().add(this);
        return this;
    }

    public FluxoAtendimento removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.getConsultas().remove(this);
        return this;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
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
        FluxoAtendimento fluxoAtendimento = (FluxoAtendimento) o;
        if (fluxoAtendimento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fluxoAtendimento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FluxoAtendimento{" +
            "id=" + getId() +
            ", nomePaciente='" + getNomePaciente() + "'" +
            ", razaoVisita='" + getRazaoVisita() + "'" +
            ", horaChegada='" + getHoraChegada() + "'" +
            "}";
    }
}
