package com.htp.repository;

import com.htp.domain.BlankShirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlankShirtRepository extends CrudRepository<BlankShirt, String>,
        JpaRepository<BlankShirt, String> {

    List<BlankShirt> findBySize(BlankShirt.Size size);

    @Query("select shirt from BlankShirt shirt where shirt.price >= :price")
    List<BlankShirt> findGreaterPrice(float price);

    @Query("select shirt from BlankShirt shirt where shirt.quantity <= :quantity")
    List<BlankShirt> findLowerQuantity(int quantity);

    @Query("select shirt from BlankShirt shirt join Colour c on shirt.colour.id = c.id " +
            "where c.colorName = :colourName")
    List<BlankShirt> findByColour(String colourName);

    @Modifying(flushAutomatically = true)
    @Query("update BlankShirt shirt set shirt.price = :price where shirt.id in(:list)")
    int updateShirtPrice(@Param("price") float price,
                         @Param("list") List<String> shirtId);


}
