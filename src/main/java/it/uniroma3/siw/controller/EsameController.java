package it.uniroma3.siw.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Esame;
import it.uniroma3.siw.model.Medico;
import it.uniroma3.siw.model.Paziente;
import it.uniroma3.siw.model.Tipo;
import it.uniroma3.siw.service.EsameService;
import it.uniroma3.siw.service.MedicoService;
import it.uniroma3.siw.service.PazienteService;
import it.uniroma3.siw.service.TipoService;

@Controller
public class EsameController {

	@Autowired
	private TipoService tipoService;

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private EsameService esameService;

	@Autowired
	private PazienteService pazienteService;

	@RequestMapping(value="/prenotaEsame", method=RequestMethod.GET)
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
							   @RequestParam("medicoID") Long idMedico,
							   @RequestParam("pazienteID") Long idPaziente) {
		
		Tipo tipo = tipoService.tipoPerId(idTipo);
		esame.setTipo(tipo);
		
		Medico medico = medicoService.medicoPerId(idMedico);
		esame.setMedico(medico);
		
		Paziente paziente = pazienteService.pazientePerId(idPaziente);
		esame.setPaziente(paziente);
		
		LocalDateTime now = LocalDateTime.now();  
		esame.setDataPrenotazione(now);
		
		esameService.inserisci(esame);
		
		return "index";
	}
	
	@RequestMapping(value="/cercaEsame", method=RequestMethod.GET)
	public String cercaEsame(Model model) {
		
		return "/admin/cercaEsame";
	}
	
	@RequestMapping(value="/addEsito", method=RequestMethod.GET)
	public String aggiungiEsito(Model model, @RequestParam("esameID") Long esameId) {
		Esame esame = this.esameService.esamePerId(esameId);
		model.addAttribute("esame", esame);
		
		String indicatori = esame.getTipo().getIndicatori();
		model.addAttribute("indicatori", indicatori);
		
		return "/admin/addEsitoForm";
	}

	@RequestMapping(value="/addEsito/{id}", method=RequestMethod.POST)
	public String aggiungiEsitoPost(Model model,
									@ModelAttribute("risultati") String risultati,
									@PathVariable("id") Long idEsame) {
		
		Esame esame = this.esameService.esamePerId(idEsame);
		esame.setRisultati(risultati);
		this.esameService.inserisci(esame);
		
		return "index";
	}
	
	@RequestMapping(value="/visualizzaEsamiPrenotati", method=RequestMethod.GET)
	public String visualizzaEsamiPrenotati(Model model, HttpSession session) {
		
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		Long pazienteId = credentials.getUser().getPaziente().getId();
		
		List<Esame> esamiPrenotati = this.pazienteService.esamiPerPazienteId(pazienteId);
		
		model.addAttribute("esami", esamiPrenotati);
		
		return "esamiPrenotati";
	}
	
	
}
