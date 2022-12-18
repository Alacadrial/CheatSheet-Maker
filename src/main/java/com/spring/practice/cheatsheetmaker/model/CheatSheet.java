package com.spring.practice.cheatsheetmaker.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "cheatsheet")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheatSheet {
  @Id
  private String id;
  private String title;
  private String creator;
  private Boolean isPublished;
  private List<Snippet> snippets;
  private List<String> tags;
  private Date createdAt;
  private Date publishedAt;
}
