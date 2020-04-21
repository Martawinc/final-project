package com.htp.repository;

import com.htp.domain.BlankShirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlankShirtRepository extends JpaRepository<BlankShirt, String> {

	List<BlankShirt> findBySize(BlankShirt.Size size);

	List<BlankShirt> findByIdIn(List<String> id);

	List<BlankShirt> findByPriceBetween(float min, float max);

	List<BlankShirt> findByQuantityBetween(int min, int max);

	@Query(
			"select shirt from BlankShirt shirt join Color c on shirt.color.id = c.id "
					+ "where c.colorName in (:colorList)")
	List<BlankShirt> findByColor(List<String> colorList);

	@Modifying(flushAutomatically = true)
	@Query("update BlankShirt shirt set shirt.price = :price where shirt.id = :id")
	void updatePrice(@Param("price") float price, @Param("id") String id);

	@Modifying(flushAutomatically = true)
	@Query("update BlankShirt shirt set shirt.quantity = :quantity where shirt.id = :id")
	void updateQuantity(@Param("quantity") int quantity, @Param("id") String id);
}
