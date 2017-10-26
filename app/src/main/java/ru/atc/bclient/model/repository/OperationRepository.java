package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atc.bclient.model.entity.Operation;

public interface OperationRepository extends JpaRepository<Operation, Integer> {
}
