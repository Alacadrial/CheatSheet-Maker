package com.spring.practice.cheatsheetmaker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.practice.cheatsheetmaker.model.CheatSheet;
import com.spring.practice.cheatsheetmaker.repository.CheatSheetRepository;


@Service
public class CheatSheetDataService {
  
  @Autowired
  private CheatSheetRepository cheatSheetRepository;

  public Optional<CheatSheet> getCheatSheetById(String id){
    return cheatSheetRepository.findById(id);
  }

  public List<CheatSheet> getAllCheatSheets() {
    return cheatSheetRepository.findAll();
  }

    public List<CheatSheet> getAllPublicCheatSheets() {
    return cheatSheetRepository.findAllByIsPublished(true);
  }
  
  public List<CheatSheet> getAllCheatSheetsByUsername(String username) {
    return cheatSheetRepository.findAllByCreator(username);
  }

  public void insertSheet(CheatSheet cheatSheet)
  {
    cheatSheetRepository.insert(cheatSheet);
  }

  public void deleteSheetById(String id)
  {
    cheatSheetRepository.deleteById(id);
  }


}
