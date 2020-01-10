package com.zenika.stack.oauth2testutils.resource.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SimpleController {

	@GetMapping("/whoami")
//	@PreAuthorize("hasAnyAuthority('CAN_READ')")
	public String whoami(Principal principal) {
		return principal.getName();
	}
}
