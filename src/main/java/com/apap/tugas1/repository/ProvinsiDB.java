package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiDB extends JpaRepository<ProvinsiModel, Long> {
	ProvinsiModel findById(long id);
	ProvinsiModel findByNama(String nama);
}
