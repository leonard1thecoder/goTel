package org.airpenthouse.GoTel.services.sponsors;

import org.airpenthouse.GoTel.dtos.sponsors.SponsorsRequest;
import org.airpenthouse.GoTel.entities.sponsors.SponsorEntity;
import org.airpenthouse.GoTel.util.executors.SponsorsExecutors;
import org.airpenthouse.GoTel.util.mappers.SponsorsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;

@Service
public class SponsorsService extends SponsorsExecutors implements Callable<List<SponsorsRequest>> {

    private SponsorsMapper mapper;
    public static String serviceHandle;

    @Override
    public List<SponsorsRequest> call() throws Exception {
        mapper = getMapper();
        return switch (serviceHandle) {
            case "GET_ALL_SPONSOR" -> getAllSponsors();
            case "GET_SPONSOR_BY_NAME" -> getSponsorByName();
            case "DONATE" -> insertDonate();
            default -> throw new RuntimeException("Entity handler failed due to incorrect value");
        };
    }

    private List<SponsorsRequest> insertDonate() {
        SponsorEntity.EntityHandler = "DONATE";
        return super.initializeSponsorsEntity().stream().map(mapper::toDto).toList();
    }

    private List<SponsorsRequest> getSponsorByName() {
        SponsorEntity.EntityHandler = "GET_SPONSOR_BY_NAME";
        return super.initializeSponsorsEntity().stream().map(mapper::toDto).toList();

    }

    private List<SponsorsRequest> getAllSponsors() {
        SponsorEntity.EntityHandler = "GET_ALL_SPONSOR";
        return super.initializeSponsorsEntity().stream().map(mapper::toDto).toList();
    }
}
