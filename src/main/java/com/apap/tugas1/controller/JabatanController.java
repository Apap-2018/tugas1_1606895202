package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;


@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		List<InstansiModel> listInstansi = instansiService.listInstansi();
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String tambahJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "tambah-jabatan";
	}
	
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "tambah-jabatan";
	}
	
	@RequestMapping("/jabatan/view")
	public String view(@RequestParam("id") long id, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(id);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping("/jabatan/viewall")
	public String viewAll(Model model) {
		List<JabatanModel> allJabatan = jabatanService.findAllJabatan();
		model.addAttribute("allJabatan", allJabatan);
		return "viewall-jabatan";
	}
	
}
