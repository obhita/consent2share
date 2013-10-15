package gov.samhsa.consent2share.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inbox")
public class InboxController {

	@RequestMapping(value="inboxMain.html")
	public String profile(Model model) {

		return "views/Inbox/inboxMain";
	}
}
