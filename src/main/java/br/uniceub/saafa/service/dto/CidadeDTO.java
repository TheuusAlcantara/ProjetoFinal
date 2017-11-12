package br.uniceub.saafa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import br.uniceub.saafa.domain.enumeration.Estado;

/**
 * A DTO for the Cidade entity.
 */
public class CidadeDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Estado estado;

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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CidadeDTO cidadeDTO = (CidadeDTO) o;
        if(cidadeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cidadeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CidadeDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
