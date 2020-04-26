package kovteba.onlineshopapi.mapper;

import kovteba.onlineshopapi.entity.RecoveryEntity;
import kovteba.onlineshopcommon.pojo.Recovery;


public interface RecoveryMapper {

    Recovery recoveryEntityToRecovery(RecoveryEntity recoveryEntity);

    RecoveryEntity recoveryToRecoveryEntity(Recovery recovery);

}
