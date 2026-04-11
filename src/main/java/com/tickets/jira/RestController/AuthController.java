package com.tickets.jira.RestController;

import com.tickets.jira.DTO.LoginRequestDTO;
import com.tickets.jira.DTO.LoginResponseDTO;
import com.tickets.jira.Exception.ServiceResult;
import com.tickets.jira.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ServiceResult<LoginResponseDTO>> login(
            @RequestBody LoginRequestDTO request) {

        ServiceResult<LoginResponseDTO> result = new ServiceResult<>();

        try {

            LoginResponseDTO response = authService.login(
                    request.getUsername(),
                    request.getPassword()
            );

            if (response != null) {

                result.object = response;
                result.correct = true;
                result.status = 200;
                result.message = "Login exitoso";

            } else {
                result.status = 401;
                result.ErrorMessage = "Credenciales incorrectas";
            }

        } catch (Exception ex) {
            result.status = 500;
            result.ErrorMessage = ex.getLocalizedMessage();
        }

        return ResponseEntity.status(result.status).body(result);
    }
}