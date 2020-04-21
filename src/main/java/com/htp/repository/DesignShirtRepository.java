package com.htp.repository;

import com.htp.domain.DesignShirt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DesignShirtRepository extends JpaRepository<DesignShirt, Long> {

	@Query(
			"Select d from DesignShirt d INNER JOIN BlankShirt b ON d.shirt.id = b.id JOIN Color c ON b.color.id = c.id WHERE c.colorName in (:colorList) and d.deleted = false")
	List<DesignShirt> findByColor(List<String> colorList);

	Set<DesignShirt> findByIdInAndDeletedFalse(List<Long> idList);

	Page<DesignShirt> findByDeletedFalse(Pageable pageable);
}
