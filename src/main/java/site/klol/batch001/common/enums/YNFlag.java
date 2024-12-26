package site.klol.batch001.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum YNFlag {
    Y("Yes"),
    N("No");

    private String description;
}
