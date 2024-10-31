package com.microservice.loans.service;

import java.util.List;

public interface IPromotionsService {

    void createPromotion(String promotionalTitle);

    List<String> getAllPromotions();
}
