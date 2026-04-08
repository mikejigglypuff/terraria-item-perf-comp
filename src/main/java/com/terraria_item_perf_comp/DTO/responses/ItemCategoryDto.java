package com.terraria_item_perf_comp.DTO.responses;

public record ItemCategoryDto(
  int id,
  String categoryName,
  int titleId,
  String titleName,
  String imgUrl
) {}