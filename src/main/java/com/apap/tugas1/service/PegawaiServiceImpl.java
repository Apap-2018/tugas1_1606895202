package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Override
	public PegawaiModel findByNIP(String NIP) {
		return pegawaiDb.findBynip(NIP);
	}

	@Override
	public void setPegawaiNIP(String NIP) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
	}

	@Override
	public List<PegawaiModel> listPegawai(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}
	
	
	
	
}
