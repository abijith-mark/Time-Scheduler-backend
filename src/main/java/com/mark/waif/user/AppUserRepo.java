package com.mark.waif.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AppUserRepo extends ElasticsearchRepository<AppUser, String>{

	public List<AppUser> findAll();
	public Optional<AppUser> findByUsername(String name);
}
