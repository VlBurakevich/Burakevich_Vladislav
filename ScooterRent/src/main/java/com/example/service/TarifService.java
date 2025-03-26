package com.example.service;

import com.example.dto.finance.TarifDto;
import com.example.dto.finance.TarifListDto;
import com.example.entity.Tarif;
import com.example.entity.TransportType;
import com.example.exceptions.CreateException;
import com.example.exceptions.DeleteException;
import com.example.exceptions.GetException;
import com.example.exceptions.UpdateException;
import com.example.mapper.TarifMapper;
import com.example.repository.TarifRepository;
import com.example.repository.TransportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifService {

    private final TarifRepository tarifRepository;
    private final TransportTypeRepository transportTypeRepository;
    private final TarifMapper tarifMapper;

    public TarifListDto getTarifs(Integer page, Integer size) {
        Page<Tarif> tarifPage = tarifRepository.findAll(PageRequest.of(page, size));
        List<TarifDto> tarifs = tarifPage.getContent().stream()
                .map(tarifMapper::entityToDto)
                .toList();

        return new TarifListDto(tarifs);
    }

    @Transactional
    public TarifDto createTarif(TarifDto tarifDto) {
        if (tarifRepository.existsByName(tarifDto.getName())) {
            throw new CreateException(Tarif.class.getSimpleName());
        }
        Tarif tarif = tarifMapper.dtoToEntity(tarifDto);
        TransportType transportType = transportTypeRepository.findById(tarifDto.getTransportTypeId())
                .orElseThrow(() -> new GetException(TransportType.class.getSimpleName()));
        tarif.setTransportType(transportType);
        tarif = tarifRepository.save(tarif);

        return tarifMapper.entityToDto(tarif);
    }

    @Transactional
    public TarifDto updateTarif(Long id, TarifDto tarifDto) {
        Tarif existingTarif = tarifRepository.findById(id)
                .orElseThrow(() -> new UpdateException(Tarif.class.getSimpleName()));

        tarifDto.setId(id);
        tarifMapper.updateEntityFromDto(tarifDto, existingTarif);
        Tarif tarif = tarifRepository.save(existingTarif);

        return tarifMapper.entityToDto(tarif);
    }

    @Transactional
    public void deleteTarif(Long id) {
        if (!tarifRepository.existsById(id)) {
            throw new DeleteException(Tarif.class.getSimpleName());
        }
        tarifRepository.deleteById(id);
    }
}
