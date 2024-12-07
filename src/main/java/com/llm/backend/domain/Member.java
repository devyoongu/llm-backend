package com.llm.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    // mappedBy는 연관관계에서 주인이 아닌 매핑된 거울일 뿐이다.
    // 여기에 값을 넣는다고 해서 order의 fk 값이 변경되지 않는다.(읽기전용)
    private List<Order> orders = new ArrayList<>();

}
