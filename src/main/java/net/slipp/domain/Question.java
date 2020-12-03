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
public class Question {

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

    private LocalDateTime createDate;

    public Question(User writer, String title, String contents){
        super();
        this.writer = writer;
        this.title =title;
        this.contents =contents;
        this.createDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate(){
        if(createDate == null)
            return "";
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
    public void update(String title, String contents){
        this.title = title;
        this.contents =contents;
    }

    public boolean isSameWriter(User loginUser){
        return this.writer.equals(loginUser);
    }


/*
    public Question(){}
 */
}

