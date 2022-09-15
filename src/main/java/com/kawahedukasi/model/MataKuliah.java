package com.kawahedukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "mata_kuliah")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MataKuliah extends PanacheEntityBase {

    @Id
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @Column(name = "mata_kuliah_id", length = 36, nullable = false, unique = true)
    private String mataKuliahId;

    @Column(name = "name")
    private String name;

    @Column(name = "sks")
    private Integer sks;

    @ManyToOne
    @JoinColumn(name = "mahasiswa_id")
    private Mahasiswa mahasiswa;
}
