package net.slipp.domain;

import org.springframework.data.jpa.repository.JpaRepository;

//데이터베이스에 question테이블이 생성되었으면 db와의 작업을 담당할 인터페이스가 필요~~
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
