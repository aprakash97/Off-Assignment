package com.microservice.accounts.service.impl;

import com.microservice.accounts.entity.Promotions;
import com.microservice.accounts.exception.CustomerAlreadyExistsException;
import com.microservice.accounts.repo.PromotionsRepository;
import com.microservice.accounts.service.IPromotionsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PromotionsServiceImpl implements IPromotionsService{
    private PromotionsRepository promotionsRepository;

    @Override
    public void createPromotion(String promotionalTitle) {
        Optional<Promotions> optionalPromotions = promotionsRepository.findByPromotionTitle(promotionalTitle);
        if(optionalPromotions.isPresent()){
            throw new CustomerAlreadyExistsException("Promotion already registered with the same title"+promotionalTitle);
        }
        promotionsRepository.save(createNewPromotion(promotionalTitle));
    }

    private Promotions createNewPromotion(String promotionTitle) {
        Promotions promotions = new Promotions();
        promotions.setPromotionTitle(promotionTitle);
        return promotions;
    }

    @Override
    public List<String> getAllPromotions() {
        return promotionsRepository.findAll()
                .stream()
                .map(Promotions::getPromotionTitle)
                .collect(Collectors.toList());
    }

}
