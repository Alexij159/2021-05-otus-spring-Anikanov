insert into authors (ID,SHORTNAME,FULLNAME,BIRTHDATE) values (1,'Пушкин А.С.', 'Пушкин Александр Сергеевич', parsedatetime('06-06-1799 10.00', 'dd-MM-yyyy hh.mm'));
insert into authors (ID,SHORTNAME,FULLNAME,BIRTHDATE) values (2,'Есенин С.А.', 'Есенин Сергей Александрович', parsedatetime('21-09-1895 10.00', 'dd-MM-yyyy hh.mm'));

insert into genres (ID,`name`) values (1, 'поэма');
insert into genres (ID,`name`) values (2, 'стих');

insert into books (ID,TITLE,AUTHORID,GENREID) values (1, 'Поэма1', 2, 1);
insert into books (ID,TITLE,AUTHORID,GENREID) values (2, 'Поэма2', 1, 1);
insert into books (ID,TITLE,AUTHORID,GENREID) values (3, 'Стих1', 1, 2);
insert into books (ID,TITLE,AUTHORID,GENREID) values (4, 'Стих2', 2, 2);

insert into comments(ID,`message`, bookid) values (1, 'Нормальная книжка', 1);
insert into comments(ID,`message`, bookid) values (2, 'Нормас', 1);
insert into comments(ID,`message`, bookid) values (3, 'Нормас1', 2);
insert into comments(ID,`message`, bookid) values (4, 'Нормас2', 2);
insert into comments(ID,`message`, bookid) values (5, 'Нормас3', 2);
insert into comments(ID,`message`, bookid) values (6, 'Плохо', 4);
insert into comments(ID,`message`, bookid) values (7, 'Хорошо', 4);
insert into comments(ID,`message`, bookid) values (8, 'Очень плохо', 4);
insert into comments(ID,`message`, bookid) values (9, 'Фигня', 4);
insert into comments(ID,`message`, bookid) values (10, 'Сойдет', 2);
insert into comments(ID,`message`, bookid) values (11, 'кое-как', 2);
insert into comments(ID,`message`, bookid) values (12, 'фьють', 2);