package com.breeze.model;

import com.breeze.constant.BreezeConstants.BreezeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "breeze_config")
public class BreezeConfig extends AbstractModel {

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "status", columnDefinition = "enum")
    @Enumerated(EnumType.STRING)
    private BreezeStatus status;

    @Column(name = "description")
    private String description;
}
