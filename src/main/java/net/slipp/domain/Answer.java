package net.slipp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Answer {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @JsonProperty
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "kf_answer_to_question"))
    private  Question question;

    @JsonProperty
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @JsonProperty
    @Lob    //이거 사용하면 255자보다 많은 것을 저장할 수 있다.
    private String contents;

    @JsonProperty
    private LocalDateTime createDate;

    public Answer(User writer, String contents, Question question){
        this.writer = writer;
        this.contents = contents;
        this.question = question;
        this.createDate = LocalDateTime.now();
    }

    public String getFormattedCreateDate(){
        if(createDate == null)
            return "";
        return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }

    public boolean isSameWriter(User loginUser) {
        return loginUser.equals(this.writer);
    }
}
