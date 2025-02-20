package site.klol.batch.user.entity;

import jakarta.persistence.*;
import lombok.*;
import site.klol.batch.common.entity.BaseEntity;
import site.klol.batch.common.enums.YNFlag;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="seq_no")
    private Long seqNo;

    @Column(name="kakaoId", unique = true)
    private String kakaoId;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name="is_school_verified", nullable = false, length = 2, columnDefinition = "VARCHAR(2)")
    @Enumerated(EnumType.STRING)
    private YNFlag isSchoolVerified;

    @Builder
    public static User of(String kakaoId, String nickname, YNFlag isSchoolVerified) {
        return new User(null, kakaoId, nickname, isSchoolVerified);
    }
}

