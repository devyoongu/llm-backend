package com.llm.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Employee extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Long id;

    @Column(length = 50)
    private String userId; // 직원 유저 ID

    @Column(nullable = false, length = 100)
    private String employeeName; // 직원 이름

    @Column(length = 20)
    private String extensionNumber; // 직원 내선번호

    @Column(length = 20)
    private String personalPhone; // 개인 전화번호

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<EmployeeRegion> employeeRegions; // 담당 지역 리스트

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<EmployeeJob> employeeJobs; // 담당 지역 리스트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    @ToString.Exclude
    @JsonIgnore
    private Department department;

}
