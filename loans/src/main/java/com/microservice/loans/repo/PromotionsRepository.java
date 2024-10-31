package com.microservice.loans.repo;

import com.microservice.loans.entity.Loans;
import com.microservice.loans.entity.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionsRepository extends JpaRepository<Promotions, Long> {

    Optional<Promotions> findByPromotionTitle(String promotionalTitle);
}
