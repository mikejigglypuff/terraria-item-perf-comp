package com.terraria_item_perf_comp.DTO.responses.VO;

public record ProgressionDto(
  int id,
  String progressName,
  TitleDto title,
  String imgUrl
) {
}