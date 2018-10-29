package com.apap.tugas1.controller;


import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.Jabatan_PegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.Jabatan_PegawaiService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private Jabatan_PegawaiService jabatan_pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	
	@RequestMapping("/pegawai")
	public String view(@RequestParam("nip") String nip, Model model) {
		PegawaiModel archive = pegawaiService.findByNIP(nip);
		model.addAttribute("pegawai", archive);
		List<Jabatan_PegawaiModel> jabatanPegawai = jabatan_pegawaiService.findByPegawai(pegawaiService.findByNIP(nip));
		double gaji = 0;
		for (Jabatan_PegawaiModel jabatan : jabatanPegawai) {
			double gajiJabatan = jabatan.getJabatan().getGaji_pokok();
			if(gaji<gajiJabatan) {
				gaji = gajiJabatan;
			}
		}
		double tunjangan = (archive.getInstansi().getProvinsi().getPresentase_tunjangan()*gaji)/100;
		int gaji_pokok = (int)( gaji + tunjangan );
		
		model.addAttribute("gaji", gaji_pokok);
		model.addAttribute("jabatan", jabatanPegawai);
		
		return "view-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String tambahPegawai (Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		model.addAttribute("pegawai", pegawai);
		List<InstansiModel> listInstansi = instansiService.listInstansi();
		model.addAttribute("listInstansi", new HashSet(listInstansi));
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		model.addAttribute("listJabatan", new HashSet(listJabatan));
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(0, 0, 0);
		model.addAttribute("tanggalLahir", dateFormat.format(date));
		model.addAttribute("headerTitle", "Tambah Pegawai");
		return "tambah-pegawai";
	}
	 
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params={"addJabatan"})
	private String addRow(@ModelAttribute PegawaiModel pegawai, Model model) {
		model.addAttribute("headerTitle", "Tambah Pegawai");
		model.addAttribute("pegawai", pegawai);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggal_lahir());
		model.addAttribute("tanggalLahir", tanggalLahir);
		List<InstansiModel> listInstansi = instansiService.findByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", new HashSet(listInstansi));
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		model.addAttribute("listJabatan", new HashSet(listJabatan));
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		return "tambah";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai (@RequestParam(value="provinsiId", required = false) Optional<Long> provinsiId, 
								@RequestParam(value="instansiId", required = false) Optional<Long> instansiId, 
								@RequestParam(value="jabatanId", required = false) Optional<Long> jabatanId, Model model) {
		ProvinsiModel provinsi = null;
		InstansiModel instansi = null;
		JabatanModel jabatan = null;
		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();
		model.addAttribute("listJabatan", new HashSet(listJabatan));
		List<ProvinsiModel> listProvinsi = provinsiService.findAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		List<PegawaiModel> hasilPencarian = null;
		if (provinsiId.isPresent()) {
			provinsi = provinsiService.findById(provinsiId.get());
			if (instansiId.isPresent()) {
				instansi = instansiService.findById(instansiId.get());
				if (jabatanId.isPresent()) {
					jabatan = jabatanService.findJabatanById(jabatanId.get());	
					hasilPencarian = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
				}
				else {
					hasilPencarian = pegawaiService.getPegawaiByInstansi(instansi);
				}
			}
			else if (jabatanId.isPresent()) {
				jabatan = jabatanService.findJabatanById(jabatanId.get());	
				hasilPencarian = pegawaiService.getPegawaiByProvinsiAndJabatan(provinsiId.get(), jabatan);
			}
			else {
				hasilPencarian = pegawaiService.getPegawaiByProvinsi(provinsiId.get());
			}
		}
		else {
			if (jabatanId.isPresent()) {
				jabatan = jabatanService.findJabatanById(jabatanId.get());	
				hasilPencarian = pegawaiService.getPegawaiByJabatan(jabatan);
			}
		}
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("hasilPencarian", hasilPencarian);
		model.addAttribute("headerTitle", "Cari Pegawai");
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)

	public String tambahPegawai (@RequestParam String nip, Model model) {

		PegawaiModel pegawai = pegawaiService.findByNIP(nip);

		model.addAttribute("pegawai", pegawai);

		

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggal_lahir());

		model.addAttribute("tanggalLahir", tanggalLahir);

		

		List<InstansiModel> listInstansi = instansiService.findByProvinsi(pegawai.getInstansi().getProvinsi());

		model.addAttribute("listInstansi", new HashSet(listInstansi));

		

		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();

		model.addAttribute("listJabatan", new HashSet(listJabatan));

		

		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();

		model.addAttribute("listProvinsi", listProvinsi);

		model.addAttribute("headerTitle", "Ubah Pegawai");

		return "ubah-pegawai";

	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params={"addJabatan"})

	private String addRowUbah(@ModelAttribute PegawaiModel pegawai, Model model) {

		model.addAttribute("headerTitle", "Tambah Pegawai");
		model.addAttribute("pegawai", pegawai);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggal_lahir());

		model.addAttribute("tanggalLahir", tanggalLahir);

		

		List<InstansiModel> listInstansi = instansiService.findByProvinsi(pegawai.getInstansi().getProvinsi());

		model.addAttribute("listInstansi", new HashSet(listInstansi));

		

		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();

		model.addAttribute("listJabatan", new HashSet(listJabatan));

		

		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();

		model.addAttribute("listProvinsi", listProvinsi);

		return "ubah-pegawai";

	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params={"deleteJabatan"})

	private String deleteRowUbah(@ModelAttribute PegawaiModel pegawai, Model model, HttpServletRequest req) {

		model.addAttribute("headerTitle", "Tambah Pegawai");

		model.addAttribute("pegawai", pegawai); 

		

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String tanggalLahir = simpleDateFormat.format(pegawai.getTanggal_lahir());

		model.addAttribute("tanggalLahir", tanggalLahir);

		

		List<InstansiModel> listInstansi = instansiService.findByProvinsi(pegawai.getInstansi().getProvinsi());

		model.addAttribute("listInstansi", new HashSet(listInstansi));

		

		List<JabatanModel> listJabatan = jabatanService.findAllJabatan();

		model.addAttribute("listJabatan", new HashSet(listJabatan));

		

		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();

		model.addAttribute("listProvinsi", listProvinsi);

		return "ubah-pegawai";

	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)

	public String ubahPegawai (@ModelAttribute PegawaiModel pegawai, Model model) {

		String oldNip = pegawai.getNip();

		pegawaiService.update(oldNip, pegawai);

		model.addAttribute("pegawai", pegawai);

		model.addAttribute("headerTitle", "Tambah Pegawai Sukses");

		return "ubah";

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
