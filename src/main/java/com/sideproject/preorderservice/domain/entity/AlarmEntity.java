package com.sideproject.preorderservice.domain.entity;


import com.sideproject.preorderservice.configuration.AlarmType;
import com.sideproject.preorderservice.domain.AuditingFields;
import com.sideproject.preorderservice.domain.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"alarm\"")
@Getter
@Setter
public class AlarmEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //알람을 받는 사람
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;
    @Column
    private Long fromUserId;
    @Column
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    public static AlarmEntity of(UserAccount userAccount, Long fromUserId, Long targetId, AlarmType alarmType) {
        AlarmEntity entity = new AlarmEntity();
        entity.setUserAccount(userAccount);
        entity.setFromUserId(fromUserId);
        entity.setTargetId(targetId);
        entity.setAlarmType(alarmType);
        return entity;
    }
}
