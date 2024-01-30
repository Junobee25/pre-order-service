package com.sideproject.preorderservice.domain.entity;


import com.sideproject.preorderservice.configuration.AlarmType;
import com.sideproject.preorderservice.domain.AlarmArgs;
import com.sideproject.preorderservice.domain.Article;
import com.sideproject.preorderservice.domain.AuditingFields;
import com.sideproject.preorderservice.domain.UserAccount;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

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

    @Type(JsonType.class)
    @Column(columnDefinition = "longtext")
    private AlarmArgs args;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    public static AlarmEntity of(UserAccount userAccount, AlarmType alarmType, AlarmArgs args) {
        AlarmEntity entity = new AlarmEntity();
        entity.setUserAccount(userAccount);
        entity.setAlarmType(alarmType);
        entity.setArgs(args);
        return entity;
    }
}
