package com.legendme.missions_svc.application.service;

import com.legendme.missions_svc.application.port.in.ListCategoriesUseCase;
import com.legendme.missions_svc.application.port.out.CategoryRepository;
import com.legendme.missions_svc.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ListCategoriesService implements ListCategoriesUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> execute() {
        return categoryRepository.findAllActive();
    }
}

