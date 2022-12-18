package com.spring.practice.cheatsheetmaker.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.practice.cheatsheetmaker.model.Snippet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheatSheetInsertionRequest {
  private String title;
  // Json property annotation is required else it expects "published" key instead of "isPublished" on the json request body.
  @JsonProperty("isPublished")
  private boolean isPublished;
  private List<Snippet> snippets;
  private List<String> tags;
}
