/*단순형 게시판*/
-- drop table board;
create table board(
	idx number(8) constraint board_idx_pk primary key,
	name varchar2(30) not null,
	email varchar2(100),
	homepage varchar2(200),
	pwd varchar2(20) not null, --글 비밀번호(삭제,수정시 사용)
	subject varchar2(200),
	content varchar2(2000),
	wdate timestamp default systimestamp,
	readnum number(8), --조회수
	filename varchar2(100), --첨부파일명
	filesize number, --파일 크기
	refer number, --글 그룹번호 [답변형 게시판에서 사용]
	lev number, --답변 레벨 [답변형 게시판에서 사용]
	sunbun number 
	--같은 글 그룹 내의 정렬 순서 [답변형 게시판에서 사용]
);

--drop sequence board_idx_seq;

create sequence board_idx_seq
start with 1
increment by 1
nocache;




