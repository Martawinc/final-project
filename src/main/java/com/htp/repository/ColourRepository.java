package com.htp.repository;

import com.htp.domain.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ColourRepository extends CrudRepository<Colour, Long>,
        JpaRepository<Colour, Long> {
}
