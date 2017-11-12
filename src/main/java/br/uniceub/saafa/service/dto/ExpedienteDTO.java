package br.uniceub.saafa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.uniceub.saafa.domain.enumeration.DiaSemana;

/**
 * A DTO for the Expediente entity.
 */
public class ExpedienteDTO implements Serializable {

    private Long id;

    @NotNull
    private String horarioEntrada;

    @NotNull
    private String horarioSaida;

    @NotNull
    private DiaSemana diaSemana;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public String getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExpedienteDTO expedienteDTO = (ExpedienteDTO) o;
        if(expedienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expedienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExpedienteDTO{" +
            "id=" + getId() +
            ", horarioEntrada='" + getHorarioEntrada() + "'" +
            ", horarioSaida='" + getHorarioSaida() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            "}";
    }
}
