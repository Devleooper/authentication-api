package co.com.jwtapp.authenticationapi.controller;


import co.com.jwtapp.authenticationapi.models.AuthRequest;
import co.com.jwtapp.authenticationapi.models.AuthResponse;
import co.com.jwtapp.authenticationapi.models.RegistryResponse;
import co.com.jwtapp.authenticationapi.models.generic.DataContainer;
import co.com.jwtapp.authenticationapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DataContainer<AuthResponse> authenticate(@RequestBody DataContainer<AuthRequest> requestDataContainer ) throws Exception {
        return service.authenticate(requestDataContainer);
    }

    @PostMapping(value = "/registry", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DataContainer<RegistryResponse> registry(@RequestBody DataContainer<AuthRequest> requestDataContainer) throws Exception {
        return service.doRegistry(requestDataContainer);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleError(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(error);
    }


}
