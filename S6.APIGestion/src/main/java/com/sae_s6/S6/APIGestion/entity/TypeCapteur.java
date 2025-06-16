package com.sae_s6.S6.APIGestion.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typecapteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeCapteur {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;


}
