package com.sideproject.preorderservice.domain.entity;


import com.sideproject.preorderservice.domain.constant.AlarmType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"alarm\"")
@Getter
@Setter
public class Alarm extends AuditingFields {

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

    public static Alarm of(UserAccount userAccount, Long fromUserId, Long targetId, AlarmType alarmType) {
        Alarm entity = new Alarm();
        entity.setUserAccount(userAccount);
        entity.setFromUserId(fromUserId);
        entity.setTargetId(targetId);
        entity.setAlarmType(alarmType);
        return entity;
    }
}
