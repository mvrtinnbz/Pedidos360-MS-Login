package com.pedidos360.ms_login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
// Controlador REST de perfil de usuario e integración de claims JWT - Pedidos360 (v1.1.0)
@RestController
@RequestMapping("/api/profile")
public class PerfilController {

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getProfile(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> response = new HashMap<>();

        if (jwt != null) {
            // Lectura real de claims decodificados desde el JWT emitido por Microsoft Entra ID
            String nombre = jwt.getClaimAsString("name");
            String email = jwt.getClaimAsString("preferred_username");
            if (email == null) {
                email = jwt.getClaimAsString("email");
            }
        
            response.put("nombre", nombre != null ? nombre : "Usuario Autenticado");
            response.put("email", email != null ? email : jwt.getSubject());
            response.put("aud", jwt.getAudience());
            response.put("iss", jwt.getIssuer() != null ? jwt.getIssuer().toString() : null);
        } else {
            // Respuesta de prueba / desarrollo local (cuando se prueba sin token en Postman)
            response.put("nombre", "Martín Baza");
            response.put("email", "martin.baza@pedidos360.com");
            response.put("modo", "Desarrollo Local (Sin Token JWT)");
        }

        return ResponseEntity.ok(response);
    }
}