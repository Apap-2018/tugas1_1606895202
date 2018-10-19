package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel findByNIP(String NIP);
	void addPegawai(PegawaiModel pegawai);
	void setPegawaiNIP(String NIP);
	List<PegawaiModel> listPegawai(InstansiModel instansi);
}
