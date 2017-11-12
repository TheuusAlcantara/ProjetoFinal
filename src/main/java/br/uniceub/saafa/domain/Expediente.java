package br.uniceub.saafa.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import br.uniceub.saafa.domain.enumeration.DiaSemana;

/**
 * A Expediente.
 */
@Entity
@Table(name = "expediente")
public class Expediente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "horario_entrada", nullable = false)
    private String horarioEntrada;

    @NotNull
    @Column(name = "horario_saida", nullable = false)
    private String horarioSaida;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiaSemana diaSemana;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHorarioEntrada() {
        return horarioEntrada;
    }

    public Expediente horarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
        return this;
    }

    public void setHorarioEntrada(String horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public String getHorarioSaida() {
        return horarioSaida;
    }

    public Expediente horarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
        return this;
    }

    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public Expediente diaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
        return this;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
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
        Expediente expediente = (Expediente) o;
        if (expediente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expediente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Expediente{" +
            "id=" + getId() +
            ", horarioEntrada='" + getHorarioEntrada() + "'" +
            ", horarioSaida='" + getHorarioSaida() + "'" +
            ", diaSemana='" + getDiaSemana() + "'" +
            "}";
    }
}
