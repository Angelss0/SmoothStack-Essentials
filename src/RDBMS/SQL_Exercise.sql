-- Adds Elements to the tables.
INSERT INTO tbl_book_loans (tbl_book_loans.bookId
  , tbl_book_loans.branchId
  , tbl_book_loans.cardNo
  , tbl_book_loans.dateOut
  , tbl_book_loans.dueDate
) VALUES (10
  , 1
  , 10
  , ADDDATE(NOW(), interval -10 day)
  , NOW()
);

INSERT INTO tbl_book_copies (tbl_book_copies.bookId
  , tbl_book_copies.branchId
  , tbl_book_copies.noOfCopies
) VALUES (1
  , 1
  , 20
);

INSERT INTO tbl_book_copies (tbl_book_copies.bookId
  , tbl_book_copies.branchId
  , tbl_book_copies.noOfCopies
) VALUES (68
  , 2
  , 25
);


-- 1. Copies of The Lost Tribe owned by Sharpstown.
SELECT tbl_book_copies.noOfCopies
FROM tbl_book_copies
WHERE bookId in (
  SELECT tbl_book.bookId
  FROM tbl_book
  WHERE title = "The Lost Tribe"
) AND branchId in (
  SELECT tbl_library_branch.branchId
  FROM tbl_library_branch
  WHERE branchName = 'Sharpstown'
);


-- 2. Copies of The Lost Tribe owned by each library branch.
SELECT tbl_library_branch.branchName
  , tbl_book_copies.noOfCopies
FROM tbl_library_branch
  , tbl_book_copies
WHERE tbl_library_branch.branchId = tbl_book_copies.branchId
AND bookId in (
  SELECT tbl_book.bookId
  FROM tbl_book
  WHERE title = "The Lost Tribe"
);


-- 3. Borrowers who do not have any books checked out.
SELECT tbl_borrower.name
FROM tbl_borrower
LEFT JOIN tbl_book_loans
ON tbl_borrower.cardNo = tbl_book_loans.cardNo
WHERE tbl_book_loans.bookId is NULL;


-- 4. Books that are loaned out from the "Sharpstown" branch and whose DueDate is today.
SELECT tbl_book.title
  , tbl_borrower.name
  , tbl_borrower.address
FROM tbl_book_loans
  , tbl_book
  , tbl_borrower
WHERE tbl_book_loans.branchId IN (
  SELECT tbl_library_branch.branchId
  FROM tbl_library_branch
  WHERE tbl_library_branch.branchName = "Sharpstown"
) AND tbl_book_loans.bookId = tbl_book.bookId
  AND tbl_borrower.cardNo = tbl_book_loans.cardNo
  AND DATEDIFF(NOW(), tbl_book_loans.dueDate) = 0;


-- 5. Total Books loaned from each branch.
SELECT tbl_library_branch.branchName
  , COUNT(tbl_book_loans.bookId)
FROM tbl_book_loans
  , tbl_library_branch
WHERE tbl_book_loans.branchId = tbl_library_branch.branchId
GROUP BY tbl_book_loans.branchId;


-- 6. Get Borrowers with more than 5 books checked out.
SELECT tbl_borrower.name
  , tbl_borrower.address
  , COUNT(tbl_book_loans.bookId)
FROM tbl_book_loans, tbl_borrower
WHERE tbl_borrower.cardNo = tbl_book_loans.cardNo
GROUP BY tbl_book_loans.cardNo
HAVING COUNT(tbl_book_loans.bookId) > 5;


-- 7. Books Authored by Stephen King owned by Central Library.
SELECT tbl_book.title
  , tbl_book_copies.noOfCopies
FROM tbl_book
  , tbl_book_copies
WHERE tbl_book.bookId = tbl_book_copies.bookId
AND tbl_book_copies.branchId in (
  SELECT tbl_book_copies.branchId
  FROM tbl_book_copies, tbl_library_branch
  WHERE tbl_book_copies.branchId = tbl_library_branch.branchId
  AND tbl_library_branch.branchName = "Central"
) AND tbl_book.authId in (
    SELECT tbl_author.authorId
    FROM tbl_author
    WHERE tbl_author.authorName = "Stephen King"
);