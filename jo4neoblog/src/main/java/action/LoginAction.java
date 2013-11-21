package action;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import example.model.User;

@UrlBinding("/blog/login")
public class LoginAction extends BaseAction {
	
	@Validate(required = true, minlength = 1, maxlength = 255, on = "login")
	private String screenName;

	@Validate(required = true, minlength = 1, maxlength = 255, on = "login")
	private String password;
	
	@ValidationMethod(on = "login")
	public void validateLogin(ValidationErrors errors) throws NoSuchAlgorithmException {
		String e = hashPassword(password);
		try {
			User user = new User();
			user = pm().find(user).where(user.screenName).is(screenName).result();
			if (user==null || !e.equals(user.getEncryptedPassword()) )
				errors.addGlobalError(new LocalizableError("loginmsg"));
			else
				context.setLogin(user);
		} catch (NoSuchElementException exp) {
			errors.addGlobalError(new LocalizableError("loginmsg"));
		}
	}
	
	@DefaultHandler
	public Resolution start() {
		return new ForwardResolution("/login.jsp");
	}
	
	@HandlesEvent("login")
	public Resolution login() {
		return new RedirectResolution(HubAction.class);
	}
	
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
