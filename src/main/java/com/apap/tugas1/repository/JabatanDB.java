package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apap.tugas1.model.JabatanModel;

public interface JabatanDB extends JpaRepository<JabatanModel, Long> {
	JabatanModel findById(long id);
	List<JabatanModel> findAll();
	
}
