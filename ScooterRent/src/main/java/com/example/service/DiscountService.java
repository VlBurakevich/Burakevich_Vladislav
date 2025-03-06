package com.example.service;

import com.example.dto.DiscountDto;
import com.example.entity.Discount;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.UpdateException;
import com.example.mapper.DiscountMapper;
import com.example.repository.DiscountRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public ResponseEntity<List<DiscountDto>> getDiscounts(int page, int size) {
        Page<Discount> discountPage = discountRepository.findAll(PageRequest.of(page, size));
        List<DiscountDto> discounts = discountPage.getContent()
                .stream()
                .map(DiscountMapper.INSTANCE::entityTiDto)
                .toList();

        return ResponseEntity.ok(discounts);
    }

    @Transactional
    public ResponseEntity<DiscountDto> createDiscount(DiscountDto discountDto) {
        if (discountRepository.existsByName(discountDto.getName())) {
            throw new CreateException(Discount.class.getSimpleName());
        }
        Discount discount = DiscountMapper.INSTANCE.dtoToEntity(discountDto);
        discount = discountRepository.save(discount);

        return ResponseEntity.ok(DiscountMapper.INSTANCE.entityTiDto(discount));
    }

    @Transactional
    public ResponseEntity<DiscountDto> updateDiscount(Long id, DiscountDto discountDto) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new UpdateException(Discount.class.getSimpleName()));

        discountDto.setId(id);
        DiscountMapper.INSTANCE.updateEntityFromDto(discountDto, existingDiscount);
        Discount discount = discountRepository.save(existingDiscount);

        return ResponseEntity.ok(DiscountMapper.INSTANCE.entityTiDto(discount));
    }

    @Transactional
    public ResponseEntity<Void> deleteDiscount(Long id) {
        if (!discountRepository.existsById(id)) {
            throw new DeleteException(Discount.class.getSimpleName());
        }
        discountRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
