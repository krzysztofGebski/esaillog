package com.esaillog.common;

import com.esaillog.error.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EntityFinder {

    public <T> T findById(JpaRepository<T, UUID> repository, String id, String entityName) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(entityName + " not found with id: " + id));
    }

    public <T> Set<T> findAllByIds(JpaRepository<T, UUID> repository, Set<String> ids, String entityName, Function<T, UUID> idExtractor) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptySet();
        }
        Set<UUID> uuids = ids.stream().map(UUID::fromString).collect(Collectors.toSet());
        List<T> entities = repository.findAllById(uuids);

        if (entities.size() != uuids.size()) {
            Set<UUID> foundIds = entities.stream().map(idExtractor).collect(Collectors.toSet());
            uuids.removeAll(foundIds);
            throw new EntityNotFoundException(entityName + " not found with ids: " + uuids);
        }

        return Set.copyOf(entities);
    }
}