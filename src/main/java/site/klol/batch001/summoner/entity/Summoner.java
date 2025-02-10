package site.klol.batch001.summoner.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Immutable;
import site.klol.batch001.common.entity.BaseEntity;
import site.klol.batch001.common.enums.YNFlag;
import site.klol.batch001.user.entity.User;

@Entity
@Table(name = "summoner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Immutable
@ToString(exclude = "user")
public class Summoner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_no")
    private Long seqNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq_no", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_main_summonner", nullable = false, length = 1, columnDefinition = "char(1)")
    private YNFlag isMainSummoner;

    @NotNull
    @Column(name ="puuid", unique = true, nullable = false, length = 256)
    private String puuid;

    @NotNull
    @Column(name = "smnr_id", nullable = false, length = 50)
    private String summonerId;

    @NotNull
    @Column(name = "smnr_tag", nullable = false, length = 50)
    private String summonerTag;

    @Column(name = "smnr_icon")
    private String summonerIcon;

    @Column(name = "smnr_level")
    private Integer summonerLevel;

    @Column(name = "smnr_tier", length = 20)
    private String summonerTier;

    @Column(name = "smnr_lp")
    private Integer summonerLp;
}