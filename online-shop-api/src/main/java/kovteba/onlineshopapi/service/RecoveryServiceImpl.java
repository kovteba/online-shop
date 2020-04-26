package kovteba.onlineshopapi.service;

import kovteba.onlineshopapi.entity.RecoveryEntity;
import kovteba.onlineshopapi.repository.RecoveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecoveryServiceImpl implements RecoveryService {

    @Autowired
    private RecoveryRepository recoveryRepository;

    @Override
    public void addNewRecovery(RecoveryEntity recoveryEntity) {
        recoveryRepository.save(recoveryEntity);
    }

    @Override
    public RecoveryEntity getRecoveryByEmail(String email) {
        return recoveryRepository.getByEmail(email);
    }
}
