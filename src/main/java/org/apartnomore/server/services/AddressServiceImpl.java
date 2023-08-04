package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.AddressNotFoundException;
import org.apartnomore.server.exceptions.CommunityNotFoundException;
import org.apartnomore.server.models.Address;
import org.apartnomore.server.models.Community;
import org.apartnomore.server.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;

    }

    @Override
    public Address save(Address address) {
        return this.addressRepository.save(address);
    }

    public Address findByUserId(Long userId) throws AddressNotFoundException {
        Optional<Address> address = this.addressRepository.findByUserId(userId);
        if (address.isEmpty()) {
            throw new AddressNotFoundException("Address not found with provided string");
        } else {
            return address.get();
        }
    }
}
