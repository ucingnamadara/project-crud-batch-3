package com.kawahedukasi.model.oas;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class CreateMahasiswaOAS {
    public class Body{
        @Schema(example = "171344003")
        public String nim;

        @Schema(example = "Ananda Dana Pratama")
        public String fullName;

        @Schema(example = "03-12-1998")
        public String dob;

        @Schema(example = "Bandung")
        public String pob;

        @Schema(example = "M")
        public String gender;

        @Schema(example = "Elektro")
        public String jurusan;
    }
}
