package com.example.algostudy.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id @GeneratedValue
    private Long id;

    private String roleName;

    private String roleDesc;

    @OneToMany(mappedBy = "role")
    private List<MemberRole> memberRoleList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<RoleResource> roleResourceList = new ArrayList<>();

}
