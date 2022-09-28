select * from LibraryCard
select * from Account
select * from Permission
select * from Category
select * from BookLending
select * from BookItem
select * from Book

-- select librarycard
select cardNumber, name
from LibraryCard

-- select account
select a.username, password, p.perID, url, name, gender, dob
	 , email, phone, address, lc.cardNumber
from Account a inner join LibraryCard lc
on a.cardNumber = lc.cardNumber
left join Account_Role ar
on a.username = ar.username
left join Role r
on r.roleID = ar.roleID
left join Role_Permission rp
on r.roleID = rp.roleID
left join Permission p
on rp.perID = p.perID
where (a.username = ? or lc.cardNumber = ?) and password = ?

-- insert account
select * from LibraryCard
where cardNumber = ?

select * from Account
where username = ? or cardNumber = ?

insert into Account
	(username, password, cardNumber)
values (?,?,?)

-- update account
update LibraryCard
set name = ?, gender = ?, dob = ?, email = ?, phone = ?
	, address = ?
where cardNumber = ?

-- select category
select categoryID, categoryName
from Category

-- select book
select isbn, title, author, publication, c.categoryID, price
	, categoryName
from Book b inner join Category c
on b.categoryID = c.categoryID

select title, author, publication, c.categoryID, price
	, categoryName, barcode, status, dateOfPurchase
	, publicationDate
from Book b inner join Category c
on b.categoryID = c.categoryID
inner join BookItem bi
on b.isbn = bi.isbn
where b.isbn = ?

(title like '%' + '' + '%' and author like '%' + '' + '%' and publication like '%' + '' + '%')

select isbn, title, author, publication, categoryName
from (select ROW_NUMBER() over (order by isbn) as rownum, isbn
		    , title, author, publication, categoryName
	  from Book b inner join Category c
	  on b.categoryID = c.categoryID 
	  where 1 = 1) a
where rownum >= (1 - 1) * 10 + 1 and rownum <= 1 * 10

DECLARE @PageNumber AS INT
DECLARE @RowsOfPage AS INT
DECLARE @MaxTablePage  AS FLOAT 
SET @PageNumber = 1
SET @RowsOfPage = 10
SELECT @MaxTablePage = COUNT(*) FROM Book b inner join Category c
								on b.categoryID = c.categoryID 
								where 1 = 1  and (title like '%' + '' + '%' and author like '%' + '' + '%' and publication like '%' + '' + '%')
SET @MaxTablePage = CEILING(@MaxTablePage/@RowsOfPage)
select isbn, title, author, publication, categoryName
from Book b inner join Category c
on b.categoryID = c.categoryID 
where 1 = 1  and (title like '%' + '' + '%' and author like '%' + '' + '%' and publication like '%' + '' + '%')
order by isbn
OFFSET (@PageNumber-1)*@RowsOfPage ROWS
FETCH NEXT @RowsOfPage ROWS ONLY

select COUNT(*) as total
from (select ROW_NUMBER() over (order by isbn) as rownum, isbn
		    , title, author, publication, categoryName
	  from Book b inner join Category c
	  on b.categoryID = c.categoryID 
	  where 1 = 1 and (title like '%' + ? + '%' or 
			author like '%' + ? + '%' or 
			publication like '%' + ? + '%') and 
			c.categoryID = ?) t

select top 10 title, author, publication, categoryName
from Book b inner join Category c
on b.categoryID = c.categoryID
inner join BookItem bi
on b.isbn = bi.isbn
inner join BookLending bl
on bl.barcode = bi.barcode
group by b.isbn, title, author, publication, categoryName
order by count(bi.barcode) desc

-- insert book
select * from Book
where isbn = ?

insert into Book 
	(isbn, title, author, publication, categoryID, price)
values (?, ?, ?, ?, ?, ?)

-- update book
select *
from Book
where isbn != ? and UPPER(title) = ? and UPPER(author) = ?

update Book
set title = ?, author = ?, publication = ?, categoryID = ?
	, price = ?
where isbn = ?

update BookItem
set dateOfPurchase = ?, publicationDate = ?
where barcode = ?

-- delete book
delete from BookLending
where barcode = ?

delete from BookItem
where barcode = ?

delete from Book
where isbn in (select b.isbn
			  from Book b left join BookItem bi
			  on b.isbn = bi.isbn
			  group by b.isbn
			  having COUNT(barcode) = 0)

-- select bookitem
select top 10 *
from (select distinct title, author, publication, categoryName
		   , dateOfPurchase, publicationDate
	  from BookItem bi inner join Book b
	  on bi.isbn = b.isbn
	  inner join Category c
	  on c.categoryID = b.categoryID) t
order by dateOfPurchase desc, publicationDate desc

select barcode, title
from BookItem bi inner join Book b
on bi.isbn = b.isbn

-- insert bookitem
select * from BookItem
where barcode = ?

insert into BookItem
	(barcode, isbn, status, dateOfPurchase, publicationDate)
values (?, ?, ?, ?, ?)

-- delete bookitem
delete from BookLendingDetail
where barcode in (select barcode from BookItem
					where barcode = ?)

delete from BookItem
where barcode = ?

delete from Book
where isbn in (select b.isbn
				from Book b left join BookItem bi
				on b.isbn = bi.isbn
				group by b.isbn
				having COUNT(barcode) = 0)

-- select booklending
select *
from (select lendingID, cardNumber, title, issueDate
		   , (case
				  when returnDate is not null then 'done'
				  when status = 'lost' then status
				  when dueDate < GETDATE() then 'overdue'
				  else 'notyet'
			  end) as state, dueDate, returnDate
	  from BookLending bl inner join BookItem bi
	  on bl.barcode = bi.barcode
	  inner join Book b
	  on bi.isbn = b.isbn) t
where 1 = 1 and title like '%' + '' + '%' and issueDate >= '1/1/1990'
	  and issueDate <= '1/1/2025' and dueDate >= '1/1/1990'
	  and dueDate <= '1/1/2025' and returnDate >= '1/1/1990'
	  and returnDate <= '1/1/2025' and state like '%' + '' + '%'
	  and cardNumber = 'sa1'

select lendingID, lc.cardNumber, name, bi.barcode, title
	 , issueDate, dueDate, returnDate, status
from BookLending bl inner join LibraryCard lc
on bl.cardNumber = lc.cardNumber
inner join BookItem bi
on bl.barcode = bi.barcode
inner join Book b
on bi.isbn = b.isbn
where lendingID = ?

-- insert booklending
select * from LibraryCard
where cardNumber = ?

select * from BookItem
where barcode = ? and status = 'available'

insert into BookLending
	(cardNumber, barcode, issueDate, dueDate)
values (?, ?, ?, ?)

update BookItem
set status = 'loaned'
where barcode = ?

-- update booklending return
update BookLending
set returnDate = ?
where lendingID = ?

update BookItem
set status = 'available'
where barcode = (select barcode from BookLending 
				 where lendingID = ?)

-- update booklending lost
update BookItem
set status = 'lost'
where barcode = (select barcode from BookLending 
				 where lendingID = ?)

-- delete booklending
update BookItem
set status = 'available'
where barcode = (select barcode from BookLending
				  where lendingID = ?)
	  and status != 'lost'

delete from BookLending
where lendingID = ?

-- select permission
select perID, url from Permission

-- select account x permission
select a.username, password, name, gender, dob, email, phone
	, address, p.perID, url
from Account a left join Account_Role ar
on a.username = ar.username
left join Role r
on ar.roleID = r.roleID
left join Role_Permission rp
on r.roleID = rp.roleID
left join Permission p
on rp.perID = p.perID