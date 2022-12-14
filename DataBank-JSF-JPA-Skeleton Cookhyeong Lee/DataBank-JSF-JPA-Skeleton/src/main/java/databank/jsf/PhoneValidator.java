/*****************************************************************
 * File: PersonPojo.java Course materials (22W) CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator( "phoneValidator")
public class PhoneValidator implements Validator< String> {


	private static final Pattern PHONE_PATTERN = Pattern
			.compile( "^(\\+\\d( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

	@Override
	public void validate( FacesContext context, UIComponent component, String value) throws ValidatorException {

		if ( value == null) {
			FacesMessage msg = new FacesMessage( "Phone Number should not be empty", "Invalid Phone Number format.");
			msg.setSeverity( FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException( msg);
		}
		//[TODO] 08 - complete the Matching using the PHONE_PATTERN above.
		//you can use methods matcher and matches from PHONE_PATTERN.
		//if match fails, create a new FacesMessage(String,String) object.
		//use proper error messages.
		//set the Severity of your FacesMessage to FacesMessage.SEVERITY_ERROR.
		//Finally, throw an exception with the FacesMessage in it.
		//to know what exception should be thrown, look at the signature of this method.
		Matcher matcher = PHONE_PATTERN.matcher(value);
		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage( "Phone Number does not match required pattern", "Invalid Phone Number format.");
			msg.setSeverity( FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException( msg);
		}
	}

}