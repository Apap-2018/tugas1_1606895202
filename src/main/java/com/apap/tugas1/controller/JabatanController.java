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
import com.apap.tugas1.service.Jabatan_PegawaiService;


@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private Jabatan_PegawaiService jabatan_pegawaiService;
	
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
		int jumlahPegawai = jabatan_pegawaiService.countPegawai(jabatan);
		model.addAttribute("jumlahPegawai", jumlahPegawai);
		return "view-jabatan";
	}
	
	@RequestMapping("/jabatan/viewall")
	public String viewAll(Model model) {
		List<JabatanModel> allJabatan = jabatanService.findAllJabatan();
		model.addAttribute("allJabatan", allJabatan);
		return "viewall-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	public String ubahJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
		model.addAttribute("jabatan", jabatan);
		return "ubah-jabatan";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	public String ubahJabatanPost(@ModelAttribute JabatanModel jabatan, Model model) {
		JabatanModel archive = jabatanService.findJabatanById(jabatan.getId());
		archive.setNama(jabatan.getNama());
		archive.setDeskripsi(jabatan.getDeskripsi());
		archive.setGaji_pokok(jabatan.getGaji_pokok());
		jabatanService.addJabatan(archive);
		model.addAttribute("jabatan",archive);
		return "ubah-jabatan";
	}

	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String hapusJabatan(@RequestParam(value="idJabatan") Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.findJabatanById(idJabatan);
		jabatanService.deleteJabatan(jabatan);
		model.addAttribute("title", "Hapus Jabatan");
		return "hapus-jabatan";

	}
	
}
