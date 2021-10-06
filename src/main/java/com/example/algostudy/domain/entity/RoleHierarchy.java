package com.example.algostudy.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RoleHierarchy {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="child_name")
    private String childName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_name", referencedColumnName = "child_name")
    private RoleHierarchy parentName;

    @OneToMany(mappedBy = "parentName")
    private List<RoleHierarchy> childRoles = new ArrayList<>();
}
