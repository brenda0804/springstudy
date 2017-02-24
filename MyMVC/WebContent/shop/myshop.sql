/*카테고리 테이블*/
--drop table category;
create table category(
	cnum number(8) constraint category_cnum_pk primary key,
	--카테고리번호
	code varchar2(10) constraint category_code_uk unique,
	--카테고리코드
	cname varchar2(50) no null	--카테고리명
);

--drop sequence category_cnum_seq;
create sequence category_cnum_seq
start with 1
increment by 1
nocache;

insert into category values(category_cnum_seq.nextval,'10000000','전자제품');
insert into category values(category_cnum_seq.nextval,'20000000','생활용품');
insert into category values(category_cnum_seq.nextval,'30000000','도서류');

commit;
/*상품 테이블*/
--drop table product;
create table product(
	pnum number(8) constraint product_pnum_pk primary key,--상품번호
	pname varchar2(100) not null,--상품명
	pcategory_fk varchar2(10) 
		constraint product_pcategory_fk 
		references category (code),--카테고리 테이블에있는 카테고리 코드를 참조한다.카테고리 코드
	pcompany varchar2(50),--제조사
	pimage1 varchar2(100) default 'noimage.png',--상품이미지1
	pimage2 varchar2(100) default 'noimage.png',--상품이미지2
	pimage3 varchar2(100) default 'noimage.png',--상품이미지3
	pqty number(8) default 0,--상품수량
	price number(8) default 0,--상품정가
	saleprice number(8) default 0,--상품판매가
	pspec varchar2(20),--상품스펙(HIT,BEST,NEW)
	pcontents varchar2(1000),--상품 설명
	point number(8) default 0,--포인트
	pindate date default sysdate --입고일
);

--drop sequence product_pnum_seq;
create sequence product_pnum_seq nocache;

