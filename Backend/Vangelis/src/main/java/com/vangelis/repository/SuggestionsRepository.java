package com.vangelis.repository;

import com.vangelis.domain.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionsRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findSuggestionsByType(String type);
}
