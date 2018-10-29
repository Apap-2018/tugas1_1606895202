package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.Jabatan_PegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.Jabatan_PegawaiDB;

@Service
@Transactional
public class Jabatan_PegawaiServiceImpl implements Jabatan_PegawaiService{
	@Autowired
	private Jabatan_PegawaiDB jabatan_pegawaiDb;
	
	@Override
	public List<Jabatan_PegawaiModel> findByPegawai(
			PegawaiModel pegawai) {
		
		return jabatan_pegawaiDb.findByPegawai(pegawai);
	}

	@Override
	public int countPegawai(JabatanModel jabatan) {
		return jabatan_pegawaiDb.findByJabatan(jabatan).size();
	}

	@Override
	public List<Jabatan_PegawaiModel> findByJabatan(JabatanModel jabatan) {
		return jabatan_pegawaiDb.findByJabatan(jabatan);
	}

	@Override
	public boolean isJabatanPegawai(PegawaiModel pegawai,JabatanModel jabatan) {
		List<Jabatan_PegawaiModel> listJabatan = jabatan_pegawaiDb.findByPegawai(pegawai);
		for (Jabatan_PegawaiModel jabatanPegawai : listJabatan) {
			if(jabatanPegawai.getJabatan().equals(jabatan)) {
				return true;
			}
		}return false;
	}
	
}
