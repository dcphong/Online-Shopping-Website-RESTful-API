package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.final_test_sof3012.sof3022_ass_restful_api.models.Category;
import com.final_test_sof3012.sof3022_ass_restful_api.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;

    @Transactional
    public List<Category> getAllsCategory() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }

}
