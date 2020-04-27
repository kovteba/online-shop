package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.RecoveryEntity;

public interface RecoveryService {

    void addNewRecovery(RecoveryEntity recoveryEntity);

    RecoveryEntity getRecoveryByEmail(String email);

    void deleteSecretTokenByEmail(String email);

}
