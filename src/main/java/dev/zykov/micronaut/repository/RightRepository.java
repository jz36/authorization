package dev.zykov.micronaut.repository;

import dev.zykov.micronaut.dto.Right;
import dev.zykov.micronaut.entity.RightEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

@Repository
public interface RightRepository extends CrudRepository<RightEntity, Integer> {
    List<RightEntity> findAllByIdIn(Set<Integer> roleRights);
}
