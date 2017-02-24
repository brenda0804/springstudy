/*ī�װ� ���̺�*/
--drop table category;
create table category(
	cnum number(8) constraint category_cnum_pk primary key,
	--ī�װ���ȣ
	code varchar2(10) constraint category_code_uk unique,
	--ī�װ��ڵ�
	cname varchar2(50) no null	--ī�װ���
);

--drop sequence category_cnum_seq;
create sequence category_cnum_seq
start with 1
increment by 1
nocache;

insert into category values(category_cnum_seq.nextval,'10000000','������ǰ');
insert into category values(category_cnum_seq.nextval,'20000000','��Ȱ��ǰ');
insert into category values(category_cnum_seq.nextval,'30000000','������');

commit;
/*��ǰ ���̺�*/
--drop table product;
create table product(
	pnum number(8) constraint product_pnum_pk primary key,--��ǰ��ȣ
	pname varchar2(100) not null,--��ǰ��
	pcategory_fk varchar2(10) 
		constraint product_pcategory_fk 
		references category (code),--ī�װ� ���̺��ִ� ī�װ� �ڵ带 �����Ѵ�.ī�װ� �ڵ�
	pcompany varchar2(50),--������
	pimage1 varchar2(100) default 'noimage.png',--��ǰ�̹���1
	pimage2 varchar2(100) default 'noimage.png',--��ǰ�̹���2
	pimage3 varchar2(100) default 'noimage.png',--��ǰ�̹���3
	pqty number(8) default 0,--��ǰ����
	price number(8) default 0,--��ǰ����
	saleprice number(8) default 0,--��ǰ�ǸŰ�
	pspec varchar2(20),--��ǰ����(HIT,BEST,NEW)
	pcontents varchar2(1000),--��ǰ ����
	point number(8) default 0,--����Ʈ
	pindate date default sysdate --�԰���
);

--drop sequence product_pnum_seq;
create sequence product_pnum_seq nocache;

