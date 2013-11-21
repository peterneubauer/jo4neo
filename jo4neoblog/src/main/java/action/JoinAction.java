package action;

import java.security.NoSuchAlgorithmException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import example.model.User;

@UrlBinding("/blog/join")
public class JoinAction extends BaseAction {

	@ValidationMethod(on = "join")
	public void validateRegistration(ValidationErrors errors) {
		if ( !info.password.equals(info.verify) )
			errors.add("password", new LocalizableError("verifymatch"));
		if (exists())
			errors.addGlobalError(new LocalizableError("userexists"));
	}

	private boolean exists() {
		User user = new User();
		return pm().find(user).where(user.screenName).is(info.screenName).result() != null;
	}

	@DefaultHandler
	public Resolution start() {
		return new ForwardResolution("/join.jsp");
	}

	@HandlesEvent("join")
	public Resolution join() throws NoSuchAlgorithmException {
		User u = new User();
		u.setScreenName(info.screenName);
		u.setEmail(info.email);
		u.setEncryptedPassword(hashPassword(info.password));
		u.save();
		context.setLogin(u);
		return new RedirectResolution(HubAction.class);
	}

	@ValidateNestedProperties({
		@Validate(field="email", required = true, minlength = 1, maxlength = 255, on = "join"),
		@Validate(field="screenName", required = true, minlength = 1, maxlength = 255, on = "join"),
		@Validate(field="password",required = true, minlength = 1, maxlength = 255, on = "join"),
		@Validate(field="verify",required = true, minlength = 1, maxlength = 255, on = "join")		 
	})
	private RegistrationInfo info;
	
	public RegistrationInfo getInfo() {
		return info;
	}

	public void setInfo(RegistrationInfo info) {
		this.info = info;
	}
}
