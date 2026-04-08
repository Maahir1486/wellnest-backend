package com.wellnest.repository;

import com.wellnest.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, String> {
    List<Resource> findByCategoryIgnoreCase(String category);
    List<Resource> findByTypeIgnoreCase(String type);
    List<Resource> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category);
}
