package org.airpenthouse.GoTel.services.administrator;

import org.airpenthouse.GoTel.dtos.administrators.AdministratorsRequest;
import org.airpenthouse.GoTel.util.executors.AdministratorsExecutors;
import org.airpenthouse.GoTel.util.mappers.AdministratorsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Service
public class AdministratorsService extends AdministratorsExecutors implements Callable<Set<AdministratorsRequest>> {

    public static String serviceHandler;
    private AdministratorsMapper mapper;

    @Autowired
    public AdministratorsService() {
    }

    @Override
    public Set<AdministratorsRequest> call() throws Exception {
        mapper = AdministratorsExecutors.getMapper();
        return switch (serviceHandler) {
            case "FIND_ALL_ADMINISTRATORS" -> findAllAdminstrators();
            case "FIND_ADMINISTRATOR_BY_NAME" -> findAdminstratorsByName();
            case "FIND_ADMINISTRATOR_BY_EMAIL" -> findAdminstratorsByEmail();
            case "UPDATE_ADMINISTRATOR_TOKEN" -> updateAdministratorToken();
            case "UPDATE_ADMINISTRATOR_PASSWORD" -> updateAdministratorPassword();
            case "REGISTRATOR_ADMINISTRATOR" -> registorAdministror();
            case "ADMINISTRATOR_LOGIN" -> administrorLogin();
            default -> throw new RuntimeException("Error occurred while processing administrator entity");
        };
    }

    private Set<AdministratorsRequest> administrorLogin() {
        return initializeAdministrAtOrEntity().stream().map(mapper::toDto).collect(Collectors.toSet());
    }

    private Set<AdministratorsRequest> registorAdministror() {
        return initializeAdministrAtOrEntity().stream().map(mapper::toDto).collect(Collectors.toSet());

    }

    private Set<AdministratorsRequest> updateAdministratorPassword() {
        return initializeAdministrAtOrEntity().stream().map(mapper::toDto).collect(Collectors.toSet());

    }

    private Set<AdministratorsRequest> updateAdministratorToken() {
        return initializeAdministrAtOrEntity().stream().map(mapper::toDto).collect(Collectors.toSet());

    }

    private Set<AdministratorsRequest> findAdminstratorsByEmail() {
        return initializeAdministrAtOrEntity().stream().map(mapper::toDto).collect(Collectors.toSet());

    }

    private Set<AdministratorsRequest> findAdminstratorsByName() {
        return initializeAdministrAtOrEntity().stream().map(mapper::toDto).collect(Collectors.toSet());

    }

    private Set<AdministratorsRequest> findAllAdminstrators() {
        return initializeAdministrAtOrEntity().stream().map(mapper::toDto).collect(Collectors.toSet());

    }
}
