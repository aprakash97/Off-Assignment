package com.microservice.loans.service.impl;

import com.microservice.loans.constants.LoansConstants;
import com.microservice.loans.dto.LoansDto;
import com.microservice.loans.entity.Loans;
import com.microservice.loans.exception.LoanAlreadyExistsException;
import com.microservice.loans.exception.ResourceNotFoundException;
import com.microservice.loans.mapper.LoansMapper;
import com.microservice.loans.repo.LoansRepository;
import com.microservice.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    /**
     * @param nicNumber - NIC Number of the Customer
     */
    @Override
    public void createLoan(String nicNumber) {
        Optional<Loans> optionalLoans= loansRepository.findByNicNumber(nicNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+nicNumber);
        }
        loansRepository.save(createNewLoan(nicNumber));
    }

    /**
     * @param nicNumber - NIC Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String nicNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setNicNumber(nicNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    /**
     *
     * @param nicNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public LoansDto fetchLoan(String nicNumber) {
        Loans loans = loansRepository.findByNicNumber(nicNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", nicNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return  true;
    }

    /**
     * @param nicNumber - Input MobileNumber
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String nicNumber) {
        Loans loans = loansRepository.findByNicNumber(nicNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", nicNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
