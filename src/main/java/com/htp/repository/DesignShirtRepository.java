package com.htp.repository;

import com.htp.domain.DesignShirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface DesignShirtRepository
    extends CrudRepository<DesignShirt, Long>, JpaRepository<DesignShirt, Long> {

  @Query(
      "Select d from DesignShirt d INNER JOIN BlankShirt b ON d.shirt.id = b.id JOIN Color c ON b.color.id = c.id WHERE c.colorName in (:colorList)")
  List<DesignShirt> findByColor(List<String> colorList);

  @Query("select d from DesignShirt d where d.id in (:idList)")
  Set<DesignShirt> findByIdList(List<Long> idList);
}
