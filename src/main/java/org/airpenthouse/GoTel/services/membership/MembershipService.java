package org.airpenthouse.GoTel.services.membership;

import org.airpenthouse.GoTel.dtos.membership.MembershipRequest;
import org.airpenthouse.GoTel.entities.membership.MembershipEntity;
import org.airpenthouse.GoTel.util.executors.MembershipExecutor;
import org.airpenthouse.GoTel.util.mappers.MembershipMapper;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class MembershipService extends MembershipExecutor implements Callable<Set<MembershipRequest>> {

    private static final MembershipService instance = new MembershipService();
    public static String serviceHandler;
    private final MembershipMapper mapper;

    protected MembershipService() {
        this.mapper = super.getMapper();
    }

    public static MembershipService getInstance() {
        return instance;
    }

    private Set<MembershipRequest> registerMember() {
        MembershipEntity.entityHandle = "REGISTER_MEMBER";
        return initializeMembershipEntity().stream().map(mapper::toDto).collect(Collectors.toSet());
    }

    private Set<MembershipRequest> updateMembershipStatus() {
        MembershipEntity.entityHandle = "UPDATE_MEMBERSHIP_STATUS";
        return initializeMembershipEntity().stream().map(mapper::toDto).collect(Collectors.toSet());
    }

    private Set<MembershipRequest> updateMembershipToken() {
        MembershipEntity.entityHandle = "UPDATE_MEMBERSHIP_TOKEN";
        return initializeMembershipEntity().stream().map(mapper::toDto).collect(Collectors.toSet());
    }

    private Set<MembershipRequest> getMemberByName() {
        MembershipEntity.entityHandle = "GET_MEMBER_BY_NAME";
        return initializeMembershipEntity().stream().map(mapper::toDto).collect(Collectors.toSet());
    }

    private Set<MembershipRequest> getAllMembers() {
        MembershipEntity.entityHandle = "GET_ALL_MEMBERS";
        return initializeMembershipEntity().stream().map(mapper::toDto).collect(Collectors.toSet());
    }

    @Override
    public Set<MembershipRequest> call() {

        return switch (serviceHandler) {
            case "GET_ALL_MEMBERS" -> getAllMembers();
            case "GET_MEMBER_BY_NAME" -> getMemberByName();
            case "UPDATE_MEMBERSHIP_TOKEN" -> updateMembershipToken();
            case "UPDATE_MEMBERSHIP_STATUS" -> updateMembershipStatus();
            case "REGISTER_MEMBER" -> registerMember();
            default -> null;
        };
    }

}
