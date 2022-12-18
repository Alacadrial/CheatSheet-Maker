package com.spring.practice.cheatsheetmaker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.practice.cheatsheetmaker.model.CheatSheet;

@Repository
public interface CheatSheetRepository extends MongoRepository<CheatSheet, String> {
  public List<CheatSheet> findAllByCreator(String creator);
  public List<CheatSheet> findAllByIsPublished(Boolean isPublished);

  public Optional<CheatSheet> findById(String id);
  public void deleteById(String id);
  
}
