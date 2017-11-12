package br.uniceub.saafa.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UBS.
 */
@Entity
@Table(name = "ubs")
public class UBS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "hora_abertura")
    private String horaAbertura;

    @Column(name = "hora_fechamento")
    private String horaFechamento;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    @ManyToOne
    private Departamento departamentos;

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

    public UBS nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHoraAbertura() {
        return horaAbertura;
    }

    public UBS horaAbertura(String horaAbertura) {
        this.horaAbertura = horaAbertura;
        return this;
    }

    public void setHoraAbertura(String horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public String getHoraFechamento() {
        return horaFechamento;
    }

    public UBS horaFechamento(String horaFechamento) {
        this.horaFechamento = horaFechamento;
        return this;
    }

    public void setHoraFechamento(String horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public UBS endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Departamento getDepartamentos() {
        return departamentos;
    }

    public UBS departamentos(Departamento departamento) {
        this.departamentos = departamento;
        return this;
    }

    public void setDepartamentos(Departamento departamento) {
        this.departamentos = departamento;
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
        UBS uBS = (UBS) o;
        if (uBS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uBS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UBS{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", horaAbertura='" + getHoraAbertura() + "'" +
            ", horaFechamento='" + getHoraFechamento() + "'" +
            "}";
    }
}
