package com.company.validators;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validateY")
public class YValidator implements Validator {
    private static final double min = -5;
    private static final double max = 5;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        double y = Double.parseDouble(String.valueOf(value));
        if (y < min || y > max) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Y must be in range [-5; 5]!"));
        }
    }
}
