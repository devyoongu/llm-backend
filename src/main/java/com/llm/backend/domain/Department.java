package com.llm.backend.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Entity
@Getter @Setter
@NoArgsConstructor
public class Department extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String departmentName; // 부서 및 팀이름

    @Column(name = "parent_id")
    private Long parentId; // 상위 부서 ID (상위 부서가 없으면 NULL)

    @Column(nullable = false)
    private int depth; // 부서 계층 (0: 최상위 부서, 1: 하위 부서)

    @Column(length = 20)
    private String mainPhone; // 부서 대표번호

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude // Lombok 순환 참조 방지
    private List<Job> jobs;

}
