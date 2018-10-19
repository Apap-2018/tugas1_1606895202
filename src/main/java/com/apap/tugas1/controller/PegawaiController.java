package com.apap.tugas1.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.PegawaiService;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/pegawai")
	public String view(@RequestParam("nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.findByNIP(nip);
		model.addAttribute("pegawai", archive);
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String tambahPegawai(Model model) {
		model.addAttribute("pegawai", new PegawaiModel());
		return "tambah-pegawai";
	}
	
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai) {
		pegawaiService.addPegawai(pegawai);
		return "tambah";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua")
	private String cariPegawaiTermudaTertua(@RequestParam("id") long id, Model model) {
		List<PegawaiModel> archive = pegawaiService.listPegawai(instansiService.findById(id));
		PegawaiModel pegawaiTermuda = archive.get(0);
		PegawaiModel pegawaiTertua = archive.get(archive.size()-1);
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		return "termuda-tertua";
		
	}

	
}
