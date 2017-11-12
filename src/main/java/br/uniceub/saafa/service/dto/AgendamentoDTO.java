package br.uniceub.saafa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Agendamento entity.
 */
public class AgendamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String horario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgendamentoDTO agendamentoDTO = (AgendamentoDTO) o;
        if(agendamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agendamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgendamentoDTO{" +
            "id=" + getId() +
            ", horario='" + getHorario() + "'" +
            "}";
    }
}
