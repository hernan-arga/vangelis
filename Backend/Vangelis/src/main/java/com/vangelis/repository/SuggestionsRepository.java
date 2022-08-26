package com.vangelis.repository;

import com.vangelis.domain.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionsRepository extends JpaRepository<Suggestion, Long>
{
    List<Suggestion> findSuggestionsByType(String type);
}
