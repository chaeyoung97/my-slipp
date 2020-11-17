package net.slipp.domain;

import net.slipp.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    private String contents;

    private LocalDateTime createDate;

    public Question(){}

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
}
