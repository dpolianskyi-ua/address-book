package com.epam.addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InMemoryAccommodationRepository implements AccommodationRepository {
    private HashMap<Long, Accommodation> accommodations = new HashMap<>();

    @Override
    public Optional<Accommodation> create(Accommodation accommodation) {
        return Optional.of(new Accommodation(accommodations.size() + 1L, accommodation.getAddressId(), accommodation.getPersonId(), accommodation.getAccommodationDate(), accommodation.isSingleOwned()))
                .map(createdAccommodation -> {
                    accommodations.put(createdAccommodation.getId(), createdAccommodation);

                    return createdAccommodation;
                });
    }

    @Override
    public Optional<Accommodation> getById(Long id) {
        return Optional.ofNullable(accommodations.get(id));
    }

    @Override
    public Optional<List<Accommodation>> findAll() {
        return Optional.of(new ArrayList<>(accommodations.values()));
    }

    @Override
    public Optional<Accommodation> update(Long id, Accommodation accommodation) {
        return getById(id).map(foundAccommodation ->
        {
            accommodation.setId(id);
            accommodations.replace(id, accommodation);

            return accommodations.get(id);
        });
    }

    @Override
    public void delete(Long id) {
        accommodations.remove(id);
    }
}