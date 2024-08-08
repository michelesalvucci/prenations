package com.michelesalvucci.prenations.web.rest;

import com.michelesalvucci.prenations.repository.PNNationRepository;
import com.michelesalvucci.prenations.service.dto.PNNationDTO;
import com.michelesalvucci.prenations.service.filter.PNNationFilterDTO;
import com.michelesalvucci.prenations.service.impl.PNNationServiceImpl;
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

/**
 * REST controller for managing {@link com.michelesalvucci.prenations.domain.PNNation}.
 */
@RestController
@RequestMapping("/api/nations")
@Slf4j
public class PNationResource {

    private final PNNationServiceImpl nationService;

    private final PNNationRepository nationRepository;

    public PNationResource(PNNationServiceImpl nationService, PNNationRepository nationRepository) {
        this.nationService = nationService;
        this.nationRepository = nationRepository;
    }

    /**
     * {@code POST  /nations} : Create a new nation.
     *
     * @param nationDTO the nationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nationDTO, or with status {@code 400 (Bad Request)} if the nation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PNNationDTO> createNation(@Valid @RequestBody PNNationDTO nationDTO) throws URISyntaxException {
        log.debug("REST request to save Nation : {}", nationDTO);
        // TODO: exists method to check unique name
        nationDTO = nationService.save(nationDTO);
        return ResponseEntity.created(new URI("/api/nations/" + nationDTO.getName()))
            .headers(PNAlertHeader.createAlertHeader("Nation successfully created"))
            .body(nationDTO);
    }

    /**
     * {@code PUT  /nations/:id} : Updates an existing nation.
     *
     * @param id the id of the nationDTO to save.
     * @param nationDTO the nationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationDTO,
     * or with status {@code 400 (Bad Request)} if the nationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{name}")
    public ResponseEntity<PNNationDTO> updateNation(
        @PathVariable(value = "name", required = false) final Long id,
        @RequestBody @Valid PNNationDTO nationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Nation : {}, {}", id, nationDTO);
        if (nationDTO.getId() == null) {
            throw new ValidationException("Invalid id");
        }
        if (!Objects.equals(id, nationDTO.getId())) {
            throw new ValidationException("Invalid id");
        }

        if (!nationRepository.existsById(id)) {
            throw new ValidationException("Entity not found");
        }

        nationDTO = nationService.save(nationDTO);
        return ResponseEntity.ok()
            .headers(PNAlertHeader.createAlertHeader("Nation successfully updated"))
            .body(nationDTO);
    }

    /**
     * {@code PATCH  /nations/:id} : Partial updates given fields of an existing nation, field will ignore if it is null
     *
     * @param id the id of the nationDTO to save.
     * @param nationDTO the nationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nationDTO,
     * or with status {@code 400 (Bad Request)} if the nationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PNNationDTO> partialUpdateNation(
        @PathVariable(value = "name", required = false) final Long id,
        @RequestBody @Valid PNNationDTO nationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nation partially : {}, {}", id, nationDTO);
        if (nationDTO.getId() == null) {
            throw new ValidationException("Invalid id");
        }
        if (!Objects.equals(id, nationDTO.getId())) {
            throw new ValidationException("Invalid name");
        }

        if (!nationRepository.existsById(id)) {
            throw new ValidationException("Entity not found");
        }

        Optional<PNNationDTO> result = nationService.partialUpdate(nationDTO);

        return result
            .map(response -> ResponseEntity.ok().headers(PNAlertHeader.createAlertHeader("Nation successfully updated")).body(response))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@code GET  /nations} : get all the nations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nations in body.
     */
    @GetMapping("")
    public ResponseEntity<Page<PNNationDTO>> getAllNations(PNNationFilterDTO filter, Pageable pageable) {
        log.debug("REST request to get all Nations");
        return ResponseEntity.ok().body(nationService.findAll(filter, pageable));
    }

    /**
     * {@code GET  /nations/:id} : get the "id" nation.
     *
     * @param id the id of the nationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PNNationDTO> getNation(@PathVariable("id") Long id) {
        log.debug("REST request to get Nation : {}", id);
        Optional<PNNationDTO> nationDTO = nationService.findOne(id);
        return nationDTO
            .map(response -> ResponseEntity.ok().body(nationDTO.get()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     * {@code DELETE  /nations/:id} : delete the "id" nation.
     *
     * @param id the id of the nationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNation(@PathVariable("id") Long id) {
        log.debug("REST request to delete Nation : {}", id);
        nationService.delete(id);
        return ResponseEntity.noContent().headers(PNAlertHeader.createAlertHeader("Nation successfully deleted")).build();
    }
}
