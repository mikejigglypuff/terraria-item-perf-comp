package com.terraria_item_perf_comp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.terraria_item_perf_comp.DTO.responses.ItemCategoryDto;
import com.terraria_item_perf_comp.models.ItemCategory;
import com.terraria_item_perf_comp.repository.ItemCategoryRepository;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public List<ItemCategoryDto> getItemCategoriesByTitleId(int titleId) {
        List<ItemCategory> categories = itemCategoryRepository.findByTitleId(titleId);

        return categories.stream()
                .map(category -> new ItemCategoryDto(
                        category.getId(),
                        category.getCategoryName(),
                        category.getTitle().getId(),
                        category.getTitle().getTitle(),
                        category.getImgUrl()
                ))
                .toList();
    }
}


