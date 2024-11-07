package com.microservice.accounts.repo;

import com.microservice.accounts.entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionsRepository extends JpaRepository<Promotions, Long> {

    Optional<Promotions> findByPromotionTitle(String promotionalTitle);
}
