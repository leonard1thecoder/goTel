package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import org.airpenthouse.GoTel.dtos.membership.MembershipRequest;
import org.airpenthouse.GoTel.entities.membership.MembershipEntity;
import org.airpenthouse.GoTel.services.membership.MembershipService;
import org.airpenthouse.GoTel.util.CommonEntityMethod;
import org.airpenthouse.GoTel.util.mappers.MembershipMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

public class MembershipExecutor extends CommonEntityMethod {

    private final ExecutorService executeMembership;
    @Autowired
    @Getter
    private MembershipMapper mapper;

    protected MembershipExecutor() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        executeMembership = Executors.newFixedThreadPool(noProcesses);
    }

    private Set<MembershipRequest> executeMembershipService() {
        try {
            return this.impMembershipServiceExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Set<MembershipEntity> executeMembershipEntity() {
        try {
            return this.impMembershipEntityExecution();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Set<MembershipEntity> initializeMembershipEntity() {
        return executeMembershipEntity();
    }

    protected Set<MembershipRequest> initializeMembershipService() {
        return executeMembershipService();
    }

    private Set<MembershipEntity> impMembershipEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<MembershipEntity>> futureCollection;
        futureCollection = executeMembership.submit(MembershipEntity.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();

    }

    private Set<MembershipRequest> impMembershipServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<MembershipRequest>> futureCollection;
        futureCollection = executeMembership.submit(MembershipService.getInstance());
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }
}
