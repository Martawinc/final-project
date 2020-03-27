package com.htp.repository;

import com.htp.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ColorRepository extends CrudRepository<Color, Long>, JpaRepository<Color, Long> {}
