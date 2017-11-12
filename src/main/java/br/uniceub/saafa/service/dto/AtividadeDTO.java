package br.uniceub.saafa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Atividade entity.
 */
public class AtividadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String horaInicio;

    @NotNull
    private String horaFim;

    private Long fluxoAtendimentoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public Long getFluxoAtendimentoId() {
        return fluxoAtendimentoId;
    }

    public void setFluxoAtendimentoId(Long fluxoAtendimentoId) {
        this.fluxoAtendimentoId = fluxoAtendimentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AtividadeDTO atividadeDTO = (AtividadeDTO) o;
        if(atividadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), atividadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AtividadeDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            "}";
    }
}
