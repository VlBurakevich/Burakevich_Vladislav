package com.example.service;

import com.example.dto.finance.DiscountDto;
import com.example.entity.Discount;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.UpdateException;
import com.example.mapper.DiscountMapper;
import com.example.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;

    public ResponseEntity<List<DiscountDto>> getDiscounts(int page, int size) {
        Page<Discount> discountPage = discountRepository.findAll(PageRequest.of(page, size));
        List<DiscountDto> discounts = discountPage.getContent()
                .stream()
                .map(discountMapper::entityToDto)
                .toList();

        return ResponseEntity.ok(discounts);
    }

    @Transactional
    public ResponseEntity<DiscountDto> createDiscount(DiscountDto discountDto) {
        if (discountRepository.existsByName(discountDto.getName())) {
            throw new CreateException(Discount.class.getSimpleName());
        }
        Discount discount = discountMapper.dtoToEntity(discountDto);
        discount = discountRepository.save(discount);

        return ResponseEntity.ok(discountMapper.entityToDto(discount));
    }

    @Transactional
    public ResponseEntity<DiscountDto> updateDiscount(Long id, DiscountDto discountDto) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new UpdateException(Discount.class.getSimpleName()));

        discountDto.setId(id);
        discountMapper.updateEntityFromDto(discountDto, existingDiscount);
        Discount discount = discountRepository.save(existingDiscount);

        return ResponseEntity.ok(discountMapper.entityToDto(discount));
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
