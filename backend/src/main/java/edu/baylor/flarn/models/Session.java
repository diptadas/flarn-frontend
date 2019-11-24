package edu.baylor.flarn.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Session {
    @NotNull
    Date dateStarted;

    @NotNull
    Date dateSubmitted;

    @ElementCollection
    private List<Integer> answers = new ArrayList<>();

    // this will be calculated when session is submitted
    Long pointsGained;

    // this will be populated when session is submitted
    @ElementCollection
    private List<Integer> correctAnswers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = User.class)
    @JsonIdentityReference(alwaysAsId = true)
    User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id",
            scope = Problem.class)
    @JsonIdentityReference(alwaysAsId = true)
    Problem problem;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
