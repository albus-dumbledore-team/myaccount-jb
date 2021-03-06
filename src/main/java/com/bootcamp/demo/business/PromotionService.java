package com.bootcamp.demo.business;

import com.bootcamp.demo.data_access.PromotionRepository;
import com.bootcamp.demo.exception.ServiceException;
import com.bootcamp.demo.model.Promotion;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bootcamp.demo.model.Promotion.*;

import java.util.concurrent.ExecutionException;

import com.bootcamp.demo.exception.ServiceException.ErrorCode;

@Service
public class PromotionService {
    PromotionRepository repository;

    @Autowired
    public void setRepository(PromotionRepository repository) {
        this.repository = repository;
    }

    public String add(Promotion promotion) throws ServiceException {
        try {
            if (!(promotion.getType() == DiscountType.PERCENTAGE) && promotion.getAmount() >= 100) {
                throw new ServiceException(ErrorCode.VALIDATION, "Promotion discount percentage cannot exceed 99.99");
            }
            return repository.addPromotion(promotion);
        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(ErrorCode.INTERNAL, e.getMessage());
        } catch (Exception exception) {
            throw new ServiceException(ErrorCode.VALIDATION, exception.getMessage());
        }
    }

    public Promotion retrieve(String code) throws ServiceException {
        try {
            Optional<Promotion> promotionOptional = repository.getPromotion(code);
            if (promotionOptional.isPresent()) {
                return promotionOptional.get();
            }
            throw new ServiceException(ErrorCode.VALIDATION, "Promotion not found");
        } catch (ExecutionException | InterruptedException e) {
            throw new ServiceException(ErrorCode.INTERNAL, e.getMessage());
        }
    }
}
