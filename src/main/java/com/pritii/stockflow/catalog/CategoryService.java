package com.pritii.stockflow.catalog;
import com.pritii.stockflow.common.DuplicateResourceException;
import com.pritii.stockflow.common.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Category already exists: " + request.name());
        }
        Category category = new Category();
        category.setName(request.name().trim());
        return CategoryResponse.from(repository.save(category));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        return repository.findAll().stream().map(CategoryResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        return CategoryResponse.from(find(id));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = find(id);
        if (!category.getName().equalsIgnoreCase(request.name())
                && repository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Category already exists: " + request.name());
        }
        category.setName(request.name().trim());
        return CategoryResponse.from(category);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Category find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }
}