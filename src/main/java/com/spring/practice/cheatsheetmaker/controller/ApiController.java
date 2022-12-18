package com.spring.practice.cheatsheetmaker.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.practice.cheatsheetmaker.dto.CheatSheetInsertionRequest;
import com.spring.practice.cheatsheetmaker.model.CheatSheet;
import com.spring.practice.cheatsheetmaker.service.CheatSheetDataService;


@RestController
@RequestMapping("/api")
public class ApiController {
  @Autowired
  private CheatSheetDataService cheatSheetDataService;

  // Gets Authenticated User's all sheets.
  @GetMapping("/private/sheets")
  public ResponseEntity<?> GetUserSheets() {
    UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(cheatSheetDataService.getAllCheatSheetsByUsername(user.getUsername()));
  }

  // Inserts new sheet in the name of Authenticated User.
  @PostMapping("/private/sheets")
  public ResponseEntity<?> InsertSheet(@RequestBody CheatSheetInsertionRequest request) {
    UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // Can create a constructor in CheatSheet class for CheatSheetInsertionRequest object to easily map.
    CheatSheet sheet = new CheatSheet();
    sheet.setTitle(request.getTitle());
    sheet.setCreator(user.getUsername());
    sheet.setIsPublished(request.isPublished());
    sheet.setSnippets(request.getSnippets());
    sheet.setTags(request.getTags());
    sheet.setCreatedAt(new Date());
    if (request.isPublished())
      sheet.setPublishedAt(new Date());

    cheatSheetDataService.insertSheet(sheet);
    return ResponseEntity.ok("Inserted new sheet for user: " + user.getUsername());
  }

  // Gets all Sheets that are published to public, doesn't require authentication.
  @GetMapping("/public/sheets")
  public ResponseEntity<?> GetPublicSheets() {
    return ResponseEntity.ok(cheatSheetDataService.getAllPublicCheatSheets());
  }

  // Delete sheet by id, checks admin role and/or authenticated user's username with the target cheatsheet's creator
  @DeleteMapping("/sheets/{id}")
  public ResponseEntity<?> DeleteSheet(@PathVariable String id) {
    UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Optional<CheatSheet> cheatSheet = cheatSheetDataService.getCheatSheetById(id);

    if (cheatSheet.isEmpty())
    {
      return ResponseEntity.badRequest().body(String.format("Cheatsheet with id: %s is not found.", id));
    }

    Boolean isAdmin = user.getAuthorities().stream().map(authority -> authority.getAuthority()).toList()
        .contains("ROLE_ADMIN");
    Boolean isOwner = user.getUsername().compareTo(cheatSheet.get().getCreator()) == 0;

    if(!(isAdmin || isOwner))
    {
      return ResponseEntity.badRequest().body("Missing required authentication.");
    }

    cheatSheetDataService.deleteSheetById(id);
    return ResponseEntity.ok("Successfully deleted cheatsheet.");
  }
}
