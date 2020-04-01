package co.com.jwtapp.authenticationapi.service;

import co.com.jwtapp.authenticationapi.entities.AuthUser;
import co.com.jwtapp.authenticationapi.models.AuthRequest;
import co.com.jwtapp.authenticationapi.models.AuthResponse;
import co.com.jwtapp.authenticationapi.models.RegistryResponse;
import co.com.jwtapp.authenticationapi.models.generic.DataContainer;
import co.com.jwtapp.authenticationapi.repository.AuthUserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

import static co.com.jwtapp.authenticationapi.utils.DateUtils.formatDate;

@Service
public class AuthService {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private AuthUserRespository authUserRespository;
    private BCryptPasswordEncoder encoder;
    private UserDetailsService details;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtService service, AuthUserRespository authUserRespository, BCryptPasswordEncoder bCryptPasswordEncoder, @Qualifier("custom-auth") UserDetailsService details) {
        this.authenticationManager = authenticationManager;
        this.jwtService = service;
        this.authUserRespository = authUserRespository;
        this.encoder = bCryptPasswordEncoder;
        this.details = details;
    }

    public DataContainer<AuthResponse> authenticate(DataContainer<AuthRequest> data) throws Exception {
        AuthRequest request = data.getData();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails user = details.loadUserByUsername(request.getUserName());

        AuthResponse response = new AuthResponse();
        response.setToken(jwtService.generateToken(user));
        response.setStatus(HttpStatus.OK.getReasonPhrase());
        response.setDate(formatDate(new Date()));

        DataContainer<AuthResponse> responseDataContainer = new DataContainer<>();
        responseDataContainer.setData(response);
        return responseDataContainer;
    }

    public DataContainer<RegistryResponse> doRegistry(DataContainer<AuthRequest> data) throws Exception {
        AuthRequest request = data.getData();
        String encryptedPassword = encoder.encode(request.getPassword());

        if (authUserRespository.existsByName(request.getUserName())){
            throw new Exception("USER YET CREATED!");
        }

        AuthUser user = authUserRespository.save(new AuthUser(request.getUserName(), encryptedPassword));

        RegistryResponse response = new RegistryResponse();
        response.setDescription(HttpStatus.OK.getReasonPhrase());
        response.setStatusCode(HttpStatus.OK.value());
        response.setUuid(user.getUid());
        response.setCreatedAt(formatDate(new Date()));

        DataContainer<RegistryResponse> responseDataContainer = new DataContainer<>();
        responseDataContainer.setData(response);

        return responseDataContainer;
    }

}
