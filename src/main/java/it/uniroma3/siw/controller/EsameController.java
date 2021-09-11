package it.uniroma3.siw.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Medico;
import it.uniroma3.siw.model.Tipo;
import it.uniroma3.siw.service.EsameService;
import it.uniroma3.siw.service.MedicoService;
import it.uniroma3.siw.service.TipoService;

@Controller
public class EsameController {

	@Autowired
	private TipoService tipoService;

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private EsameService esameService;

	@RequestMapping(value="/prenotaEsameForm", method=RequestMethod.GET)
	public String getPrenotaEsameForm(Model model) {
		model.addAttribute("esame", new Esame());
		model.addAttribute("tipologie", this.tipoService.tutti());
		model.addAttribute("medici", this.medicoService.tutti());
		return "/admin/prenotaEsameForm";
	}
	
	@RequestMapping(value="/prenotaEsame", method=RequestMethod.POST)
	public String prenotaEsame(Model model, 
							   @ModelAttribute("esame") Esame esame,
							   @RequestParam("tipoID") Long idTipo,
							   @RequestParam("medicoID") Long idMedico) {
		
		Tipo tipo = tipoService.tipoPerId(idTipo);
		esame.setTipo(tipo);
		
		Medico medico = medicoService.medicoPerId(idMedico);
		esame.setMedico(medico);
		
		LocalDateTime now = LocalDateTime.now();  
		esame.setDataPrenotazione(now);
		
		
		esameService.inserisci(esame);
		
		return "index";
	}
}
