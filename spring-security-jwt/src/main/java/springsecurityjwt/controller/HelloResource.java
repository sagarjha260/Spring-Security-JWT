package springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springsecurityjwt_jwtutil.JWTUtil;
import springsecurityjwt_model.AuthenticationRequest;
import springsecurityjwt_model.AuthenticationResponce;

@RestController
public class HelloResource 
{
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtTokenUtil;
	
	@RequestMapping("/hello")
	public String hello()
	{
		return "Hello World";
	}
	
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsernmae(), authenticationRequest.getPassword()));// this is standard token that spring mvc use for username and password
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incoorect Username and password", e);
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsernmae());
		
		final String jwt= jwtTokenUtil.generateToken(userDetails);//this jwt is something that you would hope that client hold on to either in local storage or in cookie and it passes on api on subsequent request
																  //like if we hit http://localhost:8080/hello after this http://localhost:8080/authenticate then access denied error will come	
																//create authorization header, key as: Authorization , value: Bearer "jwt" . now tell spring is to take this jwt out from header and validate and set in execution context
		return ResponseEntity.ok(new AuthenticationResponce(jwt));
	}

}
