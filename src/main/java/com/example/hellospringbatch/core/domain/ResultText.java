package com.example.hellospringbatch.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "result_text")
@Getter
@Setter
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class ResultText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String text;

}

