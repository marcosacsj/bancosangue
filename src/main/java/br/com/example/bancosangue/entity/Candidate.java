package br.com.example.bancosangue.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Candidate {
    public Candidate(){
        
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_nasc;
    private String sexo;
    private String mae;
    private String pai;
    private String email;
    private String cep;
    private String endereco;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone_fixo;
    private String celular;
    private double altura;
    private int peso;
    private String tipo_sanguineo;
//    private String tiposanguineoreceptor;
//    
//    public String getTipoSanguineoReceptor() {
//        BloodType tipoSanguineoCandidato = BloodType.fromValue(this.tipo_sanguineo);
//        RhFactor fatorRhCandidato = RhFactor.fromValue(this.tipo_sanguineo);
//
//        BloodType tipoSanguineoReceptor;
//        if (fatorRhCandidato == RhFactor.POSITIVE) {
//            tipoSanguineoReceptor = tipoSanguineoCandidato;
//        } else {
//            tipoSanguineoReceptor = tipoSanguineoCandidato.invert();
//        }
//
//        return tipoSanguineoReceptor.toString();
//    }
}


