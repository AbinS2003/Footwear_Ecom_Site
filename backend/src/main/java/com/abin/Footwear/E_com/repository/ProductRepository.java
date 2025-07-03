package com.abin.Footwear.E_com.repository;

import com.abin.Footwear.E_com.dto.CategoryImageDTO;
import com.abin.Footwear.E_com.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new com.abin.Footwear.E_com.dto.CategoryImageDTO(p.category, MIN(p.image)) " +
            "FROM Product p GROUP BY p.category")
    List<CategoryImageDTO> findDistinctCategoryImages();

    List<Product> findByCategoryAndStatus(String category, String status);
    List<Product> findByBrandIdAndStatus(Long brandId, String status);


    Optional<Product> findById(long productId);


    List<Product> findTop12ByStatusOrderByIdDesc(String status);



    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.brand.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchByTitleOrBrandContainingIgnoreCase(@Param("query") String query);

    @Query("SELECT p FROM Product p WHERE " +
            "((LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (LOWER(p.brand.name) LIKE LOWER(CONCAT('%', :query, '%')))) " +
            "AND LOWER(p.category) = LOWER(:category)")
    List<Product> searchByTitleOrBrandAndCategoryContainingIgnoreCase(@Param("query") String query,
                                                                      @Param("category") String category);


}
