package com.apap.tugas1.service;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {
	 List<InstansiModel> listInstansi();
	 InstansiModel findById(long id);
	 List<InstansiModel> findByProvinsi(ProvinsiModel provinsi);
}
