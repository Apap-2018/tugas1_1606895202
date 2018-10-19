package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apap.tugas1.model.Jabatan_PegawaiModel;

public interface Jabatan_PegawaiDB extends JpaRepository<Jabatan_PegawaiModel, Long> {
	Jabatan_PegawaiModel findById(long id);
}
