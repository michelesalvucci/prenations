package com.michelesalvucci.prenations.service.impl;

import com.michelesalvucci.prenations.domain.PNNation;
import com.michelesalvucci.prenations.repository.PNNationRepository;
import com.michelesalvucci.prenations.service.PNService;
import com.michelesalvucci.prenations.service.dto.PNNationDTO;
import com.michelesalvucci.prenations.service.filter.PNNationFilterDTO;
import com.michelesalvucci.prenations.service.filter.PNStandardSpecification;
import com.michelesalvucci.prenations.service.mapper.PNNationMapper;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.michelesalvucci.prenations.domain.PNNation}.
 */
@Service
@Transactional
public class PNNationServiceImpl implements PNService<Long, PNNationDTO> {

    private static final Logger log = LoggerFactory.getLogger(PNNationServiceImpl.class);

    private final PNNationRepository nationRepository;

    private final PNNationMapper nationMapper;

    public PNNationServiceImpl(PNNationRepository nationRepository, PNNationMapper nationMapper) {
        this.nationRepository = nationRepository;
        this.nationMapper = nationMapper;
    }

    @Override
    public PNNationDTO save(PNNationDTO nationDTO) {
        log.debug("Request to save Nation : {}", nationDTO);
        PNNation nation = nationMapper.toEntity(nationDTO);
        nation = nationRepository.save(nation);
        return nationMapper.toDto(nation);
    }

    @Override
    public Optional<PNNationDTO> partialUpdate(PNNationDTO nationDTO) {
        log.debug("Request to partially update Nation : {}", nationDTO);

        return nationRepository
            .findById(nationDTO.getId())
            .map(existingNation -> {
                nationMapper.partialUpdate(existingNation, nationDTO);

                return existingNation;
            })
            .map(nationRepository::save)
            .map(nationMapper::toDto);
    }

    @SuppressWarnings("hiding")
    @Override
    @Transactional(readOnly = true)
    public <PNNationFilterDTO> Page<PNNationDTO> findAll(PNNationFilterDTO filters, Pageable pageable) {
        log.debug("Request to get all Nations");
        Specification<PNNation> spec = PNStandardSpecification.standardSpecification(filters);
        return nationRepository.findAll(spec, pageable).map(nationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PNNationDTO> findOne(Long id) {
        log.debug("Request to get Nation : {}", id);
        return nationRepository.findById(id).map(nationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nation : {}", id);
        nationRepository.deleteById(id);
    }
}
