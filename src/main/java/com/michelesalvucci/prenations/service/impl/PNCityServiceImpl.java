package com.michelesalvucci.prenations.service.impl;

import com.michelesalvucci.prenations.domain.PNCity;
import com.michelesalvucci.prenations.repository.PNCityRepository;
import com.michelesalvucci.prenations.service.PNService;
import com.michelesalvucci.prenations.service.dto.PNCityDTO;
import com.michelesalvucci.prenations.service.filter.PNStandardSpecification;
import com.michelesalvucci.prenations.service.mapper.PNCityMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
public class PNCityServiceImpl implements PNService<Long, PNCityDTO> {

    private final PNCityRepository cityRepository;

    private final PNCityMapper cityMapper;

    public PNCityServiceImpl(PNCityRepository cityRepository, PNCityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public PNCityDTO save(PNCityDTO cityDTO) {
        log.debug("Request to save City : {}", cityDTO);
        PNCity city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    @Override
    public Optional<PNCityDTO> partialUpdate(PNCityDTO cityDTO) {
        log.debug("Request to partially update City : {}", cityDTO);

        return cityRepository
            .findById(cityDTO.getId())
            .map(existingCity -> {
                cityMapper.partialUpdate(existingCity, cityDTO);

                return existingCity;
            })
            .map(cityRepository::save)
            .map(cityMapper::toDto);
    }

    @SuppressWarnings("hiding")
    @Override
    @Transactional(readOnly = true)
    public <PNCityFilterDTO> Page<PNCityDTO> findAll(PNCityFilterDTO filters, Pageable pageable) {
        log.debug("Request to get all Cities");
        log.debug("Filters: {}", filters);
        Specification<PNCity> spec = PNStandardSpecification.standardSpecification(filters);
        return cityRepository.findAll(spec, pageable).map(cityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PNCityDTO> findOne(Long id) {
        log.debug("Request to get City : {}", id);
        return cityRepository.findById(id).map(cityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete City : {}", id);
        cityRepository.deleteById(id);
    }
}
