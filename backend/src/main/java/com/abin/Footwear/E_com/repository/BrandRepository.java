package com.abin.Footwear.E_com.repository;

import com.abin.Footwear.E_com.dto.BrandInfoDTO;
import com.abin.Footwear.E_com.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT new com.abin.Footwear.E_com.dto.BrandInfoDTO(b.name, b.image) FROM Brand b")
    List<BrandInfoDTO> findAllBrandInfo();

    Brand findByName(String brand);

}
