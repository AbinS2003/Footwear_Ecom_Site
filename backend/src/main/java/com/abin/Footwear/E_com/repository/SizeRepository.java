package com.abin.Footwear.E_com.repository;

import com.abin.Footwear.E_com.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {


}
