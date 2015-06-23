set nocount on
use master
drop database inzstu
go
if (select count(*) from sysdatabases where name='inzstu')=0
    create database inzstu
go

use inzstu
go
/********用户基本信息类*********/
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

/********用户发布信息类*************/
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


/***********类别类************************/
create table category(
	CategoryID varchar(3)  NOT NULL ,
	CategoryName nvarchar(40) NOT NULL ,
	Description ntext NULL ,
	CONSTRAINT PK_Categories PRIMARY KEY  CLUSTERED ( CategoryID )
)
go

/************内容回复类*******************/
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
values('2011333540120','sjtu001','xiaoshuai','男','经济管理学院','1106505519@qq.com','15067157866')
go

insert into users(userid,passwd,username,gender,institute,email,phone)
values('2011333540121','sjtu001','小帅','男','经济管理学院','1106505519@qq.com','15067157866')
go

insert into users(userid,passwd,username,gender,institute,email,phone)
values('2011333540122','sjtu001','孙小帅','男','经济管理学院','1106505519@qq.com','15067157866')
go

insert into category(CategoryID,CategoryName,Description)
values('0','书籍','书籍大类')
insert into category(CategoryID,CategoryName,Description)
values('000','理学院','理学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('001','服装学院','服装学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('002','机械与自动控制学院','机械与自动控制学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('003','生命科学学院','生命科学学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('004','艺术与设计学院','艺术与设计学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('005','外国语学院','外国语学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('006','马克思主义学院','马克思主义学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('007','科学与艺术学院','科学与艺术学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('008','材料与纺织学院','材料与纺织学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('009','信息学院','信息学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('010','建筑工程学院','建筑工程学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('011','经济管理学院','经济管理学院专设课程')
insert into category(CategoryID,CategoryName,Description)
values('1','二手车','二手车大类')
insert into category(CategoryID,CategoryName,Description)
values('100','自行车','自行车')
insert into category(CategoryID,CategoryName,Description)
values('101','电动车','电动车')
insert into category(CategoryID,CategoryName,Description)
values('2','手机/平板','手机/平板大类')
insert into category(CategoryID,CategoryName,Description)
values('200','苹果','苹果产品')
insert into category(CategoryID,CategoryName,Description)
values('201','三星','三星产品')
insert into category(CategoryID,CategoryName,Description)
values('202','其他','其他')
insert into category(CategoryID,CategoryName,Description)
values('3','电脑','电脑产品')
insert into category(CategoryID,CategoryName,Description)
values('300','苹果','苹果产品')
insert into category(CategoryID,CategoryName,Description)
values('301','微软','surface')
insert into category(CategoryID,CategoryName,Description)
values('302','联想','联想、thinkpad')
insert into category(CategoryID,CategoryName,Description)
values('303','惠普','惠普')
insert into category(CategoryID,CategoryName,Description)
values('304','三星','三星')
insert into category(CategoryID,CategoryName,Description)
values('305','其他','其他')
insert into category(CategoryID,CategoryName,Description)
values('4','生活用品','可用生活用品')
insert into category(CategoryID,CategoryName,Description)
values('400','插线板','插线板')
insert into category(CategoryID,CategoryName,Description)
values('401','台灯','台灯')
insert into category(CategoryID,CategoryName,Description)
values('402','其他','其他')


