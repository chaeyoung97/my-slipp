INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) values ('cy9700', '1111', '채영', 'aaa@inha.ac');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) values ('hy9600', '1111', '하영', 'bbb@inha.ac');
insert into question (writer_id,contents, title, create_date, count_of_answer) values (1,'첫번째 게시글', '첫번째',CURRENT_TIMESTAMP(), 0);
insert into question (writer_id,contents, title, create_date, count_of_answer) values (2,'두번째 게시글', '두번째',CURRENT_TIMESTAMP(), 0);
insert into question (writer_id,contents, title, create_date, count_of_answer) values (1,'세번째 게시글', '세번째',CURRENT_TIMESTAMP(), 0);