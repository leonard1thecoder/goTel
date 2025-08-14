package org.airpenthouse.GoTel.util.executors;

import lombok.Getter;
import lombok.Setter;
import org.airpenthouse.GoTel.dtos.membership.MembershipRequest;
import org.airpenthouse.GoTel.entities.membership.MembershipEntity;
import org.airpenthouse.GoTel.services.membership.MembershipService;
import org.airpenthouse.GoTel.util.CommonEntityMethod;
import org.airpenthouse.GoTel.util.mappers.MembershipMapper;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;


public class MembershipExecutor extends CommonEntityMethod {

    @Getter
    private MembershipEntity entity;

    private MembershipService service;
    @Getter
    @Setter
    private static MembershipMapper mapper;

    public ExecutorService getMembershipExecutor() {
        final var noProcesses = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(noProcesses);
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

    public Set<MembershipEntity> initializeMembershipEntity() {
        return executeMembershipEntity();
    }

    public Set<MembershipRequest> initializeMembershipService(Boolean isResultSet, MembershipEntity entity) {
        if (isResultSet) {
            return executeMembershipService();
        } else {
            try {
                return impMembershipServiceExecution(entity);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Set<MembershipEntity> impMembershipEntityExecution() throws ExecutionException, InterruptedException, TimeoutException {
        entity = new MembershipEntity();
        Future<Set<MembershipEntity>> futureCollection;
        futureCollection = getMembershipExecutor().submit(entity);
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();

    }

    private Set<MembershipRequest> impMembershipServiceExecution() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Set<MembershipRequest>> futureCollection;
        futureCollection = getMembershipExecutor().submit(service);
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }

    private Set<MembershipRequest> impMembershipServiceExecution(MembershipEntity entity) throws ExecutionException, InterruptedException, TimeoutException {
        this.entity = entity;
        service = new MembershipService();
        Future<Set<MembershipRequest>> futureCollection;
        futureCollection = getMembershipExecutor().submit(service);
        return Optional.of(futureCollection.get(15, TimeUnit.SECONDS)).get();
    }
}
