package it.uniroma3.siw.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
							   @RequestParam("tipoID") String idTipo,
							   @RequestParam("medicoID") String idMedico,
							   @RequestParam("pazienteID") String idPaziente) {
	
		if(!idTipo.equals("") && !idMedico.equals("") && !idPaziente.equals("") && esame.getDataEsame()!=null) {
			Tipo tipo = tipoService.tipoPerId(Long.parseLong(idTipo));
			esame.setTipo(tipo);
			
			Medico medico = medicoService.medicoPerId(Long.parseLong(idMedico));
			esame.setMedico(medico);
			
			Paziente paziente = pazienteService.pazientePerId(Long.parseLong(idPaziente));
			esame.setPaziente(paziente);
			
			LocalDateTime now = LocalDateTime.now();  
			esame.setDataPrenotazione(now);
			
			esameService.inserisci(esame);

			return "index";
		}
		
		model.addAttribute("esame", new Esame());
		model.addAttribute("tipologie", this.tipoService.tutti());
		model.addAttribute("medici", this.medicoService.tutti());
		
		return "/admin/prenotaEsameForm";
	}
	
	@RequestMapping(value="/cercaEsame", method=RequestMethod.GET)
	public String cercaEsame(Model model) {
		
		return "/admin/cercaEsame";
	}
	
	@RequestMapping(value="/addEsito", method=RequestMethod.GET)
	public String aggiungiEsito(Model model, @RequestParam("esameID") String esameId) {
		
			if(!esameId.equals("")) {
			Esame esame = this.esameService.esamePerId(Long.parseLong(esameId));
			model.addAttribute("esame", esame);
			
			String indicatori = esame.getTipo().getIndicatori();
			model.addAttribute("indicatori", indicatori);
			
			return "/admin/addEsitoForm";
		}
		
		return "/admin/cercaEsame";
	}

	@RequestMapping(value="/addEsito/{id}", method=RequestMethod.POST)
	public String aggiungiEsitoPost(Model model,
									@ModelAttribute("risultati") String risultati,
									@PathVariable("id") Long idEsame) {
		
		Esame esame = this.esameService.esamePerId(idEsame);

		if(!risultati.equals("")) {
			int risultatiLen = risultati.split(",").length;
			int indicatoriLen = esame.getTipo().getIndicatori().split(",").length;
			
			if(risultatiLen == indicatoriLen) {
				esame.setRisultati(risultati);
				this.esameService.inserisci(esame);
				return "index";
			}
						
		}
		
		model.addAttribute("esame", esame);
		model.addAttribute("indicatori", esame.getTipo().getIndicatori());
		
		return "/admin/addEsitoForm";
	}
	
	@RequestMapping(value="/visualizzaEsamiPrenotati", method=RequestMethod.GET)
	public String visualizzaEsamiPrenotati(Model model, HttpSession session) {
		
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		Long pazienteId = credentials.getUser().getPaziente().getId();
		
		List<Esame> esamiPrenotati = this.pazienteService.esamiPerPazienteId(pazienteId);
		
		model.addAttribute("esami", esamiPrenotati);
		
		return "esamiPrenotati";
	}

	@RequestMapping(value="/getEsame/{id}", method=RequestMethod.GET)
	public String getEsame(Model model, @PathVariable("id") Long esameId, HttpSession session) {
		
		Esame esame = this.esameService.esamePerId(esameId);
		Long idPaziente = esame.getPaziente().getUtente().getId();
		Credentials credentials = (Credentials) session.getAttribute("credentials");
		Long idUtenteConnesso = credentials.getUser().getId();
		String ruoloUtenteConnesso = credentials.getRuolo();
		String[] indicatori, risultati;
			
		if(esame.getRisultati()!=null && (idUtenteConnesso == idPaziente || ruoloUtenteConnesso.equals(Credentials.ADMIN_ROLE))) {
			indicatori = esame.getTipo().getIndicatori().split(",");
			risultati = esame.getRisultati().split(",");
				
			Map<String, String> risultatiMap = new HashMap<>();
				
			for(int i=0; i<indicatori.length; i++) {
				risultatiMap.put(indicatori[i], risultati[i]);
			}
				
			model.addAttribute("esame", esame);
			model.addAttribute("map", risultatiMap);
				
			return "esame";
		}
			
		model.addAttribute("frase", "I risultati dell'esame non sono disponibili.");
		return "risultatiNonDisponibili";
		
	}
	
	
}
