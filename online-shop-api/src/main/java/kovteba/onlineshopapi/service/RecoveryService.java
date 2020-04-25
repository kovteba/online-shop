package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.Recovery;

public interface RecoveryService {

    void addNewRecovery(Recovery recovery);

    Recovery getRecoveryByEmail(String email);

}
