package net.slipp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonProperty
//    private  Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public String getFormattedCreateDate(){
        return getFormattedDate(createDate, "yyyy.MM.dd HH:mm:ss");
    }

    public String getFormattedModifiedDate(){
        return getFormattedDate(modifiedDate, "yyyy.MM.dd HH:mm:ss");
    }

    private String getFormattedDate(LocalDateTime dateTime, String format){
        if(dateTime == null)
            return "";
        return dateTime.format(DateTimeFormatter.ofPattern(format));

    }
//    public boolean matchId(Long newId){
//        if(newId == null)
//            return  false;
//        return newId.equals(id);
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AbstractEntity that = (AbstractEntity) o;
//        return Objects.equals(id, that.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
