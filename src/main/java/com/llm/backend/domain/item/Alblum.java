package com.llm.backend.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") //싱글테이블이기 때문에 구분값
@Getter @Setter
public class Alblum extends Item {
    private String artist;
    private String etc;
}
