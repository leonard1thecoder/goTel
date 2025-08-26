package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import lombok.Setter;
import org.airpenthouse.GoTel.dtos.sponsors.SponsorsRequest;
import org.airpenthouse.GoTel.entities.sponsors.SponsorEntity;
import org.airpenthouse.GoTel.services.sponsors.SponsorsService;
import org.airpenthouse.GoTel.util.CommonEntityMethod;
import org.airpenthouse.GoTel.util.mappers.SponsorsMapper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class SponsorsExecutors extends CommonEntityMethod {

    @Getter
    private SponsorEntity entity;

    @Getter
    @Setter
    private static SponsorsMapper mapper;

    private final ExecutorService executor;

    public SponsorsExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(noProcesses);
    }


    private List<SponsorEntity> executeSponsorsEntity() {
        try {
            return this.impSponsorsEntityExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SponsorEntity> initializeSponsorsEntity() {
        return executeSponsorsEntity();
    }


    private List<SponsorEntity> impSponsorsEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<List<SponsorEntity>> futureCollection;
        futureCollection = executor.submit(new SponsorEntity());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();

    }

    private List<SponsorsRequest> impSponsorsServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<List<SponsorsRequest>> futureCollection;
        futureCollection = executor.submit(new SponsorsService());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private List<SponsorsRequest> executeSponsorsService() {
        try {
            return this.impSponsorsServiceExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
