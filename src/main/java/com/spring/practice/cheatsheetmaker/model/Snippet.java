package com.spring.practice.cheatsheetmaker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Snippet {
  private int index;
  private String header;
  private String content;
  private String language;
}
