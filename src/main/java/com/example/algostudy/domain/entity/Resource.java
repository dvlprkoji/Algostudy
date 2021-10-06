package com.example.algostudy.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "order_num")
    private int orderNum;

    @OneToMany(mappedBy = "resource")
    private List<RoleResource> roleResourceList = new ArrayList<>();
}
