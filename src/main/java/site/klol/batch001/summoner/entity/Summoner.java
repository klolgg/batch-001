package site.klol.batch001.summoner.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;


@Entity
@Table(name = "summoner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Immutable
public class Summoner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq_no")
    private Long seqNo;

    @NotNull
    @Column(name = "smnr_id")
    private String summonerId;

    @Column(name = "smnr_tag")
    private String summonerTag;
}