package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;

@Service
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDB instansiDb;
	
	@Override
	public List<InstansiModel> listInstansi() {
		System.out.println(instansiDb.findAll());
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel findById(long id) {
		return instansiDb.findById(id);
	}

	@Override
	public List<InstansiModel> findByProvinsi(ProvinsiModel provinsi) {
		return instansiDb.findByProvinsi(provinsi);
	}
	

}
