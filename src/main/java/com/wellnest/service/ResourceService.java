package com.wellnest.service;

import com.wellnest.dto.ResourceDTO;
import com.wellnest.model.Resource;
import com.wellnest.model.User;
import com.wellnest.repository.ResourceRepository;
import com.wellnest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public ResourceService(ResourceRepository resourceRepository, UserRepository userRepository) {
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    public List<ResourceDTO> getAllResources() {
        return resourceRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ResourceDTO> searchResources(String query) {
        return resourceRepository
                .findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ResourceDTO getById(String id) {
        return resourceRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Resource not found: " + id));
    }

    public ResourceDTO create(ResourceDTO dto, String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        Resource resource = new Resource();
        resource.setTitle(dto.getTitle());
        resource.setCategory(dto.getCategory());
        resource.setType(dto.getType());
        resource.setDescription(dto.getDescription());
        resource.setUrl(dto.getUrl());
        resource.setCreatedBy(admin);
        return toDTO(resourceRepository.save(resource));
    }

    public ResourceDTO update(String id, ResourceDTO dto) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found: " + id));
        resource.setTitle(dto.getTitle());
        resource.setCategory(dto.getCategory());
        resource.setType(dto.getType());
        resource.setDescription(dto.getDescription());
        resource.setUrl(dto.getUrl());
        return toDTO(resourceRepository.save(resource));
    }

    public void delete(String id) {
        if (!resourceRepository.existsById(id)) {
            throw new RuntimeException("Resource not found: " + id);
        }
        resourceRepository.deleteById(id);
    }

    private ResourceDTO toDTO(Resource r) {
        ResourceDTO dto = new ResourceDTO();
        dto.setId(r.getId());
        dto.setTitle(r.getTitle());
        dto.setCategory(r.getCategory());
        dto.setType(r.getType());
        dto.setDescription(r.getDescription());
        dto.setUrl(r.getUrl());
        if (r.getCreatedAt() != null) dto.setCreatedAt(r.getCreatedAt().toString());
        if (r.getUpdatedAt() != null) dto.setUpdatedAt(r.getUpdatedAt().toString());
        return dto;
    }
}
