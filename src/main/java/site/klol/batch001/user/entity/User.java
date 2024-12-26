package site.klol.batch001.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.klol.batch001.common.enums.YNFlag;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="seq_no")
    private Long seqNo;

    @Column(name="kakaoId", unique = true)
    private String kakaoId;

    @Column(name="nickname", nullable = false)
    private String nickname;

    @Column(name="is_school_verified", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private YNFlag isSchoolVerified;

    @Builder
    public User(String kakaoId, String nickname, YNFlag isSchoolVerified) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.isSchoolVerified = isSchoolVerified;
    }
}

