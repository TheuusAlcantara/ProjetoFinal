package br.uniceub.saafa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UBS entity.
 */
public class UBSDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String horaAbertura;

    private String horaFechamento;

    private Long enderecoId;

    private Long departamentosId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(String horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public String getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(String horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public Long getDepartamentosId() {
        return departamentosId;
    }

    public void setDepartamentosId(Long departamentoId) {
        this.departamentosId = departamentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UBSDTO uBSDTO = (UBSDTO) o;
        if(uBSDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uBSDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UBSDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", horaAbertura='" + getHoraAbertura() + "'" +
            ", horaFechamento='" + getHoraFechamento() + "'" +
            "}";
    }
}
