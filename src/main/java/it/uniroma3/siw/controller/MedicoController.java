package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Medico;
import it.uniroma3.siw.service.MedicoService;

@Controller
public class MedicoController {

	@Autowired
	private MedicoService medicoService;

	@RequestMapping(value="/addMedico", method=RequestMethod.GET)
	public String getAddMedicoForm(Model model) {
		model.addAttribute("medico", new Medico());
		return "/admin/addMedicoForm";

	}
	
	@RequestMapping(value="/addMedico", method=RequestMethod.POST)
	public String addMedicoForm(Model model, @ModelAttribute("medico") Medico medico) {

		medicoService.inserisci(medico);
		return "index";
	}
	
	@RequestMapping(value="/deleteMedico", method=RequestMethod.GET)
	public String deleteMedico(Model model,
							   @RequestParam("medicoID") Long id,
							   @RequestParam("nome") String nome,
							   @RequestParam("cognome") String cognome) {
		
		Medico medicoByNC = this.medicoService.medicoPerNomeCognome(nome, cognome);
		Medico medicoById = this.medicoService.medicoPerId(id);
		
		if(medicoById.equals(medicoByNC)) {
			this.medicoService.delete(medicoById);
		}
		
		return "index";
	}
	@RequestMapping(value="/deleteMedicoForm", method=RequestMethod.GET)
	public String deleteMedico(Model model) {
		
		
		return "/admin/deleteMedico";
	}
	
	@RequestMapping(value="/cercaMedico", method=RequestMethod.GET)
	public String cercaMedico(Model model) {
		
		return "/admin/cercaMedico";
	}
	
	@RequestMapping(value="/esamiPerMedico", method=RequestMethod.GET)
	public String cercaMedico(Model model, @RequestParam("nome") String nome, @RequestParam("cognome") String cognome) {
		
		Medico medico = this.medicoService.medicoPerNomeCognome(nome, cognome);
		
		List<Esame> esami = this.medicoService.getEsamiPrenotati(medico.getId());
		model.addAttribute("esami", esami);
		
		return "esamiPrenotati";
	}
}
