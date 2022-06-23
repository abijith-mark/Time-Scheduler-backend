package com.mark.waif.Event;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EventRepo extends ElasticsearchRepository<Event, String>
{
    long count();
    void deleteAllByTitle(String title);
    boolean existsByTitle(String title);
    void deleteById(String id);
    List<Event> findAllByUsers(String users);
    Optional<Event> findById(String id);
}