package net.slipp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@EqualsAndHashCode
@ToString   //toString메소드 자동 생성
@Setter //setter메소드 자동 생성
@Entity
public class User {
    @JsonProperty //자바 객체에서 json데이터로 변환하고 싶은 필드에 붙임
    @Getter(AccessLevel.PUBLIC) //accessLevel은 접근 제한자 설정인데 public이 디폴트임
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    //@GeneratedValue (= auto_increasement)
    private Long id;

    @JsonProperty
    @Getter
    @Column(nullable = false, length = 20, unique = true)  //필드마다 속성 설정가능
    private String userId;

    @JsonIgnore //명시적으로 처리하지 않겠다고 할 수도 있음
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty //이케하면 getter없어도 json보낼 수 잇음
    private String email;

    public boolean matchPassword(String newPassword){
        if(newPassword == null)
            return false;
        return newPassword.equals(password);
    }

    public boolean matchId(Long newId){
        if(newId == null)
            return  false;
        return newId.equals(id);
    }

    public void update(User newUser) {
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
    }


    /*

    public String getUserId() {
        return userId;
    }

     public Long getId() {
        return id;
    }

     public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
     */
}
