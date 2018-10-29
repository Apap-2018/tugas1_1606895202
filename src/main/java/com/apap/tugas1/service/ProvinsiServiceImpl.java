package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{

	@Autowired
	private ProvinsiDB provinsiDb;
	
	@Override
	public List<ProvinsiModel> findAllProvinsi() {
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel findById(long id) {
		return provinsiDb.findById(id);
	}

	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel getProvinsiByName(String nama) {
		return provinsiDb.findByNama(nama);
	}
	
}
