package dev.shouryapunj.repository;

import dev.shouryapunj.entity.ApiStats;
import org.springframework.data.repository.CrudRepository;

public interface ApiStatsRepository extends CrudRepository<ApiStats, String> {
}
