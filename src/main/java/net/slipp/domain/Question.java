package net.slipp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import net.slipp.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor //인자 없는 기본 생성자 생성
@Entity
public class Question extends AbstractEntity{

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "question")
    @OrderBy("id asc")
    private List<Answer> answers;

    @JsonProperty
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @JsonProperty
    private String title;

    @JsonProperty
    @Lob
    private String contents;

    @JsonProperty
    private Integer countOfAnswer = 0;

//    private LocalDateTime createDate;

    public Question(User writer, String title, String contents){
        super();
        this.writer = writer;
        this.title =title;
        this.contents =contents;
//        this.createDate = LocalDateTime.now();
    }


    public void update(String title, String contents){
        this.title = title;
        this.contents =contents;
    }

    public boolean isSameWriter(User loginUser){
        return this.writer.equals(loginUser);
    }

    public void addAnswer() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswer(){
        this.countOfAnswer -= 1;
    }



/*
    public Question(){}
 */
}

