package org.example.kurakani.repo;

import org.example.kurakani.model.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRequestRepo extends JpaRepository<ConnectionRequest,Long> {
}
