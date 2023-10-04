package dev.zykov.micronaut.repository;

import dev.zykov.micronaut.entity.RoleRightEntity;
import dev.zykov.micronaut.entity.RoleRightId;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface RoleRightRepository extends CrudRepository<RoleRightEntity, RoleRightId> {

    List<RoleRightEntity> findAllByRoleRightIdRoleId(Integer roleId);
}
