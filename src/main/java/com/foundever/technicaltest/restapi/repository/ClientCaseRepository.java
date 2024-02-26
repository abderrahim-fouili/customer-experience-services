package com.foundever.technicaltest.restapi.repository;

import com.foundever.technicaltest.restapi.model.ClientCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientCaseRepository extends JpaRepository<ClientCase, Long> {
}
