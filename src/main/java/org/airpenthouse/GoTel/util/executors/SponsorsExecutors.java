package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import org.airpenthouse.GoTel.entities.sponsors.SponsorEntity;
import org.airpenthouse.GoTel.util.CommonEntityMethod;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class SponsorsExecutors extends CommonEntityMethod {

    @Getter
    private SponsorEntity entity;

    private final ExecutorService executor;

    public SponsorsExecutors() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(noProcesses);
    }


    private List<SponsorEntity> executeMembershipEntity() {
        try {
            return this.impMembershipEntityExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SponsorEntity> initializeMembershipEntity() {
        return executeMembershipEntity();
    }


    private List<SponsorEntity> impMembershipEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<List<SponsorEntity>> futureCollection;
        futureCollection = executor.submit(new SponsorEntity());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();

    }


}
