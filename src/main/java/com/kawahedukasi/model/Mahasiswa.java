package com.kawahedukasi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mahasiswa")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mahasiswa extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name = "mahasiswaSequence", sequenceName = "mahasiswa_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "mahasiswaSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "mahasiswa_id", nullable = false, unique = true)
    private Long mahasiswaId;

    @Column(name = "nim", nullable = false, unique = true)
    private String nim;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "pob", nullable = false)
    private String pob;

    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "jurusan", nullable = false)
    private String jurusan;

}