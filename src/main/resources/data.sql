-- Insert sample data for the AuthorEntity
INSERT INTO author (first_name, last_name, date_of_birth, biography)
VALUES
    ('John', 'Doe', '1980-05-15', 'A bestselling author known for suspense thrillers.'),
    ('Jane', 'Smith', '1975-08-20', 'An acclaimed novelist specializing in romance novels.'),
    ('Michael', 'Johnson', '1990-03-10', 'A young and promising writer with a unique style.'),
    ('Sophia', 'Lee', '1988-11-30', 'An award-winning author famous for historical fiction.');

-- Insert sample data for the BookEntity
INSERT INTO book (title, isbn, publication_date, summary, author_id)
VALUES
    ('The Silent Observer', '978-0451494340', '2022-03-15', 'A gripping psychological thriller that keeps you on the edge of your seat.', 1),
    ('Love in Paris', '978-0062407689', '2021-07-20', 'A heartwarming love story set in the city of love.', 2),
    ('The Enigma Code', '978-1982145114', '2023-01-10', 'A mind-bending mystery that challenges your intellect.', 3),
    ('The Secrets of the Past', '978-0062857786', '2020-05-25', 'A historical novel that takes you back in time.', 4),
    ('The Art of Deception', '978-1524763531', '2022-09-05', 'A political thriller filled with twists and turns.', 1),
    ('Summer Love', '978-0345547976', '2019-06-30', 'A delightful summer romance that warms your heart.', 2),
    ('The Quantum Paradox', '978-0316468909', '2023-11-15', 'A science fiction masterpiece exploring parallel universes.', 3),
    ('Whispers of History', '978-0743273564', '2018-04-10', 'An epic historical saga spanning generations.', 4);
