package br.uniceub.saafa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the FluxoAtendimento entity.
 */
public class FluxoAtendimentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomePaciente;

    @NotNull
    private String razaoVisita;

    @NotNull
    private String horaChegada;

    private Long agendamentoId;

    private Long ubsId;

    private Set<FuncionarioDTO> funcionarios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getRazaoVisita() {
        return razaoVisita;
    }

    public void setRazaoVisita(String razaoVisita) {
        this.razaoVisita = razaoVisita;
    }

    public String getHoraChegada() {
        return horaChegada;
    }

    public void setHoraChegada(String horaChegada) {
        this.horaChegada = horaChegada;
    }

    public Long getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(Long agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public Long getUbsId() {
        return ubsId;
    }

    public void setUbsId(Long uBSId) {
        this.ubsId = uBSId;
    }

    public Set<FuncionarioDTO> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(Set<FuncionarioDTO> funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FluxoAtendimentoDTO fluxoAtendimentoDTO = (FluxoAtendimentoDTO) o;
        if(fluxoAtendimentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fluxoAtendimentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FluxoAtendimentoDTO{" +
            "id=" + getId() +
            ", nomePaciente='" + getNomePaciente() + "'" +
            ", razaoVisita='" + getRazaoVisita() + "'" +
            ", horaChegada='" + getHoraChegada() + "'" +
            "}";
    }
}
