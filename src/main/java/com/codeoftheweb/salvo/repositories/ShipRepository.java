package com.codeoftheweb.salvo.repositories;


import com.codeoftheweb.salvo.models.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface ShipRepository extends JpaRepository <Ship, Long> {
}
