package com.microservice.loans.repo;

import com.microservice.loans.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans, Long> {

    Optional<Loans> findByNicNumber(String nicNumber);

    Optional<Loans> findByLoanNumber(String loanNumber);
}
