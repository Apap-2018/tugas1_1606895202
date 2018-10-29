package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.Jabatan_PegawaiModel;
import com.apap.tugas1.model.PegawaiModel;


public interface Jabatan_PegawaiService {
	List<Jabatan_PegawaiModel> findByPegawai(PegawaiModel pegawai);
	List<Jabatan_PegawaiModel> findByJabatan(JabatanModel jabatan);
	boolean isJabatanPegawai(PegawaiModel pegawai, JabatanModel jabatan);
	int countPegawai(JabatanModel jabatan);
}
