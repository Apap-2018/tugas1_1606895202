package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.Jabatan_PegawaiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface Jabatan_PegawaiDB extends JpaRepository<Jabatan_PegawaiModel, Long> {
	Jabatan_PegawaiModel findById(long id);
	List<Jabatan_PegawaiModel> findByPegawai(PegawaiModel pegawai);
	List<Jabatan_PegawaiModel> findByJabatan(JabatanModel jabatan);
}
