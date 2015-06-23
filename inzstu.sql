set nocount on
use master
drop database inzstu
go
if (select count(*) from sysdatabases where name='inzstu')=0
    create database inzstu
go

use inzstu
go
/********�û�������Ϣ��*********/
create table users(   
	userid nchar(13) not null,
	userpicth nvarchar(80) default 'pho.jpg',
	passwd nvarchar(20) not null,
	username nvarchar(40) ,
	nickname nvarchar(40) default 'Black Widow',
	gender nchar(4) ,
	institute  nvarchar(40) ,
	email nvarchar(40),
	phone nvarchar(40),
	CONSTRAINT PK_users PRIMARY KEY  CLUSTERED (userid)	
)
go

select * from users
go
select * from postitem
go
select * from category
go

/********�û�������Ϣ��*************/
create table postitem(
	postid int identity(1,1) PRIMARY KEY CLUSTERED,
	userid nchar(13) not null,
	customerid nchar(13),
	purchasetime nvarchar(20),
	message nvarchar(100),
	picth nvarchar(500),
	posttime nvarchar(20),
	category int default '101',
	favorite int default '0',
	state int default '0',
)
go


/***********�����************************/
create table category(
	CategoryID varchar(3)  NOT NULL ,
	CategoryName nvarchar(40) NOT NULL ,
	Description ntext NULL ,
	CONSTRAINT PK_Categories PRIMARY KEY  CLUSTERED ( CategoryID )
)
go

/************���ݻظ���*******************/
create table content(
	postid int,
	userid nchar(13),
	descri nvarchar(100),
	replytime nvarchar(20)
)



/*****************************************/
use inzstu
go
if exists (select 1 from sysobjects where name = 'chat_message') drop table chat_message
go
CREATE TABLE  chat_message(
  cmID int   identity(1,1) NOT NULL primary key ,
  cmContent text,
  cmFrom varchar(45) NOT NULL,
  cmTo varchar(45) NOT NULL,
  cmRead int  NOT NULL DEFAULT '0',
  cmPostdate datetime NOT NULL,
  cmLastupdate datetime NOT NULL,
  cmDelete int  NOT NULL DEFAULT '0',
  cmType varchar(45) DEFAULT NULL,
) 

select * from users



insert into users(userid,passwd,username,gender,institute,email,phone)
values('2011333540120','sjtu001','xiaoshuai','��','���ù���ѧԺ','1106505519@qq.com','15067157866')
go

insert into users(userid,passwd,username,gender,institute,email,phone)
values('2011333540121','sjtu001','С˧','��','���ù���ѧԺ','1106505519@qq.com','15067157866')
go

insert into users(userid,passwd,username,gender,institute,email,phone)
values('2011333540122','sjtu001','��С˧','��','���ù���ѧԺ','1106505519@qq.com','15067157866')
go

insert into category(CategoryID,CategoryName,Description)
values('0','�鼮','�鼮����')
insert into category(CategoryID,CategoryName,Description)
values('000','��ѧԺ','��ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('001','��װѧԺ','��װѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('002','��е���Զ�����ѧԺ','��е���Զ�����ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('003','������ѧѧԺ','������ѧѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('004','���������ѧԺ','���������ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('005','�����ѧԺ','�����ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('006','���˼����ѧԺ','���˼����ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('007','��ѧ������ѧԺ','��ѧ������ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('008','�������֯ѧԺ','�������֯ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('009','��ϢѧԺ','��ϢѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('010','��������ѧԺ','��������ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('011','���ù���ѧԺ','���ù���ѧԺר��γ�')
insert into category(CategoryID,CategoryName,Description)
values('1','���ֳ�','���ֳ�����')
insert into category(CategoryID,CategoryName,Description)
values('100','���г�','���г�')
insert into category(CategoryID,CategoryName,Description)
values('101','�綯��','�綯��')
insert into category(CategoryID,CategoryName,Description)
values('2','�ֻ�/ƽ��','�ֻ�/ƽ�����')
insert into category(CategoryID,CategoryName,Description)
values('200','ƻ��','ƻ����Ʒ')
insert into category(CategoryID,CategoryName,Description)
values('201','����','���ǲ�Ʒ')
insert into category(CategoryID,CategoryName,Description)
values('202','����','����')
insert into category(CategoryID,CategoryName,Description)
values('3','����','���Բ�Ʒ')
insert into category(CategoryID,CategoryName,Description)
values('300','ƻ��','ƻ����Ʒ')
insert into category(CategoryID,CategoryName,Description)
values('301','΢��','surface')
insert into category(CategoryID,CategoryName,Description)
values('302','����','���롢thinkpad')
insert into category(CategoryID,CategoryName,Description)
values('303','����','����')
insert into category(CategoryID,CategoryName,Description)
values('304','����','����')
insert into category(CategoryID,CategoryName,Description)
values('305','����','����')
insert into category(CategoryID,CategoryName,Description)
values('4','������Ʒ','����������Ʒ')
insert into category(CategoryID,CategoryName,Description)
values('400','���߰�','���߰�')
insert into category(CategoryID,CategoryName,Description)
values('401','̨��','̨��')
insert into category(CategoryID,CategoryName,Description)
values('402','����','����')


