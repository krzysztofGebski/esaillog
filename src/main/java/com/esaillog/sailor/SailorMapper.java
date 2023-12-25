package com.esaillog.sailor;

public interface SailorMapper {
    static SailorDTO toSailorDTO(Sailor sailor) {
        return new SailorDTO(
            sailor.getId().toString(),
            sailor.getFirstName(),
            sailor.getLastName(),
            sailor.getEmail()
        );
    }

    static Sailor toSailor(SailorDTO sailorDTO) {
        Sailor sailor = new Sailor();
        sailor.setId((sailorDTO.id().isEmpty())? null : java.util.UUID.fromString(sailorDTO.id()));
        sailor.setFirstName(sailorDTO.firstName());
        sailor.setLastName(sailorDTO.lastName());
        sailor.setEmail(sailorDTO.email());
        return sailor;
    }

}
