package site.klol.batch.match.entity;

import jakarta.persistence.*;
import lombok.*;
import site.klol.batch.common.entity.BaseEntity;
import site.klol.batch.common.enums.YNFlag;
import site.klol.batch.summoner.entity.Summoner;

@Entity
@Table(
    name = "match_history",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_match_history_smnr_match",
            columnNames = {"smnr_seq_no", "match_id"}

        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MatchHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_no")
    private Long seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "smnr_seq_no",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_match_history_summoner"),
        referencedColumnName = "seq_no"
    )
    private Summoner summoner;

    @Column(name = "match_id", nullable = false)
    private String matchId;

    @Column(name = "is_updated", nullable = false, length = 1, columnDefinition = "char(1)")
    @Enumerated(EnumType.STRING)
    private YNFlag isUpdated;

    public static MatchHistory getNotUpdatedMatchHistory(Summoner summoner, String matchId) {
        return new MatchHistory(null, summoner, matchId, YNFlag.N);
    }

    public void setIsUpdatedToY() {
        this.isUpdated = YNFlag.Y;
    }
}
