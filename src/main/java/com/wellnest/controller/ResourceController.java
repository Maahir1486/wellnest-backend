package com.wellnest.controller;

import com.wellnest.dto.ResourceDTO;
import com.wellnest.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<ResourceDTO> getAll(@RequestParam(required = false) String q) {
        if (q != null && !q.isBlank()) {
            return resourceService.searchResources(q);
        }
        return resourceService.getAllResources();
    }

    @GetMapping("/{id}")
    public ResourceDTO getById(@PathVariable String id) {
        return resourceService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResourceDTO> create(@Valid @RequestBody ResourceDTO dto, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resourceService.create(dto, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResourceDTO update(@PathVariable String id, @Valid @RequestBody ResourceDTO dto) {
        return resourceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        resourceService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Resource deleted successfully."));
    }
}
