package it.uniroma3.siw.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Medico;
import it.uniroma3.siw.model.Paziente;
import it.uniroma3.siw.service.PazienteService;

@Component
public class PazienteValidator implements Validator{

	@Autowired
	private PazienteService pazienteService;

    private static final Logger logger = LoggerFactory.getLogger(TipoValidator.class);

	
	@Override
    public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if(this.pazienteService.alreadyExist((Paziente) o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		} 
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Paziente.class.equals(clazz);
    }
}
