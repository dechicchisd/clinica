package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
