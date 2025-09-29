package com.develatter.fisioclinic.infraestructure.controller;

import com.develatter.fisioclinic.application.service.ServiceService;
import com.develatter.fisioclinic.infraestructure.controller.dto.request.ServiceRequest;
import com.develatter.fisioclinic.infraestructure.controller.dto.response.ServiceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/services")
public class ServiceController {

    private final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable String id) {
        return ResponseEntity.ok(
                service.getServiceById(UUID.fromString(id))
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceResponse>> getAllServices() {
        return ResponseEntity.ok(
                service.getAllServices()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceResponse> createService(
            @Valid @RequestBody ServiceRequest request
    ) {
        return ResponseEntity.ok(
                service.createService(request)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "Invalid UUID: " + ex.getMessage();
    }

}
