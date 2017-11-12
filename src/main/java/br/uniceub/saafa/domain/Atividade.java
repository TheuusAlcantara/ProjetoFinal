package br.uniceub.saafa.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Atividade.
 */
@Entity
@Table(name = "atividade")
public class Atividade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;

    @NotNull
    @Column(name = "hora_fim", nullable = false)
    private String horaFim;

    @ManyToOne
    private FluxoAtendimento fluxoAtendimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Atividade descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public Atividade horaInicio(String horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public Atividade horaFim(String horaFim) {
        this.horaFim = horaFim;
        return this;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public FluxoAtendimento getFluxoAtendimento() {
        return fluxoAtendimento;
    }

    public Atividade fluxoAtendimento(FluxoAtendimento fluxoAtendimento) {
        this.fluxoAtendimento = fluxoAtendimento;
        return this;
    }

    public void setFluxoAtendimento(FluxoAtendimento fluxoAtendimento) {
        this.fluxoAtendimento = fluxoAtendimento;
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
        Atividade atividade = (Atividade) o;
        if (atividade.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), atividade.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Atividade{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFim='" + getHoraFim() + "'" +
            "}";
    }
}
