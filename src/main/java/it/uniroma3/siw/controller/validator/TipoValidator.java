package it.uniroma3.siw.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Tipo;
import it.uniroma3.siw.service.TipoService;

@Component
public class TipoValidator implements Validator{

	@Autowired
	private TipoService tipoService;

    private static final Logger logger = LoggerFactory.getLogger(TipoValidator.class);

	
	@Override
    public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.tipoService.alreadyExists((Tipo)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		} 
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Tipo.class.equals(clazz);
    }
}
