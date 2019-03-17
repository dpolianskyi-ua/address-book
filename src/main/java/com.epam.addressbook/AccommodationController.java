package com.epam.addressbook;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

    private AccommodationRepository accommodationRepository;

    public AccommodationController(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @PostMapping
    public ResponseEntity<Accommodation> create(@RequestBody Accommodation accommodation) {
        return accommodationRepository.create(accommodation)
                .map(createdAccommodation -> new ResponseEntity<>(createdAccommodation, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @GetMapping("{id}")
    public ResponseEntity<Accommodation> getById(@PathVariable Long id) {
        return accommodationRepository.getById(id)
                .map(accommodation -> new ResponseEntity<>(accommodation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> findAll() {
        return accommodationRepository.findAll()
                .map(accommodations -> new ResponseEntity<>(accommodations, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity<Accommodation> update(@PathVariable Long id, @RequestBody Accommodation accommodation) {
        return accommodationRepository.update(id, accommodation)
                .map(updatedAccommodation -> new ResponseEntity<>(updatedAccommodation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Accommodation> delete(@PathVariable Long id) {
        accommodationRepository.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}