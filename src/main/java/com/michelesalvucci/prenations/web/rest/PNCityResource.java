package com.michelesalvucci.prenations.web.rest;

import com.michelesalvucci.prenations.repository.PNCityRepository;
import com.michelesalvucci.prenations.service.dto.PNCityDTO;
import com.michelesalvucci.prenations.service.filter.PNCityFilterDTO;
import com.michelesalvucci.prenations.service.impl.PNCityServiceImpl;
import com.michelesalvucci.prenations.web.rest.headers.PNAlertHeader;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/cities")
@Slf4j
public class PNCityResource {

    private final PNCityServiceImpl cityService;

    private final PNCityRepository cityRepository;

    public PNCityResource(PNCityServiceImpl cityService, PNCityRepository cityRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
    }

    /**
     * {@code POST  /cities} : Create a new city.
     *
     * @param cityDTO the cityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cityDTO, or with status {@code 400 (Bad Request)} if the city has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PNCityDTO> createCity(@RequestBody @Valid PNCityDTO cityDTO) throws URISyntaxException, ValidationException {
        log.debug("REST request to save City : {}", cityDTO);
        cityDTO = cityService.save(cityDTO);

        return ResponseEntity.created(new URI("/api/cities/" + cityDTO.getId()))
            .headers(PNAlertHeader.createAlertHeader("City successfully created"))
            .body(cityDTO);
    }

    /**
     * {@code PUT  /cities/:id} : Updates an existing city.
     *
     * @param name the id of the cityDTO to save.
     * @param cityDTO the cityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityDTO,
     * or with status {@code 400 (Bad Request)} if the cityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PNCityDTO> updateCity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody @Valid PNCityDTO cityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update City : {}, {}", id, cityDTO);
        if (cityDTO.getId() == null) {
            throw new ValidationException("Invalid id");
        }
        if (!Objects.equals(id, cityDTO.getId())) {
            throw new ValidationException("Invalid id");
        }

        if (!cityRepository.existsById(id)) {
            throw new ValidationException("Entity not found");
        }

        cityDTO = cityService.save(cityDTO);
        return ResponseEntity.ok()
            .headers(PNAlertHeader.createAlertHeader("City successfully updated"))
            .body(cityDTO);
    }

    /**
     * {@code PATCH  /cities/:name} : Partial updates given fields of an existing city, field will ignore if it is null
     *
     * @param id the id of the cityDTO to save.
     * @param cityDTO the cityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityDTO,
     * or with status {@code 400 (Bad Request)} if the cityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PNCityDTO> partialUpdateCity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody @Valid PNCityDTO cityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update City partially : {}, {}", id, cityDTO);
        if (cityDTO.getName() == null) {
            throw new ValidationException("Invalid id");
        }
        if (!Objects.equals(id, cityDTO.getId())) {
            throw new ValidationException("Invalid id");
        }

        if (!cityRepository.existsById(id)) {
            throw new ValidationException("Entity not found");
        }

        Optional<PNCityDTO> result = cityService.partialUpdate(cityDTO);

        return result
            .map(response -> ResponseEntity.ok().headers(PNAlertHeader.createAlertHeader("City successfully updated")).body(response))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@code GET  /cities} : get all the cities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cities in body.
     */
    @GetMapping
    public ResponseEntity<Page<PNCityDTO>> getAllCities(PNCityFilterDTO filter, Pageable pageable) {
        log.debug("REST request to get all Cities");
        return ResponseEntity.ok().body(cityService.findAll(filter, pageable));
    }

    /**
     * {@code GET  /cities/:id} : get the "id" city.
     *
     * @param id the id of the cityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PNCityDTO> getCity(@PathVariable("id") Long id) {
        log.debug("REST request to get City : {}", id);
        Optional<PNCityDTO> cityDTO = cityService.findOne(id);
        return cityDTO
            .map(response -> ResponseEntity.ok().body(cityDTO.get()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@code DELETE  /cities/:id} : delete the "id" city.
     *
     * @param id the id of the cityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable("id") Long id) {
        log.debug("REST request to delete City : {}", id);
        cityService.delete(id);
        return ResponseEntity.noContent().headers(PNAlertHeader.createAlertHeader("City successfully deleted")).build();
    }
}
