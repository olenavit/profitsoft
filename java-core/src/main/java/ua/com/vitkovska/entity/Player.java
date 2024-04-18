package ua.com.vitkovska.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

@JsonAutoDetect
@JsonPropertyOrder({"name", "surname", "team", "year_of_birth", "position"})
public class Player {
    private String name;
    private String surname;
    private String team;
    private Integer yearOfBirth;
    private List<String> position;

    @JsonCreator
    public Player(@JsonProperty("name") String name,
                  @JsonProperty("surname") String surname,
                  @JsonProperty("team") String team,
                  @JsonProperty("year_of_birth") Integer yearOfBirth,
                  @JsonProperty("position") String position) {
        this.name = name;
        this.surname = surname;
        this.team = team;
        this.yearOfBirth = yearOfBirth;
        this.position = Arrays.asList(position.split(", "));
    }
}
