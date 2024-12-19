package site.klol.batch001.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seqNo;

    @Column(unique = true)
    private Long kakaoId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, length = 1)
    private String isSchoolVerified;

    @Builder
    public User(Long kakaoId, String nickname, String isSchoolVerified) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.isSchoolVerified = isSchoolVerified;
    }
}

